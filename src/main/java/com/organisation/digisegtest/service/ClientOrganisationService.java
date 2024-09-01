package com.organisation.digisegtest.service;

import com.organisation.digisegtest.model.ClientOrganisation;
import com.organisation.digisegtest.repo.ClientOrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientOrganisationService {

    @Autowired
    private ClientOrganisationRepository clientOrganisationRepository;

    public ClientOrganisationService(ClientOrganisationRepository clientOrganisationRepository) {
        this.clientOrganisationRepository = clientOrganisationRepository;
    }
    public List<ClientOrganisation> getAllClientOrganisations() {
        return clientOrganisationRepository.findAll();
    }

//    public ClientOrganisation createClientOrganisation(ClientOrganisation clientOrganisation) {
//        return clientOrganisationRepository.save(clientOrganisation);
//    }
    @Transactional
    public ClientOrganisation createClientOrganisation(ClientOrganisation clientOrganisation) {
        // Automatically disable if the expiry date is past
        if (clientOrganisation.getExpiryDate().isBefore(LocalDate.now())) {
            clientOrganisation.setEnabled(false);
        }
        return clientOrganisationRepository.save(clientOrganisation);
    }

    public ClientOrganisation updateClientOrganisation(ClientOrganisation updatedClientOrganisation) {
        return clientOrganisationRepository.findById(updatedClientOrganisation.getId()).map(clientOrganisation -> {
            clientOrganisation.setName(updatedClientOrganisation.getName());
            clientOrganisation.setRegistrationDate(updatedClientOrganisation.getRegistrationDate());
            clientOrganisation.setExpiryDate(updatedClientOrganisation.getExpiryDate());
            clientOrganisation.setEnabled(updatedClientOrganisation.isEnabled());
            return clientOrganisationRepository.save(clientOrganisation);
        }).orElseThrow(() -> new RuntimeException("Client Organisation not found"));
    }

    public void deleteClientOrganisation(Long id) {
        clientOrganisationRepository.deleteById(id);
    }

    public long countEnabledOrganisations() {
        return clientOrganisationRepository.countByEnabledTrue();
    }

    public List<ClientOrganisation> findExpiringSoon() {
        LocalDate today = LocalDate.now();
        LocalDate warningDate = today.plusDays(7);
        return clientOrganisationRepository.findAll().stream()
                .filter(org -> org.getExpiryDate().isBefore(today) || org.getExpiryDate().isBefore(warningDate))
                .toList();
    }
    @Transactional
    public void disableExpiredOrganisations() {
        List<ClientOrganisation> expiredOrganisations = clientOrganisationRepository.findAll().stream()
                .filter(org -> org.getExpiryDate().isBefore(LocalDate.now()) && org.isEnabled())
                .toList();

        expiredOrganisations.forEach(org -> org.setEnabled(false));
        clientOrganisationRepository.saveAll(expiredOrganisations);
    }

}
