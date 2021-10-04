package com.korurg.mimimimetr.service.api;

import com.korurg.mimimimetr.data.entity.CatEntity;
import com.korurg.mimimimetr.exception.NotEnoughCatException;
import org.springframework.data.util.Pair;

import java.util.List;

public interface CatService {

    List<CatEntity> getTopCats(int count);

    CatEntity createCat(CatEntity cat);

    Pair<CatEntity, CatEntity> getNewPairCats(List<Long> showedCats) throws NotEnoughCatException;

    void incrementRating(long catId);

    CatEntity getById(Long catId);
}
