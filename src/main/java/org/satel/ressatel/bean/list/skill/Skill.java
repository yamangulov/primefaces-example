package org.satel.ressatel.bean.list.skill;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Skill {
    private Integer id;
    private String name;
    private String skillGrade;
    private String type;
}
