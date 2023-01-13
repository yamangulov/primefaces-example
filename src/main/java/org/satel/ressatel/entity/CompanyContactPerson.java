package org.satel.ressatel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "company_contact_person")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CompanyContactPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String secondName;
    private String surname;
    private String phone;
    private String email;
    private String telegram;
}
