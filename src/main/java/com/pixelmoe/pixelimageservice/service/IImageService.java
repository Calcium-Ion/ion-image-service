package com.pixelmoe.pixelimageservice.service;

import com.pixelmoe.pixelimageservice.bean.S3File;
import com.pixelmoe.pixelimageservice.dto.S3FileDTO;
import org.springframework.stereotype.Service;

@Service
public interface IImageService {

    S3File getS3File(S3FileDTO image);

    String getMCIconBse64(S3FileDTO image, String itemID);
}
