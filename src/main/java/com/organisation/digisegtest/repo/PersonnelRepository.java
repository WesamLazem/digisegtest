package com.organisation.digisegtest.repo;

import com.organisation.digisegtest.model.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    List<Personnel> findByClientOrganisationId(Long clientOrganisationId);
}

