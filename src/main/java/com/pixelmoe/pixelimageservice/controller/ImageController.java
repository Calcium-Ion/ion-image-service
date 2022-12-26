package com.pixelmoe.pixelimageservice.controller;

import cn.hutool.core.img.ImgUtil;
import com.pixelmoe.pixelimageservice.bean.S3File;
import com.pixelmoe.pixelimageservice.dto.S3FileDTO;
import com.pixelmoe.pixelimageservice.service.IImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @Resource
    IImageService imageService;


    /**
     * 访问S3中的图片
     * @param itemID 图片英文id
     * @param request 请求
     * @param response 响应
     * @throws IOException IO异常
     */
    @GetMapping("/getMCItem/{itemID}")
    public void getMCItem(@PathVariable String itemID, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (itemID.contains("/") || itemID.equals("\\")) throw new Exception("ItemID is no valid");
        log.info("=============== Working ===============");
        log.info("Get image from itemID");
        log.info("{}", itemID);
        S3FileDTO s3FileDTO = new S3FileDTO();
        if (!itemID.contains(":")) itemID = "minecraft:" + itemID;

        s3FileDTO.setCategory("minecraft/" + itemID.split(":")[0]);
        s3FileDTO.setFileName("item.json");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + itemID.split(":")[1] + ".png");
        ImgUtil.write(ImgUtil.toImage(imageService.getMCIconBse64(s3FileDTO, itemID)), "png",response.getOutputStream());
        log.info("================= Done =================");
    }

}
