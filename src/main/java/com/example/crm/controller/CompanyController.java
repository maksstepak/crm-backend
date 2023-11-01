package com.example.crm.controller;

import com.example.crm.dto.CompanyDto;
import com.example.crm.dto.CreateCompanyDto;
import com.example.crm.dto.UpdateCompanyDto;
import com.example.crm.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody CreateCompanyDto request) {
        companyService.create(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{companyId}")
    ResponseEntity<CompanyDto> getById(@PathVariable Long companyId) {
        var companyDto = companyService.getById(companyId);
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping
    ResponseEntity<Page<CompanyDto>> getAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var page = companyService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{companyId}")
    ResponseEntity<Void> update(@PathVariable Long companyId, @Valid @RequestBody UpdateCompanyDto request) {
        companyService.update(companyId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{companyId}")
    ResponseEntity<Void> delete(@PathVariable Long companyId) {
        companyService.delete(companyId);
        return ResponseEntity.noContent().build();
    }
}
