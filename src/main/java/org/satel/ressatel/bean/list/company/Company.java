package org.satel.ressatel.bean.list.company;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Company {
    private String name;
    private String inn;
    private String skills;
    private String linkToLK;
}
