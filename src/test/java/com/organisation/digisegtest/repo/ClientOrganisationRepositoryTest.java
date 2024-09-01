package com.organisation.digisegtest.repo;


import com.organisation.digisegtest.model.ClientOrganisation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClientOrganisationRepositoryTest {

    @Autowired
    private ClientOrganisationRepository clientOrganisationRepository;
//
//    @BeforeEach
//    public void cleanDatabase() {
//        // Clear the table before each test
//        //clientOrganisationRepository.deleteAll();
//    }

    @Test
    @Transactional
    public void testFindByName() {
        // Arrange
        Optional<ClientOrganisation> existingOrganisation = Optional.ofNullable(clientOrganisationRepository.findByName("Test Organisation"));
        if (!existingOrganisation.isPresent()) {
            ClientOrganisation org = new ClientOrganisation();
            org.setName("Test Organisation");
            org.setRegistrationDate(LocalDate.now());
            org.setExpiryDate(LocalDate.now().plusYears(1));
            org.setEnabled(true);
            clientOrganisationRepository.save(org);
        }

        // Act
        ClientOrganisation foundOrg = clientOrganisationRepository.findByName("Test Organisation");

        // Assert
        assertThat(foundOrg).isNotNull();
        assertThat(foundOrg.getName()).isEqualTo("Test Organisation");
    }

    @Test
    @DirtiesContext
    public void testCountByEnabledTrue() {
        // Arrange
        ClientOrganisation org1 = new ClientOrganisation();
        org1.setName("Unique Enabled Organisation 1");  // Ensure the name is unique
        org1.setRegistrationDate(LocalDate.now());
        org1.setExpiryDate(LocalDate.now().plusYears(1));
        org1.setEnabled(true);

        ClientOrganisation org2 = new ClientOrganisation();
        org2.setName("Unique Disabled Organisation");  // Ensure the name is unique
        org2.setRegistrationDate(LocalDate.now());
        org2.setExpiryDate(LocalDate.now().minusDays(1));
        org2.setEnabled(false);

        clientOrganisationRepository.save(org1);
        clientOrganisationRepository.save(org2);

        // Act
        long enabledCount = clientOrganisationRepository.countByEnabledTrue();

        // Assert
        assertThat(enabledCount).isEqualTo(2L); // Only one enabled organisation

    }

    @Test
    @Transactional
    public void testFindById() {
        // Arrange
        ClientOrganisation org = new ClientOrganisation();
        org.setName("Another Organisation");
        org.setRegistrationDate(LocalDate.now());
        org.setExpiryDate(LocalDate.now().plusYears(1));
        org.setEnabled(true);
        org = clientOrganisationRepository.save(org);

        // Act
        Optional<ClientOrganisation> foundOrg = clientOrganisationRepository.findById(org.getId());

        // Assert
        assertThat(foundOrg).isPresent();
        assertThat(foundOrg.get().getName()).isEqualTo("Another Organisation");
    }
}