package org.satel.ressatel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "company_skill_to_skills")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CompanySkillToSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "company_skill_id")
    private Integer companySkillId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public CompanySkillToSkills(Integer companySkillId, Skill skill) {
        this.companySkillId = companySkillId;
        this.skill = skill;
    }
}
