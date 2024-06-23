package com.sample.music.controller;

import com.sample.music.domain.result.Result;
import com.sample.music.utils.AliOssUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class FileUploadController {
    @Resource
    private AliOssUtil ossUtil;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        // 把文件内容存储到磁盘上
        String originalFilename = file.getOriginalFilename();
        // 保证文件的名字唯一，防止文件被覆盖
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));

        String url = ossUtil.uploadFile("music/"+filename, file.getInputStream());
        return Result.success(url);
    }
}
