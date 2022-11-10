package org.satel.ressatel.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "occupation")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TypeDef(name = "json", typeClass = JsonType.class)
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean status;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private Map<Integer, Map<String, String>> satelOccupation;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private Map<Integer, Map<String, String>> plannedSatelOccupation;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private Map<Integer, Map<String, String>> outerOccupation;

    @OneToOne(mappedBy = "occupation")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Employee employee;

    @OneToOne(mappedBy = "occupation")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Person person;
}
