package com.github.arorasagar.projectplanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.annotations.Expose;
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
@Table(name = "sprint")
public class Sprint {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String sprintId;

    private String sprintName;

    private String startDate;

    private String endDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    @Expose
    private Project project;

    @OneToMany(mappedBy = "sprint", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();
}
