package com.hebeu.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hebeu.common.MinioUtil;
import com.hebeu.common.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: UploadController
 * @Author: Smoadrareun
 * @Description: TODO 文件存储控制层实现类
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/Jinops/files")
public class FilesController {

    @Resource
    private MinioUtil minioUtil;

    public ResponseBody<Object> resp = new ResponseBody<>();

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public ResponseBody<Object> upload(String name, MultipartFile[] files) {
        List<String> filenames = minioUtil.upload(name, files);
        if (filenames == null) {
            return resp.failure(-1, "文件上传失败");
        } else if (filenames.size() == 0) {
            return resp.failure(-404, "上传的文件信息为空");
        } else {
            return resp.success("文件上传成功", filenames);
        }
    }

    /**
     * 下载文件
     */
    @RequestMapping("/download/{name}/{file}")
    public ResponseEntity<byte[]> download(@PathVariable("name") String name,
                                           @PathVariable("file") String file) {
        log.info("文件下载请求开始，请求参数：{},{}", name, file);
        ResponseEntity<byte[]> responseEntity = minioUtil.download(name, file);
        if (responseEntity != null) {
            log.info("文件获取成功，即将开始下载...");
        } else {
            log.error("文件获取失败");
        }
        return responseEntity;
    }

    /**
     * 移除文件
     */
    @RequestMapping("/remove/{name}/{file}")
    public ResponseBody<Object> remove(@PathVariable("name") String name,
                                       @PathVariable("file") String file) {
        log.info("文件移除请求开始，请求参数：{},{}", name, file);
        List<String> list = new ArrayList<>();
        list.add(file);
        Boolean aBoolean = minioUtil.removeObjects(name, list);
        if (aBoolean == null || !aBoolean) {
            return resp.failure(-1, "文件移除失败");
        } else {
            return resp.success("文件移除成功", null);
        }
    }

    /**
     * 获取临时地址
     */
    @RequestMapping("/getUrl/{name}/{file}")
    public ResponseBody<Object> getUrl(@PathVariable("name") String name,
                                       @PathVariable("file") String file) {
        log.info("获取临时地址请求开始，请求参数：{},{}", name, file);
        String url = minioUtil.getObjectUrl(name, file);
        if (ObjectUtils.isEmpty(url)) {
            return resp.failure(-1, "获取临时地址失败");
        } else {
            return resp.success("获取临时地址成功", url);
        }
    }
}
