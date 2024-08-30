package com.organisation.digisegtest.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "client_organisation")
public class ClientOrganisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate registrationDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "clientOrganisation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Personnel> personnel = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Personnel> getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Set<Personnel> personnel) {
        this.personnel = personnel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientOrganisation that = (ClientOrganisation) o;
        return enabled == that.enabled && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(registrationDate, that.registrationDate) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(personnel, that.personnel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, registrationDate, expiryDate, enabled, personnel);
    }

    @Override
    public String toString() {
        return "ClientOrganisation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registrationDate=" + registrationDate +
                ", expiryDate=" + expiryDate +
                ", enabled=" + enabled +
                ", personnel=" + personnel +
                '}';
    }
}