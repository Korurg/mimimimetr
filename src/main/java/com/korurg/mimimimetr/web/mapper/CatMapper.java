package com.korurg.mimimimetr.web.mapper;

import com.korurg.mimimimetr.data.entity.CatEntity;
import com.korurg.mimimimetr.web.dto.CatDto;
import org.springframework.stereotype.Component;

@Component
public class CatMapper {

    public CatEntity toCatEntity(CatDto cat) {
        return CatEntity.builder()
                .id(cat.getId())
                .imageUrl(cat.getImageUrl())
                .score(cat.getScore())
                .name(cat.getName())
                .build();
    }

    public CatDto toCatDto(CatEntity cat) {
        return CatDto.builder()
                .id(cat.getId())
                .imageUrl(cat.getImageUrl())
                .score(cat.getScore())
                .name(cat.getName())
                .build();
    }
}
