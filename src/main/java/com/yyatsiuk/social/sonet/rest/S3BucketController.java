package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.OperationName;
import com.yyatsiuk.social.sonet.dto.OperationStatus;
import com.yyatsiuk.social.sonet.dto.response.OperationStatusResponse;
import com.yyatsiuk.social.sonet.service.AmazonS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@RequestMapping("api/users")
public class S3BucketController {

    private AmazonS3Service s3Service;

    @Autowired
    public S3BucketController(AmazonS3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/{userId}/files")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity uploadFile(@RequestPart(value = "file") MultipartFile file, @PathVariable Long userId) {

      URL url = this.s3Service.uploadFileToS3(file.getOriginalFilename(),String.valueOf(userId),file);

       OperationStatusResponse response = new OperationStatusResponse(
                OperationName.UPLOAD_FILE.name(),
                OperationStatus.SUCCESS.name(),
                url.toString()
       );

       return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{userId}/files/{fileName}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity deleteFile(@PathVariable("userId") Long userId, @PathVariable String fileName){
        s3Service.deleteFileFromS3(userId + "/" + fileName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
