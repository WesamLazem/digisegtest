package com.organisation.digisegtest.controller;

import com.organisation.digisegtest.model.ClientOrganisation;
import com.organisation.digisegtest.service.ClientOrganisationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/client-organisations")
public class ClientOrganisationController {

    @Autowired
    private ClientOrganisationService clientOrganisationService;

    public ClientOrganisationController(ClientOrganisationService clientOrganisationService) {
        this.clientOrganisationService = clientOrganisationService;
    }
    @GetMapping
    public String listClientOrganisations(Model model) {
        List<ClientOrganisation> organisations = clientOrganisationService.getAllClientOrganisations();
        model.addAttribute("organisations", organisations);
        return "client_organisations/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("clientOrganisation", new ClientOrganisation());
        return "client_organisations/create";
    }

//    @PostMapping("/create")
//    public String createClientOrganisation(@RequestBody ClientOrganisation clientOrganisation) {
//        clientOrganisationService.createClientOrganisation(clientOrganisation);
//        return "redirect:/client-organisations";
//    }

//    @PostMapping("/create")
//    public String createClientOrganisation(@Valid @RequestBody ClientOrganisation clientOrganisation, BindingResult bindingResult, Model model) {
//        clientOrganisationService.createClientOrganisation(clientOrganisation);
//        return "redirect:/client-organisations";
//    }

    @PostMapping("/create")
    public String createClientOrganisation(@Valid @RequestBody ClientOrganisation clientOrganisation, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "client_organisations/create"; // Return to the form if there are validation errors
        }
        clientOrganisationService.createClientOrganisation(clientOrganisation);
        return "redirect:/client-organisations";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        ClientOrganisation clientOrganisation = clientOrganisationService.getAllClientOrganisations().stream()
                .filter(org -> org.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Client Organisation not found"));
        model.addAttribute("clientOrganisation", clientOrganisation);
        return "client_organisations/edit";
    }

    @PutMapping("/{id}/edit")
    public String updateClientOrganisation(@RequestBody ClientOrganisation clientOrganisation) {
        clientOrganisationService.updateClientOrganisation(clientOrganisation);
        return "redirect:/client-organisations";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteClientOrganisation(@PathVariable Long id) {
        clientOrganisationService.deleteClientOrganisation(id);
        return "redirect:/client-organisations";
    }

    @GetMapping("/enabled/count")
    public String countEnabledOrganisations(Model model) {
        long count = clientOrganisationService.countEnabledOrganisations();
        model.addAttribute("enabledCount", count);
        return "client_organisations/home";
    }

    @GetMapping("/expiring-soon")
    public String findExpiringSoon(Model model) {
        List<ClientOrganisation> expiringSoon = clientOrganisationService.findExpiringSoon();
        model.addAttribute("expiringSoon", expiringSoon);
        return "client_organisations/expiring_soon";
    }
}
