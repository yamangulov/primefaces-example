package org.satel.ressatel.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "english_level")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EnglishLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
