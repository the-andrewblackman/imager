package com.image.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageEntity {
    private String imageUrl;
    private String description;
}
