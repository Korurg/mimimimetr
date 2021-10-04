package com.korurg.mimimimetr.service.implementation;

import com.korurg.mimimimetr.data.entity.CatEntity;
import com.korurg.mimimimetr.data.repository.CatRepository;
import com.korurg.mimimimetr.exception.NotEnoughCatException;
import com.korurg.mimimimetr.service.api.CatService;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;

    @Override
    public List<CatEntity> getTopCats(int count) {
        return catRepository.getTopCats(count);
    }

    @Override
    public CatEntity createCat(CatEntity cat) {
        return catRepository.save(cat);
    }

    @Override
    public Pair<CatEntity, CatEntity> getNewPairCats(List<Long> showedCats) throws NotEnoughCatException {
        List<CatEntity> cats = catRepository.getNewPairs(showedCats);
        if (cats.size() < 2) {
            throw new NotEnoughCatException();
        }
        return Pair.of(cats.get(0), cats.get(1));
    }

    @Override
    public void incrementRating(long catId) {
        catRepository.incrementRating(catId);
    }

    @Override
    public CatEntity getById(Long catId) {
        return catRepository.getById(catId);
    }
}
