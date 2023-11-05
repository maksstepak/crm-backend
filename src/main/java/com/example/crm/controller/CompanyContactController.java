package com.example.crm.controller;

import com.example.crm.dto.CompanyContactDto;
import com.example.crm.dto.CreateCompanyContactDto;
import com.example.crm.dto.UpdateCompanyContactDto;
import com.example.crm.service.CompanyContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CompanyContactController {
    private final CompanyContactService companyContactService;

    @PostMapping("/companies/{companyId}/contacts")
    ResponseEntity<Void> create(@PathVariable Long companyId, @Valid @RequestBody CreateCompanyContactDto request) {
        companyContactService.create(companyId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/companies/{companyId}/contacts")
    ResponseEntity<List<CompanyContactDto>> getAll(@PathVariable Long companyId) {
        var list = companyContactService.getAll(companyId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/company-contacts/{companyContactId}")
    ResponseEntity<Void> update(@PathVariable Long companyContactId, @Valid @RequestBody UpdateCompanyContactDto request) {
        companyContactService.update(companyContactId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/company-contacts/{companyContactId}")
    ResponseEntity<Void> delete(@PathVariable Long companyContactId) {
        companyContactService.delete(companyContactId);
        return ResponseEntity.noContent().build();
    }
}
