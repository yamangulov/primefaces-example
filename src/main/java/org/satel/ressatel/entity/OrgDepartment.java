package org.satel.ressatel.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "org_department")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OrgDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private OrgDepartment parent;
}
