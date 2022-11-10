package org.satel.ressatel.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String secondName;
    private String surname;
    private Integer age;
    private Integer workExperience;
    private Integer costPriceRate;
    private String estimation;
    private String selfInfo;
    private String chefInfo;
    private Boolean archived;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "occupation_id", referencedColumnName = "id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Occupation occupation;

    @Transient
    private String russianResume;
    @Transient
    private String englishResume;
    @Transient
    private String linkToLK;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Set<File> files;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "english_level_id")
    private EnglishLevel englishLevel;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Set<ProjectExperience> projectExperiences;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "education_id", referencedColumnName = "id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Education education;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_department_id")
    private EmployeeDepartment employeeDepartment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "employeesToSkills",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id")
    )
    private Set<Skill> skills;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Set<Specialty> specialties;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_command_id", referencedColumnName = "id")
    private PersonCommand personCommand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_command_id", referencedColumnName = "id")
    private CompanyCommand companyCommand;
}
