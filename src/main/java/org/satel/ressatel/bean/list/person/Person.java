package org.satel.ressatel.bean.list.person;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Person {
    private String name;
    private String inn;
    private String skills;
    private String specialties;
    private String linkToLK;
}
