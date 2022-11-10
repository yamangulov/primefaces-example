package org.satel.ressatel.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "specialty")
@Getter
@Setter
@RequiredArgsConstructor
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Specialty parent;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employeesToSpecialties",
            joinColumns = @JoinColumn(name = "specialty_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
    )
    private Set<Employee> employees;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "personsToSpecialties",
            joinColumns = @JoinColumn(name = "specialty_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
    )
    private Set<Person> persons;
}
