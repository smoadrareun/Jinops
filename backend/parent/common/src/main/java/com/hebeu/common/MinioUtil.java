package com.hebeu.common;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: MinIOUtil
 * @Author: Smoadrareun
 * @Description: TODO MinIO工具类
 */

@Slf4j
@Component
public class MinioUtil {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Resource
    private MinioClient minioClient;

    /**
     * description: 判断bucket是否存在，不存在则创建
     *
     * @return: void
     */
    public void existBucket(String name) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
            if (!exists) {
                makeBucket(name);
                createBucketPolicy(null, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出所有存储bucket
     *
     * @return List<Bucket>
     */
    public List<Bucket> listBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean
     */
    public Boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket
     *
     * @param bucketName 存储bucket名称
     * @return Boolean
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * description: 上传文件
     *
     * @param multipartFile
     * @return: java.lang.String
     */
    public List<String> upload(String bucketName, MultipartFile[] multipartFile) {
        log.info("文件上传请求开始，请求参数：{},{}", bucketName, multipartFile);
        existBucket(bucketName);
        List<String> names = new ArrayList<>();
        if (multipartFile == null) {
            log.info("上传的文件信息为空");
            return names;
        }
        for (MultipartFile file : multipartFile) {
            String fileName = file.getOriginalFilename();
            log.info("正在上传文件：{}", fileName);
            String[] split = new String[0];
            if (fileName != null) {
                split = fileName.split("\\.");
            }
            if (split.length > 1) {
                fileName = IDUtil.getUUID() + "." + split[split.length - 1];
            } else {
                fileName = IDUtil.getUUID();
            }
            InputStream in = null;
            try {
                in = file.getInputStream();
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(in, in.available(), -1)
                        .contentType(file.getContentType())
                        .build()
                );
                String newFileName = endpoint + "/" + bucketName + "/" + fileName;
                log.info("文件上传成功：{}", newFileName);
                names.add(newFileName);
            } catch (Exception e) {
                log.error("文件上传失败", e);
                return null;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return names;
    }

    /**
     * description: 下载文件
     *
     * @param fileName
     * @return: org.springframework.http.ResponseEntity<byte [ ]>
     */
    public ResponseEntity<byte[]> download(String bucketName, String fileName) {
        ResponseEntity<byte[]> responseEntity = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            //封装返回值
            byte[] bytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            try {
                headers.add("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            headers.setContentLength(bytes.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setAccessControlExposeHeaders(List.of("*"));
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }

    /**
     * 查看文件对象
     *
     * @param bucketName 存储bucket名称
     * @return 存储bucket内文件对象信息
     */
    public List<Item> listObjects(String bucketName) {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).build());
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                Item item = result.get();
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return items;
    }

    /**
     * 批量删除文件对象
     *
     * @param bucketName 存储bucket名称
     * @param objects    对象名称集合
     */
    public Boolean removeObjects(String bucketName, List<String> objects) {
        Boolean aBoolean = true;
        List<DeleteObject> dos = objects.stream().map(DeleteObject::new).collect(Collectors.toList());
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
        try {
            for (Result<DeleteError> result : results) {
                final DeleteError deleteError = result.get();
                log.info("Error in deleting object " + deleteError.objectName() + "; " + deleteError.message());
                aBoolean = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return aBoolean;
    }

    /**
     * 文件访问路径
     *
     * @return String
     */
    public String getObjectUrl(String bucketName, String fileName) {
        String url = "";
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(60 * 60 * 24)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    /**
     * 设置桶策略
     *
     * @param stringBuilder 策略json
     * @param bucketName    桶名称
     */
    public Bucket createBucketPolicy(StringBuilder stringBuilder, String bucketName) {
        try {
            if (ObjectUtils.isEmpty(stringBuilder)) {
                stringBuilder = defaultBucketPolicy(bucketName);
            }
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(stringBuilder.toString()).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取默认桶策略
     *
     * @param bucketName
     * @return
     */
    private static StringBuilder defaultBucketPolicy(String bucketName) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n" +
                " \"Version\": \"2012-10-17\",\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Action\": [\n" +
//                "                \"s3:ListAllMyBuckets\",\n" +
//                "                \"s3:ListBucket\",\n" +
//                "                \"s3:GetBucketLocation\",\n" +
                "                \"s3:GetObject\",\n" +
                "                \"s3:PutObject\",\n" +
                "                \"s3:DeleteObject\"\n" +
                "      ],\n" +
                "      \"Principal\":\"*\",\n" +
                "      \"Resource\": [\n" +
                "        \"arn:aws:s3:::" + bucketName + "/*\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}");
        return builder;
    }

    /**
     * 查看桶策略
     *
     * @param bucketName
     * @return
     */
    public String queryBucketPolicy(String bucketName) {
        String bucketPolicy = null;
        try {
            bucketPolicy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bucketPolicy;
    }
}
