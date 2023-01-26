package org.satel.ressatel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<Employee> employees;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "extraRoles")
    private Set<Employee> extraEmployees;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "personsToSpecialties",
            joinColumns = @JoinColumn(name = "specialty_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
    )
    private Set<Person> persons;
}
