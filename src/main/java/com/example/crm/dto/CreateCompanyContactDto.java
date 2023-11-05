package com.example.crm.dto;

import jakarta.validation.constraints.Size;

public record CreateCompanyContactDto(String email, String firstName, String lastName,
                                      @Size(max = 50) String phoneNumber) {
}
