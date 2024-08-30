package com.organisation.digisegtest.service;

import com.organisation.digisegtest.model.Personnel;
import com.organisation.digisegtest.repo.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Personnel> getPersonnelByClientOrganisation(Long clientOrganisationId) {
        return personnelRepository.findByClientOrganisationId(clientOrganisationId);
    }

   public Personnel createPersonnel(Personnel personnel) {
        personnel.setPassword(passwordEncoder.encode(personnel.getPassword()));
        return personnelRepository.save(personnel);
    }
//   public Personnel createPersonnel(Personnel personnel) {
//       // Store the password as plain text (no encoding)
//       personnel.setPassword("test");
//       return personnelRepository.save(personnel);
//   }

    public Personnel updatePersonnel(Personnel updatedPersonnel) {
        return personnelRepository.findById(updatedPersonnel.getId()).map(personnel -> {
            personnel.setFirstName(updatedPersonnel.getFirstName());
            personnel.setLastName(updatedPersonnel.getLastName());
            personnel.setEmail(updatedPersonnel.getEmail());
            personnel.setTelephoneNumber(updatedPersonnel.getTelephoneNumber());
            return personnelRepository.save(personnel);
        }).orElseThrow(() -> new RuntimeException("Personnel not found"));
    }

    public void deletePersonnel(Long id) {
        personnelRepository.deleteById(id);
    }
}
