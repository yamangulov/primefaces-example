package org.satel.ressatel.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contact")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phone;
    private String mobile;
    private String email;
}
