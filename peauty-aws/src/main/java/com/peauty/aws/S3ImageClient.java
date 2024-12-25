package com.peauty.aws;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3ImageClient {

    private final S3Client s3Client;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.s3-bucket-name}")
    private String s3BucketName;

    public String uploadImageToS3(MultipartFile image) {
        String key = "images/" + UUID.randomUUID() + "." + getFileExtension(image.getOriginalFilename());
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3BucketName)
                .key(key)
                .contentType(image.getContentType())
                .build();
        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.getBytes()));
        } catch (IOException e) {
            throw new PeautyException(PeautyResponseCode.IMAGE_UPLOAD_FAIl);
        }
        return generateImageUrl(key);
    }

    public List<String> uploadImagesToS3(List<MultipartFile> images) {
        List<String> uploadedUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                String imageUrl = uploadImageToS3(image);
                uploadedUrls.add(imageUrl);
            } catch (PeautyException e) {
                throw new PeautyException(PeautyResponseCode.IMAGE_UPLOAD_FAIl);
            }
        }
        return uploadedUrls;
    }

    private String generateImageUrl(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                s3BucketName,
                Region.of(region).id(),
                key
        );
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "png";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
