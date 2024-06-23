package com.sample.music.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.sample.music.domain.result.Result;
import com.sample.music.domain.vo.FileManageVO;
import com.sample.music.service.impl.FileManageService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileManageController {

    @Resource
    private FileManageService fileManageService;

    @RequestMapping("upload")
    @ResponseBody
    public Result<FileManageVO> upload(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        return this.fileManageService.upload(uploadFile);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result<FileManageVO> delete(@RequestParam("fileName") String objectName) throws Exception {
        return this.fileManageService.delete(objectName);
    }

    @RequestMapping("list")
    @ResponseBody
    public List<OSSObjectSummary> list() throws Exception {
        return this.fileManageService.list();
    }

    @RequestMapping("download")
    @ResponseBody
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        // 浏览器以附件形式下载
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(objectName.getBytes(), StandardCharsets.ISO_8859_1));
        this.fileManageService.exportOssFile(response.getOutputStream(), objectName);
    }
}
