package org.satel.ressatel.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "command")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private String conditions;

}
