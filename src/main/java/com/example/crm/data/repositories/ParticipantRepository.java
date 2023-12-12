package com.example.crm.data.repositories;

import com.example.crm.data.entities.Participant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends PagingAndSortingRepository<Participant, Long>, JpaSpecificationExecutor<Participant> {
}
