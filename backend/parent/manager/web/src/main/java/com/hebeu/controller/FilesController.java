package com.hebeu.controller;

import com.hebeu.common.MinioUtil;
import com.hebeu.common.ResponseBody;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
public class FileController {

    @Resource
    private MinioUtil minioUtil;

    public ResponseBody<Object> resp = new ResponseBody<>();

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public ResponseBody<Object> upload(MultipartFile[] file) {
        log.info("文件上传请求开始，请求参数：{}", (Object[]) file);
        List<String> filename = minioUtil.upload(file);
        if(filename==null){
            return resp.failure(-1,"文件上传失败");
        }else if(filename.size()==0){
            return resp.failure(-404,"上传的文件信息为空");
        }else{
            return resp.success("文件上传成功",filename);
        }
    }

    /**
     * 下载文件
     */
    @RequestMapping("/download/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable String filename) {
        log.info("文件下载请求开始，请求参数：{}", filename);
        ResponseEntity<byte[]> responseEntity = minioUtil.download(filename);
        log.info("文件获取成功，即将开始下载...");
        return responseEntity;
    }

}
