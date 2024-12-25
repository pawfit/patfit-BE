package com.peauty.addy.dalle;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

@Component
public class ImageMaskGenerator {

    public MultipartFile createMask(MultipartFile originalImageFile, Point[] points) throws IOException {
        if (points.length != 4) {
            throw new IllegalArgumentException("정확히 4개의 점이 필요합니다.");
        }

        // 원본 이미지를 BufferedImage로 변환
        BufferedImage originalImage = ImageIO.read(originalImageFile.getInputStream());

        // 원본 이미지와 동일한 크기의 새 이미지 생성
        BufferedImage maskedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        // 원본 이미지를 새 이미지에 복사
        Graphics2D g2d = maskedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);

        // 마스킹할 영역을 투명하게 만들기
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));

        // 점들로 다각형 생성
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            xPoints[i] = points[i].x;
            yPoints[i] = points[i].y;
        }

        // 지정된 영역을 투명하게 만들기
        g2d.fillPolygon(xPoints, yPoints, points.length);
        g2d.dispose();

        // BufferedImage를 MultipartFile로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(maskedImage, "PNG", baos);
        byte[] imageBytes = baos.toByteArray();

        return new MultipartFile() {
            @Override
            public String getName() {
                return "masked_" + originalImageFile.getOriginalFilename();
            }

            @Override
            public String getOriginalFilename() {
                return "masked_" + originalImageFile.getOriginalFilename();
            }

            @Override
            public String getContentType() {
                return "image/png";
            }

            @Override
            public boolean isEmpty() {
                return imageBytes.length == 0;
            }

            @Override
            public long getSize() {
                return imageBytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return imageBytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(imageBytes);
            }

            @Override
            public void transferTo(File dest) throws IOException {
                Files.write(dest.toPath(), imageBytes);
            }
        };
    }
}