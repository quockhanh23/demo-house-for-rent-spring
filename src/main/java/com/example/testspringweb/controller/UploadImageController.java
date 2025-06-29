package com.example.testspringweb.controller;


import com.example.testspringweb.common.CommonUtils;
import com.example.testspringweb.dto.imgbb.ImgbbResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class UploadImageController {

    private final RestTemplate restTemplate;

    @PostMapping("/uploadImageToImgbb")
    public ResponseEntity<Object> uploadImageToImgbb(@RequestParam("file") MultipartFile image) throws IOException {
        String API_KEY = "60240f7e1353fc707e7b77994b1bcd81";
        String url = "https://api.imgbb.com/1/upload?expiration=31536000&key=" + API_KEY;

        // ✅ 1. Ghi MultipartFile ra file tạm
        File tempFile = File.createTempFile("upload-", image.getOriginalFilename());
        image.transferTo(tempFile); // chuyển dữ liệu từ MultipartFile vào temp file

        // ✅ 2. Chuẩn bị multipart body gửi đến imgbb
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource(tempFile));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class
        );

        // ✅ 3. Xóa file tạm sau khi xong (tùy chọn)
        tempFile.delete();
        ImgbbResponse readValue = CommonUtils.intObjectMapper().readValue(response.getBody(), ImgbbResponse.class);
        return ResponseEntity.ok(readValue.getData().getDisplay_url());
    }
}
