package com.organisation.digisegtest.repo;

import com.organisation.digisegtest.model.ClientOrganisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOrganisationRepository extends JpaRepository<ClientOrganisation, Long> {
    ClientOrganisation findByName(String name);
    long countByEnabledTrue();
}
