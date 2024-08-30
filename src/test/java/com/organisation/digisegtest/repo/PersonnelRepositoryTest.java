package com.organisation.digisegtest.repo;

import static org.junit.jupiter.api.Assertions.*;

import com.organisation.digisegtest.model.ClientOrganisation;
import com.organisation.digisegtest.model.Personnel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PersonnelRepositoryTest {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private ClientOrganisationRepository clientOrganisationRepository;

    @BeforeEach
    public void setUp() {
        // Clear repositories before each test
        personnelRepository.deleteAll();
        clientOrganisationRepository.deleteAll();
    }

    private ClientOrganisation getOrCreateClientOrganisation(String organisationName) {
        Optional<ClientOrganisation> existingOrganisation = Optional.ofNullable(clientOrganisationRepository.findByName(organisationName));
        if (existingOrganisation.isPresent()) {
            return existingOrganisation.get();
        } else {
            ClientOrganisation clientOrganisation = new ClientOrganisation();
            clientOrganisation.setName(organisationName);
            clientOrganisation.setRegistrationDate(LocalDate.now());
            clientOrganisation.setExpiryDate(LocalDate.now().plusYears(1));
            clientOrganisation.setEnabled(true);
            return clientOrganisationRepository.save(clientOrganisation);
        }
    }
    @Test
    public void testFindByClientOrganisationId() {
        // Use the helper method to get or create the ClientOrganisation
        ClientOrganisation clientOrganisation = getOrCreateClientOrganisation("Test Organisation");

        // Create and save Personnel linked to the ClientOrganisation
        Personnel personnel1 = new Personnel();
        personnel1.setFirstName("John");
        personnel1.setLastName("Doe");
        personnel1.setUsername("johndoe");
        personnel1.setPassword("password123");
        personnel1.setEmail("johndoe@example.com");
        personnel1.setTelephoneNumber("1234567890");
        personnel1.setClientOrganisation(clientOrganisation);
        personnelRepository.save(personnel1);

        Personnel personnel2 = new Personnel();
        personnel2.setFirstName("Jane");
        personnel2.setLastName("Doe");
        personnel2.setUsername("janedoe");
        personnel2.setPassword("password123");
        personnel2.setEmail("janedoe@example.com");
        personnel2.setTelephoneNumber("0987654321");
        personnel2.setClientOrganisation(clientOrganisation);
        personnelRepository.save(personnel2);

        // Retrieve Personnel by ClientOrganisation ID
        List<Personnel> personnelList = personnelRepository.findByClientOrganisationId(clientOrganisation.getId());

        // Assert that the retrieved personnel match the saved ones
        assertThat(personnelList).isNotEmpty();
        assertThat(personnelList.size()).isEqualTo(2);
        assertThat(personnelList).extracting(Personnel::getFirstName).containsExactlyInAnyOrder("John", "Jane");
    }

}
