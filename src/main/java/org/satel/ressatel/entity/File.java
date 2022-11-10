package org.satel.ressatel.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.net.URL;

@Entity
@Table(name = "file")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content;

    private String comment;
    private Integer type; // 1 - команда_компании.компания, 2 - команда_компании.резюме_ресурса

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "company_command_id", referencedColumnName = "id")
    private CompanyCommand companyCommand;

    @ManyToOne
    @JoinColumn(name = "resources_company_command_id", referencedColumnName = "id")
    private CompanyCommand resourcesCompanyCommand;
}
