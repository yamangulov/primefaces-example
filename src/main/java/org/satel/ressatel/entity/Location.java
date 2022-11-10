package org.satel.ressatel.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "location")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String city;
    private String officeAddress;
    private String personalAddress;
}
