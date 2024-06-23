package com.sample.music.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AliOssConfig {
    // Endpoint:华南广州
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    // 从环境变量中获取访问凭证。确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    // EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    // 填写Bucket名称，例如examplebucket。
    private String bucketName;
    @Value("${aliyun.oss.urlPrefix}")
    private String urlPrefix;
    @Value("${aliyun.oss.servlet.multipart.max-file-size}")
    private String maxFileSize;
    @Value("${aliyun.oss.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
