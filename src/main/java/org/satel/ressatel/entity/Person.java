package org.satel.ressatel.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String secondName;
    private String surname;
    private String inn;
    private Integer age;
    private Integer costPriceRate;
    private String estimation;
    private Integer taxes;
    private Integer ndaStatus;
    private String socnetworks;

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

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<File> files;

    @OneToOne
    @JoinColumn(name = "english_level_id")
    private EnglishLevel englishLevel;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProjectExperience> satelProjectExperiences;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "possible_employment_id", referencedColumnName = "id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private PossibleEmployment possibleEmployment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToMany(mappedBy = "persons", fetch = FetchType.EAGER)
    private Set<Skill> skills;

    @ManyToMany(mappedBy = "persons", fetch = FetchType.EAGER)
    private Set<Specialty> specialties;

    @OneToOne(mappedBy = "person")
    private PersonCommand personCommand;
}
