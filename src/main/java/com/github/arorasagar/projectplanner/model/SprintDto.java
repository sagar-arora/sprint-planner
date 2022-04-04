package com.github.arorasagar.projectplanner.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintDto {

    private String sprintName;

    private String startDate;

    private String endDate;
}
