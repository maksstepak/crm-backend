package com.example.crm.service;

import com.example.crm.dto.CompanyContactDto;
import com.example.crm.dto.CreateCompanyContactDto;
import com.example.crm.dto.UpdateCompanyContactDto;
import com.example.crm.entity.CompanyContact;
import com.example.crm.exception.ResourceNotFoundException;
import com.example.crm.repository.CompanyContactRepository;
import com.example.crm.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyContactService {
    private final CompanyRepository companyRepository;
    private final CompanyContactRepository companyContactRepository;

    public void create(Long companyId, CreateCompanyContactDto dto) {
        var company = companyRepository.findById(companyId).orElseThrow(ResourceNotFoundException::new);
        var companyContact = new CompanyContact();
        companyContact.setCompany(company);
        companyContact.setEmail(dto.email());
        companyContact.setFirstName(dto.firstName());
        companyContact.setLastName(dto.lastName());
        companyContact.setPhoneNumber(dto.phoneNumber());
        companyContactRepository.save(companyContact);
    }

    public List<CompanyContactDto> getAll(Long companyId) {
        var company = companyRepository.findById(companyId).orElseThrow(ResourceNotFoundException::new);
        var companyContacts = companyContactRepository.findAllByCompanyOrderByIdDesc(company);
        var companyContactDtos = companyContacts.stream().map(CompanyContactDto::new).toList();
        return companyContactDtos;
    }

    @Transactional
    public void update(Long companyContactId, UpdateCompanyContactDto dto) {
        var companyContact = companyContactRepository.findById(companyContactId).orElseThrow(ResourceNotFoundException::new);
        companyContact.setEmail(dto.email());
        companyContact.setFirstName(dto.firstName());
        companyContact.setLastName(dto.lastName());
        companyContact.setPhoneNumber(dto.phoneNumber());
    }

    public void delete(Long companyContactId) {
        var companyContact = companyContactRepository.findById(companyContactId).orElseThrow(ResourceNotFoundException::new);
        companyContactRepository.delete(companyContact);
    }
}
