package com.organisation.digisegtest.controller;

import com.organisation.digisegtest.model.ClientOrganisation;
import com.organisation.digisegtest.model.Personnel;
import com.organisation.digisegtest.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/personnel")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;


    @GetMapping("/client-organisation/{clientOrganisationId}")
    public String getPersonnelByClientOrganisation(@PathVariable Long clientOrganisationId, Model model) {
        List<Personnel> personnelList = personnelService.getPersonnelByClientOrganisation(clientOrganisationId);
        model.addAttribute("personnelList", personnelList);
        return "personnel/list";
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam Long clientOrganisationId, Model model) {
        Personnel personnel = new Personnel();
        personnel.setClientOrganisation(new ClientOrganisation()); // Set default client organisation
        model.addAttribute("personnel", personnel);
        model.addAttribute("clientOrganisationId", clientOrganisationId);
        return "personnel/create";
    }
//@ModelAttribute
    @PostMapping("/create")
    public String createPersonnel(@RequestBody Personnel personnel, @RequestParam Long clientOrganisationId) {
        ClientOrganisation clientOrganisation = new ClientOrganisation(); // Fetch from repository in real implementation
        clientOrganisation.setId(clientOrganisationId);
        personnel.setClientOrganisation(clientOrganisation);
        personnelService.createPersonnel(personnel);
        return "redirect:/personnel/client-organisation/" + clientOrganisationId;
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Personnel personnel = personnelService.getPersonnelByClientOrganisation(id).stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Personnel not found"));
        model.addAttribute("personnel", personnel);
        return "personnel/edit";
    }

    @PutMapping ("/edit")
    public String updatePersonnel(@RequestBody Personnel personnel, @RequestParam Long clientOrganisationId) {
        ClientOrganisation clientOrganisation = new ClientOrganisation();
        clientOrganisation.setId(clientOrganisationId);
        personnel.setClientOrganisation(clientOrganisation);

        personnelService.updatePersonnel(personnel);
        return "redirect:/personnel/client-organisation/" + personnel.getClientOrganisation().getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deletePersonnel(@PathVariable Long id) {
        personnelService.deletePersonnel(id);
        return "personnel/list";
    }

}
