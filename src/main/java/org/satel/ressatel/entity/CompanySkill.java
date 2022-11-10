package org.satel.ressatel.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "company_skill")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CompanySkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descr;

    @OneToMany(mappedBy = "companySkillId", fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<CompanySkillToSkills> companySkillToSkills;

    @OneToOne(mappedBy = "companySkill")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Company company;
}
