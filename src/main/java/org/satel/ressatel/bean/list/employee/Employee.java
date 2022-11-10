package org.satel.ressatel.bean.list.employee;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Employee {
    private String name;
    private String skills;
    private String employeeDepartment;
    private String specialties;
    private String archived;
    private String linkToLK;
}
