package com.sl.ms.web.manager.controller.base.common;

import com.sl.transport.common.util.AliOSSUtil;
import com.sl.transport.common.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传服务
 */
@Slf4j
@RestController
@RequestMapping("/files")
@Api(value = "File", tags = "文件上传相关接口")
public class FileUploadController {

    @Resource
    private AliOSSUtil aliOSSUtil;

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public R<String> upload(@RequestPart("file") MultipartFile file) throws IOException {
        //获得原始文件名
        String originalFilename = file.getOriginalFilename();
        //获得文件扩展名
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + extension;

        String filePath = aliOSSUtil.upload(file.getBytes(), fileName);

        return R.success(filePath);
    }

    @ApiOperation(value = "图片上传")
    @PostMapping("/imageUpload")
    public R<String> imageUpload(@RequestPart("file") MultipartFile file) {
        String url = null;
        try {
            url = aliOSSUtil.uploadImage(file);
        } catch (IOException e) {
            log.error("文件上传失败，异常信息：", e);
        }
        return R.success(url);
    }
}
