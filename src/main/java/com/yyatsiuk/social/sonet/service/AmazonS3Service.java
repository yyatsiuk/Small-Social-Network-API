package com.yyatsiuk.social.sonet.service;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;


public interface AmazonS3Service {

    URL uploadFileToS3(String fileName, String userId, MultipartFile multipartFile) throws AmazonS3Exception;

    void deleteFileFromS3(String fileKey) throws AmazonS3Exception;

}
