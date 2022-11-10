package org.satel.ressatel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "company_project_experience")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CompanyProjectExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String contractNumber;
    private String name;
    private Integer budget;
    private String period;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
}
