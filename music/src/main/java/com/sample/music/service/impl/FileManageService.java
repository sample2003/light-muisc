package com.sample.music.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.sample.music.config.AliOssConfig;
import com.sample.music.domain.result.Result;
import com.sample.music.domain.vo.FileManageVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class FileManageService {

    // 允许上传的格式
    private static final String[] FILE_TYPE = new String[]{
            ".txt", ".bmp", ".jpg", ".jpeg", ".gif", ".png", ".mp3", ".flac"};

    @Resource
    private OSS ossClient;
    @Resource
    private AliOssConfig aliOssConfig;

    /**
     * 文件上传接口
     *
     * @param uploadFile 文件
     * @return Result<FileManageVO>
     */
    public Result<FileManageVO> upload(MultipartFile uploadFile) {
        // 校验文件格式
        boolean isLegal = false;
        for (String type : FILE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        // 封装Result对象，并且将文件的byte数组放置到result对象中
        FileManageVO fileManageVO = new FileManageVO();
        if (!isLegal) {
            fileManageVO.setStatus("error");
            return Result.success(fileManageVO);
        }
        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);
        // 上传到阿里云
        try {
            ossClient.putObject(aliOssConfig.getBucketName(), filePath, new ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            // 上传失败
            fileManageVO.setStatus("error");
            return Result.error(400, "上传失败", fileManageVO);
        }
        fileManageVO.setStatus("done");
        fileManageVO.setResponse("success");
        // 文件路径需要保存到数据库
        fileManageVO.setUrl(this.aliOssConfig.getUrlPrefix() + filePath);
        fileManageVO.setUid(String.valueOf(System.currentTimeMillis()));
        return Result.success(fileManageVO);
    }

    public String uploadInterface(MultipartFile uploadFile, String path) {
        // 校验文件格式
        boolean isLegal = false;
        for (String type : FILE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            return "error";
        }
        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = customFilePath(fileName, path);
        // 上传到阿里云
        try {
            ossClient.putObject(aliOssConfig.getBucketName(), filePath, new ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return this.aliOssConfig.getUrlPrefix() + filePath;
    }

    /**
     * 生成路径以及文件名
     * @param sourceFileName 文件路径
     * @return String
     */
    private String getFilePath(String sourceFileName) {
        // 获取当前时间
        Date date = new Date();

        // 格式化日期
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        // 创建目录路径
        String dirPath = "music/"
                + yearFormat.format(date) + "/"
                + monthFormat.format(date) + "/"
                + dayFormat.format(date) + "/";

        // 获取文件的原始扩展名
        int extensionIndex = sourceFileName.lastIndexOf('.');
        String extension = (extensionIndex != -1) ? sourceFileName.substring(extensionIndex) : "";

        // 生成随机数作为文件名的一部分
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // 生成1000到9999的随机数

        // 创建新的文件名
        String newFileName = System.currentTimeMillis() + randomNumber + extension;

        // 返回完整的文件路径
        return dirPath + newFileName;
    }

    private String customFilePath(String sourceFileName, String path) {

        // 创建目录路径
        String dirPath = "music/" + path + "/";

        // 获取文件的原始扩展名
        int extensionIndex = sourceFileName.lastIndexOf('.');
        String extension = (extensionIndex != -1) ? sourceFileName.substring(extensionIndex) : "";

        // 生成随机数作为文件名的一部分
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // 生成1000到9999的随机数

        // 创建新的文件名
        String newFileName = System.currentTimeMillis() + randomNumber + extension;

        // 返回完整的文件路径
        return dirPath + newFileName;
    }

    /**
     * 查看文件列表
     * @return List<OSSObjectSummary>
     */
    public List<OSSObjectSummary> list() {
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliOssConfig.getBucketName()).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    /**
     * 删除文件
     * @param objectName 文件名
     * @return Result<FileManageVO>
     */
    public Result<FileManageVO> delete(String objectName) {
        // 根据BucketName,objectName删除文件
        ossClient.deleteObject(aliOssConfig.getBucketName(), objectName);
        FileManageVO fileManageVO = new FileManageVO();
        fileManageVO.setUrl(objectName);
        fileManageVO.setStatus("removed");
        fileManageVO.setResponse("success");
        return Result.success(fileManageVO);
    }

    /**
     * 下载文件
     * @param os 输出流
     * @param objectName 文件名
     * @throws IOException io异常
     */
    public void exportOssFile(OutputStream os, String objectName) throws IOException {
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(aliOssConfig.getBucketName(), objectName);
        // 读取文件内容。
        BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        out.flush();
        out.close();
        in.close();
    }

}
