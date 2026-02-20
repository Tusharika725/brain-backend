package com.brainrot.backend.dto;

public class CaptionImageDto {
    private String imageId;
    private String imageUrl;

    public CaptionImageDto(String imageId,String imageUrl){
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }

    public String getImageId() {
        return imageId;
    }
    public String getImageUrl(){
        return imageUrl;
    }
}
