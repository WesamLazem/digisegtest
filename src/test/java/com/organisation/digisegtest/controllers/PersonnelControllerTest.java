package com.organisation.digisegtest.controllers;

import com.organisation.digisegtest.controller.PersonnelController;
import com.organisation.digisegtest.model.ClientOrganisation;
import com.organisation.digisegtest.model.Personnel;
import com.organisation.digisegtest.service.PersonnelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonnelControllerTest {

    @Mock
    private PersonnelService personnelService;

    @InjectMocks
    private PersonnelController personnelController;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPersonnelByClientOrganisation() {
        Long clientOrganisationId = 1L;
        Personnel personnel = new Personnel();
        personnel.setId(1L);
        personnel.setFirstName("John");
        personnel.setLastName("Doe");

        when(personnelService.getPersonnelByClientOrganisation(clientOrganisationId))
                .thenReturn(Collections.singletonList(personnel));

        String viewName = personnelController.getPersonnelByClientOrganisation(clientOrganisationId, model);
        verify(model).addAttribute("personnelList", Collections.singletonList(personnel));
        assertThat(viewName).isEqualTo("personnel/list");
    }

    @Test
    public void testShowCreateForm() {
        Long clientOrganisationId = 1L;
        String viewName = personnelController.showCreateForm(clientOrganisationId, model);
        verify(model).addAttribute(eq("personnel"), any(Personnel.class));
        verify(model).addAttribute("clientOrganisationId", clientOrganisationId);
        assertThat(viewName).isEqualTo("personnel/create");
    }

    @Test
    public void testCreatePersonnel() {
        Long clientOrganisationId = 1L;
        Personnel personnel = new Personnel();
        personnel.setFirstName("John");
        personnel.setLastName("Doe");

        String viewName = personnelController.createPersonnel(personnel, clientOrganisationId);
        verify(personnelService).createPersonnel(personnel);
        assertThat(viewName).isEqualTo("redirect:/personnel/client-organisation/" + clientOrganisationId);
    }

    @Test
    public void testShowEditForm() {
        Long id = 1L;
        Personnel personnel = new Personnel();
        personnel.setId(id);

        when(personnelService.getPersonnelByClientOrganisation(anyLong()))
                .thenReturn(Collections.singletonList(personnel));

        String viewName = personnelController.showEditForm(id, model);
        verify(model).addAttribute("personnel", personnel);
        assertThat(viewName).isEqualTo("personnel/edit");
    }

    @Test
    public void testUpdatePersonnel() {
        Long id = 1L;
        Personnel personnel = new Personnel();
        personnel.setId(id);
        personnel.setFirstName("John");
        personnel.setLastName("Doe");

        String viewName = personnelController.updatePersonnel(personnel, 2L);
        verify(personnelService).updatePersonnel(personnel);
        assertThat(viewName).isEqualTo("redirect:/personnel/client-organisation/" + 2);
    }


    @Test
    public void testDeletePersonnel() {
        Long id = 1L;

        doNothing().when(personnelService).deletePersonnel(id);

        String viewName = personnelController.deletePersonnel(id);
        verify(personnelService).deletePersonnel(id);
        assertThat(viewName).isEqualTo("personnel/list");
    }
}
