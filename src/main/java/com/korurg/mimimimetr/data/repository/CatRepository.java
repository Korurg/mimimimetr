package com.korurg.mimimimetr.data.repository;

import com.korurg.mimimimetr.data.entity.CatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CatRepository extends JpaRepository<CatEntity, Long> {

    @Query(value = "select * from cat order by score desc limit :count", nativeQuery = true)
    List<CatEntity> getTopCats(@Param("count") int count);

    @Modifying
    @Transactional
    @Query(value = "update cat set score = score+1 where id=:id", nativeQuery = true)
    void incrementRating(@Param("id") long catId);

    @Query(value = "select * from cat where id not in (:cats) or :cats is null order by random() limit 2", nativeQuery = true)
    List<CatEntity> getNewPairs(@Param("cats") List<Long> showedCats);
}
