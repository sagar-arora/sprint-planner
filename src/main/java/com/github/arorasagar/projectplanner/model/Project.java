package com.github.arorasagar.projectplanner.model;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@javax.persistence.Table(name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String projectId;

    private String projectName;

    private String startDate;

    private boolean isActive;
}
