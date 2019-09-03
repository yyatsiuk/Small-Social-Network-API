package com.yyatsiuk.social.sonet.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.repository.ImageRepo;
import com.yyatsiuk.social.sonet.service.AmazonS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;


@PropertySource("classpath:security_resource/S3.properties")
@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3ServiceImpl.class);

    @Value("${aws.endpointUrl}")
    private String endPointUrl;

    @Value("${aws.bucketName}")
    private String bucketName;

    private final AmazonS3 s3client;
    private final ImageRepo imageRepo;

    @Autowired
    public AmazonS3ServiceImpl(AmazonS3 s3client, ImageRepo imageRepo) {
        this.s3client = s3client;
        this.imageRepo = imageRepo;
    }

    @Override
    public URL uploadFileToS3(String fileName, String userId, MultipartFile multipartFile) throws AmazonS3Exception {

        try {
            File file = convertMultiPartToFile(multipartFile);

            String fileKey = userId + "/" + fileName;

            URL url = s3client.getUrl(bucketName, fileKey);
            if (url == null) {
                s3client.putObject(bucketName, fileKey, file);
            } else {
                fileKey = userId + "/" + generateRandomString();
                s3client.putObject(bucketName, fileKey, file);
            }

            boolean isDeleted = file.delete();

            if (!isDeleted) {
                logger.warn("Cannot delete file after " +
                        "convert it from MultipartFile to File");
            }

            URL resultUrl = s3client.getUrl(bucketName, fileKey);

            Image image = new Image();
            image.setUrl(resultUrl.toString());
            image.setStatus(Status.ACTIVE);

            imageRepo.save(image);

            return resultUrl;

        } catch (AmazonClientException e) {
            throw new AmazonS3Exception(e.getMessage());
        }
    }

    @Async
    @Override
    public void deleteFileFromS3(String fileKey) throws AmazonS3Exception {

        try {
            s3client.deleteObject(new DeleteObjectRequest(bucketName, fileKey));
        } catch (Exception e) {
            throw new AmazonS3Exception("Error while deleting file: " + fileKey, e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws AmazonS3Exception {

        if (file == null) {
            throw new IllegalArgumentException("File for uploading cannot be null");
        }

        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {

            fos.write(file.getBytes());
            fos.flush();

            return convertedFile;

        } catch (IOException e) {
            throw new AmazonS3Exception("Error while saving file");
        }
    }

    private static String generateRandomString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
