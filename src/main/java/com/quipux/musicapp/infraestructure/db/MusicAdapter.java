package com.quipux.musicapp.infraestructure.db;


import com.quipux.musicapp.infraestructure.db.entity.ReproductionList;

import java.util.List;

public interface MusicAdapter {


    public List<ReproductionList> getReproductionList();


    public ReproductionList findReproductionListByName(String  name);

    public void deleteReproductionList(String name);
}
