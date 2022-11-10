package org.satel.ressatel.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "education")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String organization;

    @OneToOne
    @JoinColumn(name = "education_grade_id")
    private EducationGrade educationGrade;

    @OneToOne(mappedBy = "education")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Employee employee;
}
