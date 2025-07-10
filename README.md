# 💼 Transbnk Intern Week 3 — Java Spring Boot Project

---

## 🔧 Tech Stack

| Layer             | Technologies/Tools Used                       |
| ----------------- | --------------------------------------------- |
| Framework         | Spring Boot 3.x                               |
| Security          | Spring Security + JWT                         |
| OTP & SMS         | Twilio API                                    |
| HTTP Client       | Spring WebClient                              |
| Data Persistence  | Spring Data JPA + H2/MySQL (optional)         |
| File Handling     | Apache POI (Excel read/write), **AWS S3 SDK** |
| Cloud Storage     | **Amazon S3 (AWS SDK v2)**                    |
| Scheduler         | Spring @Scheduled tasks                       |
| Testing           | JUnit 5 + SpringBootTest                      |
| Utility Libraries | Lombok                                        |
| Build Tool        | Maven                                         |
| Java Version      | 17                                            |

---

## 📁 Folder Overview

```
internPractise/
├── config/           → App & Security configuration, Twilio & S3 setup
├── controller/       → REST API endpoints
├── dto/              → Data Transfer Objects (API payloads)
├── entity/           → JPA Entities (DB Models)
├── exception/        → Global exception handler
├── helper/           → Excel & S3 utilities
├── repository/       → Spring Data JPA interfaces
├── resource/         → Custom Twilio OTP handler
├── scheduler/        → Scheduled background job (UserScheduler)
├── service/          → Business logic and JWT utilities
└── JournalApplication.java → Main class
```

---

## ✨ Key Features

### 🔐 JWT Authentication

* Login via `/user/login` using mobile number
* Generates JWT on successful login
* Authenticated routes protected via custom filter

### 📲 Twilio OTP Verification

* `/sms/sendOTP` sends OTP to mobile
* `/sms/verifyOTP` verifies OTP
* Configured using `TwilioConfig` and routed via `TwilioRouterConfig`

### 📓 Journal Management

* Save journal entries: `POST /journal/save`
* List all entries: `GET /journal/all`
* Search journals: `POST /journal/search` using specifications

### 📂 Excel Import/Export

* Upload Excel to DB: `POST /user/excel-upload`
* Download users as Excel: `GET /user/export`
* Apache POI handles the parsing and formatting

### ☁️ AWS S3 File Storage

* Upload file to S3: `POST /s3/upload`
* Download file from S3: `GET /s3/download?fileName=...`
* **Delete file from S3: `DELETE /s3/delete?fileName=...`**
* Integrated using **AWS SDK v2** with S3 client bean
* Handles bucket configuration in `S3Config.java`

### 🗓️ Scheduled Task

* `UserScheduler` runs background cleanup or maintenance logic
* Runs periodically using Spring's `@Scheduled`

### 🌐 WebClient Usage

* Demonstrates integration with external APIs via `/client/sample`
* Configured in `WebClientConfig`

---

## 🔄 API Overview

| Endpoint                | Method | Description                         |
|-------------------------| ------ | ----------------------------------- |
| `/user/login`           | POST   | Log in using mobile number/username |
| `/sms/sendOTP`          | POST   | Sends OTP via Twilio                |
| `/sms/verifyOTP`        | POST   | Verifies received OTP               |
| `/user/all`             | GET    | Lists all users                     |
| `/user/excel-upload`    | POST   | Uploads users via Excel             |
| `/user/export`          | GET    | Exports users to Excel              |
| `/journal/save`         | POST   | Creates a journal entry             |
| `/journal/all`          | GET    | Lists all journal entries           |
| `/journal/search`       | POST   | Searches journal entries            |
| `/client/sample`        | GET    | Calls external API using WebClient  |
| `/api/storage/upload`   | POST   | Uploads a file to AWS S3            |
| `/api/storage/download` | GET    | Downloads a file from AWS S3        |
| `//api/storage/delete`  | DELETE | Deletes a file from AWS S3          |

---

## 🔒 Security Overview

* **JWT Token**: Generated on login, stored in `Authorization` header
* **Spring Security**: Custom filters and stateless session management
* **Roles/Authorities**: Handled via `CustomUserDetails` and JWT claims

---

## 🧾 DTO Summary

| DTO Name               | Role                                             |
| ---------------------- | ------------------------------------------------ |
| `UserDto`              | Carries user registration/login data             |
| `TwilioOTPRequestDto`  | Phone number for OTP generation                  |
| `TwilioOTPResponseDto` | OTP send/verify status                           |
| `JournalEntryDto`      | Data for journal save/search                     |
| `LoginRequestDto`      | DTO for login endpoint                           |
| `SearchRequestDto`     | Carries search filters                           |
| `S3UploadRequestDto`   | Carries file metadata and file itself for upload |

---

## 📃 Entity Summary

| Entity Name    | Table Purpose                       |
| -------------- | ----------------------------------- |
| `User`         | Stores registered users             |
| `Otp`          | Temporarily stores OTPs             |
| `JournalEntry` | Represents journal records          |
| `Payment`      | (Optional) Payment stub logic       |
| `UserExcel`    | Entity used for Excel import/export |

---

## 🧰 Configuration Files

* `AppConfig.java`: Bean definitions and general config
* `SpringSecurityConfig.java`: HTTP security rules and filters
* `JwtAuthenticationFilter.java`: JWT parsing from request
* `TwilioConfig.java`: Initializes Twilio service
* `WebClientConfig.java`: WebClient bean for external calls
* `S3Config.java`: AWS S3 client and bucket configuration

---

## 🧪 Testing

Unit tests are located in:

```
src/test/java/com/transbnk/internPractise/
```

You can run the full suite using:

```bash
./mvnw test
```

---

## ▶️ Running Locally

1. Clone the repo:

   ```bash
   git clone https://github.com/ShreyasDankhade/transbnk_intern_week3.git
   cd internPractise
   ```

2. Set your environment in `application.properties`:

   ```properties
   twilio.accountSid=YOUR_SID
   twilio.authToken=YOUR_TOKEN
   twilio.phoneNumber=+1234567890
   jwt.secret=some_secure_key

   aws.region=us-east-1
   aws.s3.bucket=your-bucket-name
   aws.accessKey=your-access-key
   aws.secretKey=your-secret-key
   ```

3. Build and run:

   ```bash
   ./mvnw spring-boot:run
   ```

---

## 📦 Future Enhancements

* Add role-based authorization (ADMIN, USER)
* Improve OTP storage expiration using Redis
* Dockerize for container deployment
* Connect to PostgreSQL instead of H2 for persistence
* Add Swagger/OpenAPI docs
* **Add file versioning and public URLs in AWS S3**

---

## 👨‍💼 Author

**Shreyas Dankhade**
📍 GitHub: [@ShreyasDankhade](https://github.com/ShreyasDankhade)

---

## 📄 License

This project is for **educational purposes only** as part of the Transbnk Internship Program.
