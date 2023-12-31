package com.example.crm.entity;

import com.example.crm.enumeration.CompanySize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanySize size;

    @OneToMany(mappedBy = "company")
    private List<CompanyContact> contacts;
}
