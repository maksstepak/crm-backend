package com.example.crm.dto;

import com.example.crm.entity.Company;
import com.example.crm.enumeration.CompanySize;

public record CompanyDto(Long id, String name, CompanySize size) {
    public CompanyDto(Company company) {
        this(company.getId(), company.getName(), company.getSize());
    }
}
