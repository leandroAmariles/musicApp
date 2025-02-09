package com.quipux.musicapp.service;

import com.quipux.musicapp.controller.out.ReproductionListRecord;
import org.springframework.data.domain.Page;

public interface IMusicService {


        public ReproductionListRecord saveReproductionList(ReproductionListRecord reproductionListRecord);

        public Page<ReproductionListRecord> listReproductionList(int page, int size);

        public void deleteReproductionList(String id);
}
