package com.github.arorasagar.projectplanner.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Sprint> sprints = new ArrayList<>();
}
