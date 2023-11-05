package com.example.crm.dto;

import com.example.crm.entity.CompanyContact;

public record CompanyContactDto(Long id, String email, String firstName, String lastName, String phoneNumber) {
    public CompanyContactDto(CompanyContact companyContact) {
        this(companyContact.getId(), companyContact.getEmail(), companyContact.getFirstName(), companyContact.getLastName(), companyContact.getPhoneNumber());
    }
}
