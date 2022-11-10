package org.satel.ressatel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_department")
@Getter
@Setter
@RequiredArgsConstructor
public class FinDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private FinDepartment parent;

    @Override
    public String toString() {
        return name;
    }
}
