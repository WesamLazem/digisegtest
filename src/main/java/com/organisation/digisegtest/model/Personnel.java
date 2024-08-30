package com.organisation.digisegtest.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "personnel")
public class Personnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telephoneNumber;

    @ManyToOne
    @JoinColumn(name = "client_organisation_id")
    private ClientOrganisation clientOrganisation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public ClientOrganisation getClientOrganisation() {
        return clientOrganisation;
    }

    public void setClientOrganisation(ClientOrganisation clientOrganisation) {
        this.clientOrganisation = clientOrganisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personnel personnel = (Personnel) o;
        return Objects.equals(id, personnel.id) && Objects.equals(firstName, personnel.firstName) && Objects.equals(lastName, personnel.lastName) && Objects.equals(username, personnel.username) && Objects.equals(password, personnel.password) && Objects.equals(email, personnel.email) && Objects.equals(telephoneNumber, personnel.telephoneNumber) && Objects.equals(clientOrganisation, personnel.clientOrganisation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password, email, telephoneNumber, clientOrganisation);
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", clientOrganisation=" + clientOrganisation +
                '}';
    }
}
