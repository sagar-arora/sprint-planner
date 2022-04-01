package com.github.arorasagar.projectplanner.controller;

import com.github.arorasagar.projectplanner.ProjectService;
import com.github.arorasagar.projectplanner.model.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/projects")
public class ProjectController {

    Logger LOGGER = LogManager.getLogger(ProjectController.class);

    ProjectService projectService;
    public ProjectController() {
        this.projectService = new ProjectService();
    }

    @GET
    @Path("/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Project getProject(@PathParam(value = "projectId") String projectId) {
        LOGGER.info("Got request for {}", projectId);

        return projectService.getProject(projectId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Project postProject(Project project) {
     //   LOGGER.info("Got request for {}", project.());
        Project project1 = Project.builder()
                .projectName("df")
                .isActive(true)
                .startDate("dfsj")
                .build();
        projectService.writeLogfile(project1);
        return project;
    }
}
