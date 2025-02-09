package com.quipux.musicapp.service.impl;

import com.quipux.musicapp.controller.out.ReproductionListRecord;
import com.quipux.musicapp.service.IMusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements IMusicService {

    @Value("${kafka.topic.saveReproductionList}")
    private String saveReproductionListTopic;

    @Value("${kafka.topic.deleteReproductionList}")
    private String deleteReproductionListTopic;

    @Value("${kafka.topic.listReproductionList}")
    private String listReproductionListTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public ReproductionListRecord saveReproductionList(ReproductionListRecord reproductionListRecord) {

        return null;
    }

    @Override
    public Page<ReproductionListRecord> listReproductionList(int page, int size) {
        return null;
    }

    @Override
    public void deleteReproductionList(String id) {

    }
}
