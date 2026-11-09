# Blog API

REST API cho nền tảng blog cá nhân, viết bằng Spring Boot.

> **Trạng thái:** đang phát triển. Hiện có CRUD người dùng, dữ liệu lưu tạm trong bộ nhớ (mất khi restart). PostgreSQL đã cấu hình trong Docker nhưng chưa kết nối với ứng dụng.

## Công nghệ

- Java 27
- Spring Boot 4.1.2 (Spring Web)
- Maven (có sẵn Maven Wrapper `mvnw`)
- PostgreSQL 16 (Docker Compose)

## Yêu cầu

- JDK 27
- Docker và Docker Compose (để chạy database)

## Cài đặt và chạy

1. Clone repo:
   ```bash
   git clone https://github.com/Jellishyfishsy/blog-api.git
   cd blog-api
   ```

2. Tạo file `.env` từ file mẫu rồi điền thông tin database:
   ```bash
   cp .env.example .env
   ```
   ```properties
   DB_NAME=your_db_name
   DB_USER=your_db_user
   DB_PASSWORD=change_me
   ```

3. Khởi động PostgreSQL:
   ```bash
   docker compose up -d
   ```

4. Chạy ứng dụng:
   ```bash
   ./mvnw spring-boot:run        # Linux/macOS
   mvnw.cmd spring-boot:run      # Windows
   ```

Ứng dụng chạy tại `http://localhost:8080`.

## API

Base URL: `/api/users`

| Method | Endpoint          | Mô tả                         | Thành công       | Lỗi             |
|--------|-------------------|-------------------------------|------------------|-----------------|
| GET    | `/api/users`      | Lấy danh sách, sắp xếp theo id | `200 OK`         |                 |
| GET    | `/api/users/{id}` | Lấy người dùng theo id         | `200 OK`         | `404 Not Found` |
| POST   | `/api/users`      | Tạo người dùng mới             | `201 Created`    |                 |
| PUT    | `/api/users/{id}` | Cập nhật người dùng            | `200 OK`         | `404 Not Found` |
| DELETE | `/api/users/{id}` | Xóa người dùng                 | `204 No Content` | `404 Not Found` |

Body cho POST và PUT:
```json
{
  "name": "Nguyen Van A",
  "email": "a@example.com"
}
```

Response:
```json
{
  "id": 1,
  "name": "Nguyen Van A",
  "email": "a@example.com"
}
```

Có thể test nhanh bằng file [`api-test.http`](api-test.http) (dùng extension REST Client trên VS Code hoặc HTTP Client của IntelliJ).

## Chạy test

```bash
./mvnw test
```

## Cấu trúc thư mục

```
.
├── docker-compose.yml          # PostgreSQL
├── .env.example                # Mẫu biến môi trường
├── api-test.http               # Request mẫu để test API
├── pom.xml
└── src
    ├── main
    │   ├── java/com/example/demo
    │   │   ├── DemoApplication.java   # Điểm khởi động
    │   │   └── UserControler.java     # CRUD /api/users
    │   └── resources/application.properties
    └── test/java/com/example/demo
        └── DemoApplicationTests.java
```

## Lộ trình

- [x] CRUD người dùng (lưu trong bộ nhớ)
- [ ] Kết nối PostgreSQL bằng Spring Data JPA
- [ ] Tách lớp Controller / Service / Repository / DTO
- [ ] Validate dữ liệu và xử lý lỗi tập trung
- [ ] Bài viết (Post), bình luận (Comment), thẻ (Tag)
- [ ] Đăng ký, đăng nhập bằng JWT (Spring Security)
- [ ] Tài liệu API với Swagger (springdoc-openapi)
