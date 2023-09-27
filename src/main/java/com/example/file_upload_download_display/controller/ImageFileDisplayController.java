package com.example.file_upload_download_display.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class ImageFileDisplayController {

    @Value("${file.save-directory}")
    private String BASE_DIR;

    @GetMapping("/file-display/{savedDate}/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("savedDate") String savedDate, @PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {

        String imgPath = BASE_DIR + "/" + savedDate + "/" + imageName;
        File imageFile = new File(imgPath);

        if (imageFile.exists()) {
            log.debug("파일 발견 => {}", imageFile);

            return ResponseEntity.status(200)
                                 .contentType(MediaType.IMAGE_PNG)
                                 .body(Files.readAllBytes(imageFile.toPath()));

        } else {
            log.debug("파일 없음");
            return ResponseEntity.notFound().build();
        }
    }


}
