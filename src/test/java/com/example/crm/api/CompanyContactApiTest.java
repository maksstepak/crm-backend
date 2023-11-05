package com.example.crm.api;

import com.example.crm.entity.Company;
import com.example.crm.entity.CompanyContact;
import com.example.crm.enumeration.CompanySize;
import com.example.crm.repository.CompanyContactRepository;
import com.example.crm.repository.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CompanyContactApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyContactRepository companyContactRepository;

    @Test
    @WithMockUser
    public void shouldCreateCompanyContact() throws Exception {
        var company = new Company();
        company.setName("company test");
        company.setSize(CompanySize.MEDIUM);
        companyRepository.save(company);
        var body = new HashMap<>();
        body.put("firstName", "John");
        body.put("lastName", "Doe");
        body.put("email", "companyContact1@test.com");
        body.put("phoneNumber", "123 123 123");

        this.mockMvc
                .perform(post("/api/companies/{companyId}/contacts", company.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());

        var optionalCompanyContact = companyContactRepository.findByEmail("companyContact1@test.com");
        assertTrue(optionalCompanyContact.isPresent());
    }

    @Test
    @WithMockUser
    public void shouldGetAllCompanyContacts() throws Exception {
        var company = new Company();
        company.setName("company test");
        company.setSize(CompanySize.MEDIUM);
        companyRepository.save(company);
        var companyContact1 = new CompanyContact();
        companyContact1.setCompany(company);
        var companyContact2 = new CompanyContact();
        companyContact2.setCompany(company);
        var companyContact3 = new CompanyContact();
        companyContact3.setCompany(company);
        companyContactRepository.saveAll(List.of(companyContact1, companyContact2, companyContact3));

        this.mockMvc
                .perform(get("/api/companies/{companyId}/contacts", company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser
    public void shouldUpdateCompanyContact() throws Exception {
        var company = new Company();
        company.setName("company test");
        company.setSize(CompanySize.MEDIUM);
        companyRepository.save(company);
        var companyContact = new CompanyContact();
        companyContact.setCompany(company);
        companyContactRepository.save(companyContact);
        var body = new HashMap<>();
        body.put("firstName", "John");
        body.put("lastName", "Doe");
        body.put("email", "companyContact1@test.com");
        body.put("phoneNumber", "123 123 123");

        this.mockMvc
                .perform(put("/api/company-contacts/{companyContactId}", companyContact.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());

        assertEquals("John", companyContact.getFirstName());
    }

    @Test
    @WithMockUser
    public void shouldDeleteCompanyContact() throws Exception {
        var company = new Company();
        company.setName("company test");
        company.setSize(CompanySize.MEDIUM);
        companyRepository.save(company);
        var companyContact = new CompanyContact();
        companyContact.setCompany(company);
        companyContactRepository.save(companyContact);

        this.mockMvc
                .perform(delete("/api/company-contacts/{companyContactId}", companyContact.getId()))
                .andExpect(status().isNoContent());

        var exists = companyContactRepository.existsById(companyContact.getId());
        assertFalse(exists);
    }
}
