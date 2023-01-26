package org.satel.ressatel.bean.list.role;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Role {
    private Integer id;
    private String name;
    private String grade;
    private String type;
}
