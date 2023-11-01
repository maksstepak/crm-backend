package com.example.crm.api;

import com.example.crm.entity.Company;
import com.example.crm.enumeration.CompanySize;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CompanyApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @WithMockUser
    public void shouldCreateCompany() throws Exception {
        var body = new HashMap<>();
        body.put("name", "company test");
        body.put("size", "MEDIUM");

        this.mockMvc
                .perform(post("/api/companies").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());

        var optionalCompany = companyRepository.findByName("company test");
        assertTrue(optionalCompany.isPresent());
    }

    @Test
    @WithMockUser
    public void shouldGetCompanyById() throws Exception {
        var company = new Company();
        company.setName("company test");
        company.setSize(CompanySize.MEDIUM);
        companyRepository.save(company);

        this.mockMvc
                .perform(get("/api/companies/{companyId}", company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("company test"));
    }

    @Test
    @WithMockUser
    public void shouldGetAllCompanies() throws Exception {
        var company1 = new Company();
        company1.setName("company test 1");
        company1.setSize(CompanySize.SMALL);
        companyRepository.save(company1);
        var company2 = new Company();
        company2.setName("company test 2");
        company2.setSize(CompanySize.MEDIUM);
        companyRepository.save(company2);
        var company3 = new Company();
        company3.setName("company test 3");
        company3.setSize(CompanySize.LARGE);
        companyRepository.save(company3);

        this.mockMvc
                .perform(get("/api/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Test
    @WithMockUser
    public void shouldUpdateCompany() throws Exception {
        var company = new Company();
        company.setName("company test");
        company.setSize(CompanySize.MEDIUM);
        companyRepository.save(company);
        var body = new HashMap<>();
        body.put("name", "company test 2");
        body.put("size", "MEDIUM");

        this.mockMvc
                .perform(put("/api/companies/{userId}", company.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());

        assertEquals("company test 2", company.getName());
    }

    @Test
    @WithMockUser
    public void shouldDeleteCompany() throws Exception {
        var company = new Company();
        company.setName("company test");
        company.setSize(CompanySize.MEDIUM);
        companyRepository.save(company);

        this.mockMvc
                .perform(delete("/api/companies/{companyId}", company.getId()))
                .andExpect(status().isNoContent());

        var exists = companyRepository.existsById(company.getId());
        assertFalse(exists);
    }
}
