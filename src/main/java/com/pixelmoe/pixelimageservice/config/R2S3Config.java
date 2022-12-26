package com.pixelmoe.pixelimageservice.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class R2S3Config {

    @Value("${s3.endpoint}")
    private String endpoint;

    @Value("${s3.accessKey}")
    private String accessKey;

    @Value("${s3.secretKey}")
    private String secretKey;

    @Bean
    public AmazonS3 amazonS3() {
        //构建S3 Client
        AmazonS3ClientBuilder standard = AmazonS3ClientBuilder.standard();
        standard.setEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(endpoint, "auto"));
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        standard.withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        return standard.build();
    }

}
