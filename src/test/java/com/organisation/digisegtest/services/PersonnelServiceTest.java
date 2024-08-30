package com.organisation.digisegtest.services;

import static org.junit.jupiter.api.Assertions.*;

import com.organisation.digisegtest.model.Personnel;
import com.organisation.digisegtest.repo.PersonnelRepository;
import com.organisation.digisegtest.service.PersonnelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@SpringJUnitConfig
class PersonnelServiceTest {

    @Mock
    private PersonnelRepository personnelRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PersonnelService personnelService;

    private Personnel personnel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personnel = new Personnel();
        personnel.setId(1L);
        personnel.setFirstName("John");
        personnel.setLastName("Doe");
        personnel.setUsername("johndoe");
        personnel.setEmail("johndoe@example.com");
        personnel.setPassword("password123");
        personnel.setTelephoneNumber("1234567890");
    }

    @Test
    void testGetPersonnelByClientOrganisation() {
        when(personnelRepository.findByClientOrganisationId(anyLong())).thenReturn(List.of(personnel));

        List<Personnel> personnelList = personnelService.getPersonnelByClientOrganisation(1L);
        assertFalse(personnelList.isEmpty());
        assertEquals("John", personnelList.get(0).getFirstName());
    }

    @Test
    void testCreatePersonnel() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(personnelRepository.save(any(Personnel.class))).thenReturn(personnel);

        Personnel createdPersonnel = personnelService.createPersonnel(personnel);
        assertNotNull(createdPersonnel);
        assertEquals("encodedPassword", createdPersonnel.getPassword());
    }

//    @Test
//    void testUpdatePersonnel() {
//        when(personnelRepository.findById(anyLong())).thenReturn(Optional.of(personnel));
//        when(personnelRepository.save(any(Personnel.class))).thenReturn(personnel);
//
//        Personnel updatedPersonnel = new Personnel();
//        updatedPersonnel.setFirstName("Jane");
//
//        Personnel savedPersonnel = personnelService.updatePersonnel( updatedPersonnel);
//        assertEquals("Jane", savedPersonnel.getFirstName());
//    }

    @Test
    void testUpdatePersonnel() {
        // Create and set up the original personnel
        Personnel originalPersonnel = new Personnel();
        originalPersonnel.setId(1L);
        originalPersonnel.setFirstName("John");
        originalPersonnel.setLastName("Doe");

        // Mock the repository to return the original personnel when findById is called
        when(personnelRepository.findById(1L)).thenReturn(Optional.of(originalPersonnel));

        // Set up the updated personnel data
        Personnel updatedPersonnel = new Personnel();
        updatedPersonnel.setId(1L);  // Set the same id as the original personnel
        updatedPersonnel.setFirstName("Jane");

        // Mock save method
        when(personnelRepository.save(any(Personnel.class))).thenReturn(originalPersonnel);

        // Call the service method to update personnel
        Personnel savedPersonnel = personnelService.updatePersonnel(updatedPersonnel);

        // Assert that the first name was updated
        assertEquals("Jane", savedPersonnel.getFirstName());
    }
    @Test
    void testDeletePersonnel() {
        doNothing().when(personnelRepository).deleteById(anyLong());

        personnelService.deletePersonnel(1L);
        verify(personnelRepository, times(1)).deleteById(1L);
    }
}
