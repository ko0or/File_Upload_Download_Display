package com.example.file_upload_download_display.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Value("${file.save-directory}")
    private String BASE_DIR;

    @PostMapping("file-upload")
    public ResponseEntity<String> fileUpload(@RequestParam("files") MultipartFile[] files) {

        // 파일 저장 경로 설정 (없을 경우 새로 생성)
        String path = BASE_DIR + "/" + LocalDate.now() + "/";
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 업로드 (대상 존재시)
        if (files != null && files.length > 0) {
            // 파일이 여러개일 수 있으니, 다음과 같이 반복
            Arrays.stream(files).forEach(file -> {
                try {
                    // 각 파일마다, transferTo() 메소드를 이용해서, 파일 객체를 저장
                    String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    File target = new File(directory, filename);
                    file.transferTo(target);
                } catch (IOException e) {
                    // 저장하다가 오류 발생시 로그 찍어주기
                    log.debug("오류 발생 -> 파일 저장하다가 문제 발생 \n" + e);
                }
            });
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("파일 업로드 성공! -> " + directory.getAbsolutePath());
    }
}
