package com.pixelmoe.pixelimageservice.service.impl;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.pixelmoe.pixelimageservice.bean.S3File;
import com.pixelmoe.pixelimageservice.dto.S3FileDTO;
import com.pixelmoe.pixelimageservice.service.IImageService;
import com.pixelmoe.pixelimageservice.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ImageServiceImpl implements IImageService {

    @Value("${s3.bucket}")
    private String bucket;

    @Resource
    AmazonS3 amazonS3;

    @Resource
    IRedisService redisService;

    private boolean checkImageValid(S3FileDTO image) {
        if (image.getCategory() == null || image.getFileName() == null) {
            return false;
        }
        if (image.getCategory().contains("/") || image.getFileName().contains("/")) {
            return false;
        }
        if (image.getCategory().contains("\\") || image.getFileName().contains("\\")) {
            return false;
        }
        return true;
    }

    @Override
    public S3File getS3File(S3FileDTO fileDTO) {
        log.info("Getting file {}", fileDTO);
        S3File origin = new S3File();
        origin.setCategory(fileDTO.getCategory());
        origin.setFileName(fileDTO.getFileName());
        String key = fileDTO.getCategory() + "/" + fileDTO.getFileName();
        S3ObjectInputStream objectContent = amazonS3.getObject(bucket, key).getObjectContent();
        FastByteArrayOutputStream read = IoUtil.read(objectContent);
        JSONObject object = JSONUtil.parseObj(read.toString());
        origin.setFile(object);
        log.info("Get fileDTO from s3 success");
        return origin;
    }

    @Override
    public String getMCIconBse64(S3FileDTO image, String itemID) {
        String redisKey = "minecraft:" + itemID;
        String base64Img = (String) redisService.get(redisKey);
        if (base64Img == null) {
            S3File s3File = getS3File(image);
            base64Img = s3File.getFile().getJSONObject(itemID).getStr("icon");
            if (base64Img == null) {
                throw new NullPointerException();
            } else {
                redisService.set(redisKey, base64Img);
                redisService.setExpire(redisKey, 30, TimeUnit.DAYS);
            }
        }
        return base64Img;
    }
}
