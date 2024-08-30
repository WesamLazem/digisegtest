package com.organisation.digisegtest.services;

import static org.junit.jupiter.api.Assertions.*;

import com.organisation.digisegtest.model.ClientOrganisation;
import com.organisation.digisegtest.repo.ClientOrganisationRepository;
import com.organisation.digisegtest.service.ClientOrganisationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.Arrays;




@SpringBootTest
@SpringJUnitConfig
class ClientOrganisationServiceTest {

    @Mock
    private ClientOrganisationRepository clientOrganisationRepository;

    @InjectMocks
    private ClientOrganisationService clientOrganisationService;

    private ClientOrganisation clientOrganisation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientOrganisation = new ClientOrganisation();
        clientOrganisation.setId(1L);
        clientOrganisation.setName("Test Org");
        clientOrganisation.setRegistrationDate(LocalDate.now());
        clientOrganisation.setExpiryDate(LocalDate.now().plusDays(30));
        clientOrganisation.setEnabled(true);
    }

    @Test
    void testGetAllClientOrganisations() {
        when(clientOrganisationRepository.findAll()).thenReturn(List.of(clientOrganisation));

        List<ClientOrganisation> organisations = clientOrganisationService.getAllClientOrganisations();
        assertFalse(organisations.isEmpty());
        assertEquals("Test Org", organisations.get(0).getName());
    }

    @Test
    void testCreateClientOrganisation() {
        when(clientOrganisationRepository.save(any(ClientOrganisation.class))).thenReturn(clientOrganisation);

        ClientOrganisation savedOrganisation = clientOrganisationService.createClientOrganisation(clientOrganisation);
        assertNotNull(savedOrganisation);
        assertEquals("Test Org", savedOrganisation.getName());
    }

    @Test
    void testUpdateClientOrganisation()
    {
        when(clientOrganisationRepository.findById(anyLong())).thenReturn(Optional.of(clientOrganisation));
        when(clientOrganisationRepository.save(any(ClientOrganisation.class))).thenReturn(clientOrganisation);

        ClientOrganisation updatedOrganisation = new ClientOrganisation();

        updatedOrganisation.setId(1L);
        updatedOrganisation.setName("Updated Org");


        ClientOrganisation savedOrganisation = clientOrganisationService.updateClientOrganisation(updatedOrganisation);
        assertEquals("Updated Org", savedOrganisation.getName());
    }

    @Test
    void testDeleteClientOrganisation() {
        doNothing().when(clientOrganisationRepository).deleteById(anyLong());

        clientOrganisationService.deleteClientOrganisation(1L);
        verify(clientOrganisationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCountEnabledOrganisations() {
        // Arrange
        when(clientOrganisationRepository.countByEnabledTrue()).thenReturn(3L);

        // Act
        long enabledCount = clientOrganisationService.countEnabledOrganisations();

        // Assert
        assertThat(enabledCount).isEqualTo(3L);
    }

    @Test
    public void testFindExpiringSoon() {
        // Arrange
        LocalDate today = LocalDate.now();
        LocalDate expiringSoonDate = today.plusDays(5);
        LocalDate expiredDate = today.minusDays(1);
        LocalDate notExpiringSoonDate = today.plusDays(10);

        ClientOrganisation org1 = new ClientOrganisation();
        org1.setName("Expiring Soon Organisation");
        org1.setExpiryDate(expiringSoonDate);
        org1.setEnabled(true);

        ClientOrganisation org2 = new ClientOrganisation();
        org2.setName("Expired Organisation");
        org2.setExpiryDate(expiredDate);
        org2.setEnabled(true);

        ClientOrganisation org3 = new ClientOrganisation();
        org3.setName("Not Expiring Soon Organisation");
        org3.setExpiryDate(notExpiringSoonDate);
        org3.setEnabled(true);

        List<ClientOrganisation> organisations = Arrays.asList(org1, org2, org3);
        when(clientOrganisationRepository.findAll()).thenReturn(organisations);

        // Act
        List<ClientOrganisation> expiringSoonOrganisations = clientOrganisationService.findExpiringSoon();

        // Assert
        assertThat(expiringSoonOrganisations).containsExactlyInAnyOrder(org1, org2);
        assertThat(expiringSoonOrganisations).doesNotContain(org3);
    }
}
