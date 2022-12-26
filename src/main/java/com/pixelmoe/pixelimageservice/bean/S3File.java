package com.pixelmoe.pixelimageservice.bean;

import cn.hutool.json.JSONObject;
import lombok.Data;


@Data
public class S3File {

    String category;

    String fileName;

    JSONObject file;
}
