package com.example.crm.service;

import com.example.crm.dto.CompanyDto;
import com.example.crm.dto.CreateCompanyDto;
import com.example.crm.dto.UpdateCompanyDto;
import com.example.crm.entity.Company;
import com.example.crm.exception.ResourceNotFoundException;
import com.example.crm.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public void create(CreateCompanyDto dto) {
        var company = new Company();
        company.setName(dto.name());
        company.setSize(dto.size());
        companyRepository.save(company);
    }

    public CompanyDto getById(Long companyId) {
        var company = companyRepository.findById(companyId).orElseThrow(ResourceNotFoundException::new);
        var companyDto = new CompanyDto(company);
        return companyDto;
    }

    public Page<CompanyDto> getAll(Pageable pageable) {
        var companies = companyRepository.findAll(pageable);
        var companyDtos = companies.map(CompanyDto::new);
        return companyDtos;
    }

    @Transactional
    public void update(Long companyId, UpdateCompanyDto dto) {
        var company = companyRepository.findById(companyId).orElseThrow(ResourceNotFoundException::new);
        company.setName(dto.name());
        company.setSize(dto.size());
    }

    public void delete(Long companyId) {
        var company = companyRepository.findById(companyId).orElseThrow(ResourceNotFoundException::new);
        companyRepository.delete(company);
    }
}
