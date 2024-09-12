package com.image.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@Builder
public class ImageEntity {
    private String imageUrl;
    private String prompt;
    private String userInfo;
}
