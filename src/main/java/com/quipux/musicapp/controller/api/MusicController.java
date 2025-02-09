package com.quipux.musicapp.controller.api;

import com.quipux.musicapp.controller.out.ReproductionListRecord;
import org.springframework.data.domain.Page;



public interface MusicController {


    public ReproductionListRecord saveReproductionList(ReproductionListRecord reproductionListRecord);

    public Page<ReproductionListRecord> listReproductionList(int page, int size);

    public void deleteReproductionList(String id);
}
