package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
//enum chứa các status code của HTTP
import org.springframework.http.ResponseEntity;
//cho phép tự do tùy biến Mã trạng thái

import java.util.List;//List
import java.util.concurrent.ConcurrentHashMap;//hàm băm cấu trúc key - value
import java.util.concurrent.atomic.AtomicLong;// biến tự tăng để lưu dữ liệu tạm thời khi chưa có database

@RestController 
//Khai báo controller trả về dữ liệu cho client
@RequestMapping ("/api/users")
public class UserControler {
    private final ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();
    private  final AtomicLong nextID = new AtomicLong();

    // GET: Lấy danh sách người dùng trả về 200 nếu thành công
    @GetMapping 
    // khi client gửi request tới /api/users sẽ tự động gọi hàm này
    public List<User> getAllUsers(){
    //Vì có RestController nên List sẽ tự động chuyển về mảng dạnh JSON
        return users.values().stream().sorted((a,b) -> Long.compare(a.id(), b.id())).toList();
        // chuyển danh sách thành một luồng (stream) thực hiện xử lý các thao tác liên hoàn (so sánh các phần tử) -> sắp xếp stream thông qua biểu thức lambda so sánh 2 số bất kì - > sắp xếp theo id tăng dần rồi trả về kết quả kiểu list 
    }
    // Lấy danh sách tất cả người dùng sắp xếp theo ID tăng dần

    // GET: lấy dữ liệu theo ID
    @GetMapping ("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
    // Lấy dữ liệu của user có giá trị id truyền vào hàm 
        User user = users.get(id);
        //khai báo biến chứa user có ID trùng với ID nhập vào

        if (user == null){
            return ResponseEntity.notFound().build();
        }
        // Nếu user ko tồn tại tạo mới

        return  ResponseEntity.ok(user);
        // Nếu đã có thì ok
    }

    //Post: Tạo người dùng mới
    @PostMapping 
    public ResponseEntity<User> creaeteUser(
            @RequestBody UserRequest request){
        
        Long id = nextID.incrementAndGet();
        User newUser = new User(
            id, 
            request.name(), 
            request.email()
        );
        
        users.put(id,newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request){
        
        User existignUser = users.get(id);

        if (existignUser == null){
            return ResponseEntity.notFound().build();
        }

        User updatedUser = new User(
            id,
            request.name(),
            request.email()
        );   

        users.put(id,updatedUser);

        return ResponseEntity.ok(updatedUser);
    }
    // DELETE: xóa người dùng 
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        User removedUser = users.remove(id);

        if (removedUser == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    public record User(
        Long id,
        String name,
        String email
    ) {}
    
    public record UserRequest(
        String name,
        String email
    ) {
    }
}
