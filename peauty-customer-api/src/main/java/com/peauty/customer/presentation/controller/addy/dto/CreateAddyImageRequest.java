package com.peauty.customer.presentation.controller.addy.dto;

import com.peauty.customer.business.addy.dto.CreateAddyImageCommand;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@Builder
public record CreateAddyImageRequest(
        MultipartFile image,
        int x1, int y1,
        int x2, int y2,
        int x3, int y3,
        int x4, int y4,
        String prompt,
        String bearerToken
) {

    public Point[] getPoints() {
        return new Point[]{
                new Point(x1, y1),
                new Point(x2, y2),
                new Point(x3, y3),
                new Point(x4, y4)
        };
    }
    public static CreateAddyImageCommand toCommand() {
        return CreateAddyImageCommand.builder()
                .build();
    }
}