package org.satel.ressatel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "skill")
@Getter
@Setter
@RequiredArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Skill parent;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "skills")
    private Set<Employee> employees;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "personsToSkills",
            joinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
    )
    private Set<Person> persons;


}
