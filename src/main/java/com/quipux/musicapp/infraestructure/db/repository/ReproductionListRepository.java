package com.quipux.musicapp.infraestructure.db.repository;


import com.quipux.musicapp.infraestructure.db.entity.ReproductionList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReproductionListRepository extends JpaRepository<ReproductionList, Long> {


    Optional<ReproductionList> findByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);

}
