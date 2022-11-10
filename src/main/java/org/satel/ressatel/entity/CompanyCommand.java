package org.satel.ressatel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company_command")
@Getter
@Setter
@RequiredArgsConstructor
public class CompanyCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descr;
    private String conditions;


    @OneToMany(mappedBy = "companyCommand", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<File> files;

    @OneToMany(mappedBy = "resourcesCompanyCommand", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<File> resourcesFiles;

    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @OneToMany(mappedBy = "companyCommand", fetch = FetchType.EAGER)
    private List<Employee> employees;
}
