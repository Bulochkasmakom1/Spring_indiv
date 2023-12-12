package com.example.crm.services;

import com.example.crm.data.entities.Participant;
import com.example.crm.data.repositories.ParticipantRepository;
import com.example.crm.data.specifications.ParticipantDatatableFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public Page<Participant> getParticipantsForDatatable(String queryString, Pageable pageable) {

        ParticipantDatatableFilter participantDatatableFilter = new ParticipantDatatableFilter(queryString);

        return participantRepository.findAll(participantDatatableFilter, pageable);
    }
}
