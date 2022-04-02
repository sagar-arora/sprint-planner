package com.github.arorasagar.projectplanner.controller;

import com.github.arorasagar.projectplanner.ProjectService;
import com.github.arorasagar.projectplanner.model.Project;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/projects")
public class ProjectController {

    Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

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

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getProjects(@PathParam(value = "projectId") String projectId) {
        return "hello";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Project postProject(Project project) {
        Gson gson = new Gson();
        LOGGER.info("Got request for {}", gson.toJson(project));
/*        Project project1 = Project.builder()
                .projectId("4")
                .startDate(project.getStartDate())
                .projectName(project.getProjectName())
                .build();*/
        projectService.writeProject(project);
        return project;
    }
}
