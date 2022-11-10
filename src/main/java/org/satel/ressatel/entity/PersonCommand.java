package org.satel.ressatel.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "person_command")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PersonCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descr;

    @OneToMany(mappedBy = "personCommand")
    private List<Employee> employees;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
}
