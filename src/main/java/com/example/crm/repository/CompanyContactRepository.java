package com.example.crm.repository;

import com.example.crm.entity.Company;
import com.example.crm.entity.CompanyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyContactRepository extends JpaRepository<CompanyContact, Long> {
    Optional<CompanyContact> findByEmail(String email);

    List<CompanyContact> findAllByCompanyOrderByIdDesc(Company company);

    @Modifying
    @Query("delete from CompanyContact cc where cc.company.id = :companyId")
    void deleteAllByCompanyId(@Param("companyId") Long companyId);
}
