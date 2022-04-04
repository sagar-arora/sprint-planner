package com.github.arorasagar.projectplanner.controller;

import com.github.arorasagar.projectplanner.model.Project;
import com.github.arorasagar.projectplanner.model.Sprint;
import com.github.arorasagar.projectplanner.service.ProjectService;
import com.github.arorasagar.projectplanner.service.SprintService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class SprintController {

    Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    ProjectService projectService;
    SprintService sprintService;

    public SprintController() {
        this.sprintService = new SprintService();
        this.projectService = new ProjectService();
    }

    @GET
    @Path("/projects/{projectId}/sprints")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sprint> getSprint(@PathParam(value = "projectId") String projectId) {
        LOGGER.info("Got request for to get all the sprints for the projectId {}", projectId);
        Project project = projectService.getProject(projectId);
        if (project == null) {
            LOGGER.info("Project {} is not found.", projectId);
            throw new RuntimeException("project not found");
        }

        return project.getSprints();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectId}/sprints")
    public Sprint postProject(@PathParam(value = "projectId") String projectId, Sprint sprint) {
        Gson gson = new Gson();
        LOGGER.info("Got request for projectId: {} to create new sprint {}", projectId, gson.toJson(sprint));
        Project project = projectService.getProject(projectId);
        if (project == null) {
            LOGGER.info("Project {} is not found.", projectId);
            throw new RuntimeException("project not found");
        }

        sprint.setProject(project);
        sprintService.saveSprint(sprint);
        return sprint;
    }
}
