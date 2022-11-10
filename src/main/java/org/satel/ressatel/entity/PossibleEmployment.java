package org.satel.ressatel.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "possible_employment")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PossibleEmployment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer hoursPerWeek;
    private Integer termInMonths;

    @OneToOne(mappedBy = "possibleEmployment")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Person person;
}
