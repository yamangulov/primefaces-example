package org.satel.ressatel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String inn;
    private String site;
    private Integer taxes;
    private String ndaStatus;
    private String socnetworks;

    @Transient
    private String linkToLK;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_skill_id")
    private CompanySkill companySkill;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_contact_person_id")
    private CompanyContactPerson companyContactPerson;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<CompanyProjectExperience> satelProjectExperiences;

    @OneToOne(mappedBy = "company", fetch = FetchType.EAGER)
    private CompanyCommand companyCommand;
}
