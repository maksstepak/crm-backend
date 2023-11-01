package com.example.crm.dto;

import com.example.crm.enumeration.CompanySize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCompanyDto(@NotBlank String name, @NotNull CompanySize size) {
}
