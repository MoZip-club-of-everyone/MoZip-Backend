package com.mozip.mozip.global.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String fileName) throws IOException {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));

        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        return s3Client.utilities().getUrl(request).toString();
    }

    public String createFileName(MultipartFile multipartFile){
        String randomName = UUID.randomUUID().toString();
        String originalFileName = multipartFile.getOriginalFilename();

        String dirName = "image";
        String fileName = dirName + "/" + randomName+ "-" + originalFileName;
        return fileName;
    }

    public void delete(String fileName){
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}
