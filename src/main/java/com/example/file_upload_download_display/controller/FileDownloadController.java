package com.example.file_upload_download_display.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@RestController
@RequestMapping("/api")
public class FileDownloadController {

    @Value("${file.save-directory}")
    private String BASE_DIR;

    @GetMapping("/file-download/{savedDate}/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("savedDate") String savedDate, @PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
        String filePath = BASE_DIR + "/" + savedDate + "/" + fileName; // 파일 경로 수정 필요
        log.debug("파일명 및 경로 => {}", filePath);

        File file = new File(filePath);
        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(file.length());
            byte[] fileContent = Files.readAllBytes(file.toPath());

            log.debug("파일 다운로드 -> 파일명 : {} ", file.getName());
            return ResponseEntity.ok().headers(headers).body(fileContent);

        } else {
            log.debug("파일 없음");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
