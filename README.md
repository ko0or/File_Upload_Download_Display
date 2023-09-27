# 파일 업로드 / 다운로드 / 이미지 표시

### 버전 정보
`🔨 SpringBoot 2.7.16, JDK 1.8` <br />

### REST API

`[POST] http://localhost:8080/api/file-upload` <br />
`[GET] http://localhost:8080/api/file-download/저장일시(YYYY-MM-DD)/파일명.확장자` <br />
`[GET] http://localhost:8080/api/file-display/저장일시(YYYY-MM-DD)/파일명.확장자` <br />
```
@PostMapping("file-upload")
@GetMapping("/file-download/{savedDate}/{fileName}")
@GetMapping("/file-display/{savedDate}/{imageName}")
```




### 설정 파일
`📃 build.gradle`
```
plugins {
    id 'java'
    id 'org.springframework.boot' version '2.7.16'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'

java {
    sourceCompatibility = '1.8'
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
    useJUnitPlatform()
}

```
<br /><br />

`📃 application.yml`
```
# 업로드 파일 용량 제한 -> 파일 개별
# spring.servlet.multipart.max-file-size=5MB
# 업로드 파일 용량 제한 -> 파일 전체
# spring.servlet.multipart.max-request-size=10MB

# 저장경로
file:
  save-directory: C:/SPRINGBOOT_FILE_UPLOAD

# 업로드 용량 제한 -> 파일 개별(max-file-size: 5MB) & 모두 합쳐서(max-request-size: 10MB) 까지로 제한
spring:
  servlet:
    multipart:
      max-file-size: 5MB
      max-request-size: 10MB

# 디버깅 설정
logging:
  level:
    com.example.file_upload_download_display: debug

```