package com.organisation.digisegtest.controllers;


import com.organisation.digisegtest.controller.ClientOrganisationController;
import com.organisation.digisegtest.model.ClientOrganisation;
import com.organisation.digisegtest.service.ClientOrganisationService;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ClientOrganisationControllerTest {

    @Mock
    private ClientOrganisationService clientOrganisationService;

    @InjectMocks
    private ClientOrganisationController clientOrganisationController;


    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListClientOrganisations() {
        ClientOrganisation organisation = new ClientOrganisation();
        organisation.setId(1L);
        organisation.setName("Test Organisation");

        when(clientOrganisationService.getAllClientOrganisations())
                .thenReturn(Collections.singletonList(organisation));

        String viewName = clientOrganisationController.listClientOrganisations(model);
        verify(model).addAttribute("organisations", Collections.singletonList(organisation));
        assertThat(viewName).isEqualTo("client_organisations/list");
    }

    @Test
    public void testShowCreateForm() {
        String viewName = clientOrganisationController.showCreateForm(model);
        verify(model).addAttribute(eq("clientOrganisation"), any(ClientOrganisation.class));
        assertThat(viewName).isEqualTo("client_organisations/create");
    }

//    @Test
//    public void testCreateClientOrganisation() {
//        ClientOrganisation organisation = new ClientOrganisation();
//        organisation.setName("New Organisation");
//
//        String viewName = clientOrganisationController.createClientOrganisation(organisation);
//        verify(clientOrganisationService).createClientOrganisation(organisation);
//        assertThat(viewName).isEqualTo("redirect:/client-organisations");
//    }
//    @Test
//    public void testCreateClientOrganisation() {
//        ClientOrganisation organisation = new ClientOrganisation();
//        organisation.setName("New Organisation");
//
//        when(bindingResult.hasErrors()).thenReturn(false);
//
//        String viewName = clientOrganisationController.createClientOrganisation(organisation, bindingResult, model);
//        verify(clientOrganisationService).createClientOrganisation(organisation);
//        assertThat(viewName).isEqualTo("redirect:/client-organisations");
//    }
    @Test
    public void testCreateClientOrganisation() {
        ClientOrganisation organisation = new ClientOrganisation();
        organisation.setName("New Organisation");

        when(bindingResult.hasErrors()).thenReturn(false);  // Simulate no validation errors

        String viewName = clientOrganisationController.createClientOrganisation(organisation, bindingResult, model);
        verify(clientOrganisationService).createClientOrganisation(organisation);
        assertThat(viewName).isEqualTo("redirect:/client-organisations");
    }

    @Test
    public void testShowEditForm() {
        Long id = 1L;
        ClientOrganisation organisation = new ClientOrganisation();
        organisation.setId(id);
        organisation.setName("Existing Organisation");

        when(clientOrganisationService.getAllClientOrganisations())
                .thenReturn(Collections.singletonList(organisation));

        String viewName = clientOrganisationController.showEditForm(id, model);
        verify(model).addAttribute("clientOrganisation", organisation);
        assertThat(viewName).isEqualTo("client_organisations/edit");
    }

    @Test
    public void testUpdateClientOrganisation() {
        Long id = 1L;
        ClientOrganisation organisation = new ClientOrganisation();
        organisation.setId(id);
        organisation.setName("Updated Organisation");

        String viewName = clientOrganisationController.updateClientOrganisation( organisation);
        verify(clientOrganisationService).updateClientOrganisation(organisation);
        assertThat(viewName).isEqualTo("redirect:/client-organisations");
    }

    @Test
    public void testDeleteClientOrganisation() {
        Long id = 1L;

        doNothing().when(clientOrganisationService).deleteClientOrganisation(id);

        String viewName = clientOrganisationController.deleteClientOrganisation(id);
        verify(clientOrganisationService).deleteClientOrganisation(id);
        assertThat(viewName).isEqualTo("redirect:/client-organisations");
    }

    @Test
    public void testCountEnabledOrganisations() {
        long count = 5L;

        when(clientOrganisationService.countEnabledOrganisations()).thenReturn(count);

        String viewName = clientOrganisationController.countEnabledOrganisations(model);
        verify(model).addAttribute("enabledCount", count);
        assertThat(viewName).isEqualTo("client_organisations/home");
    }

    @Test
    public void testFindExpiringSoon() {
        ClientOrganisation organisation = new ClientOrganisation();
        organisation.setId(1L);
        organisation.setName("Expiring Soon");

        when(clientOrganisationService.findExpiringSoon())
                .thenReturn(Collections.singletonList(organisation));

        String viewName = clientOrganisationController.findExpiringSoon(model);
        verify(model).addAttribute("expiringSoon", Collections.singletonList(organisation));
        assertThat(viewName).isEqualTo("client_organisations/expiring_soon");
    }
}
