package com.peauty.addy.dalle;

import java.util.List;

public record DalleEditImageResponse(
        Long created,
        List<ImageData> data
) {
    public record ImageData(
            String url
    ) {
    }
}
