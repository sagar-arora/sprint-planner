package com.github.arorasagar.projectplanner.model;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@javax.persistence.Table(name = "Project")
public class Project {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String projectId;

    private String projectName;

    private String startDate;
}
