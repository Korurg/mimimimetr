package com.korurg.mimimimetr.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CatDto {
    private long id;

    private String name;

    private String imageUrl;

    private int score;
}
