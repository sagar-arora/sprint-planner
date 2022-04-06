package com.github.arorasagar.projectplanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public final class Task {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String taskId;

    private String taskName;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    @JsonBackReference
    @Expose
    private Sprint sprint;

    private int points;
}
