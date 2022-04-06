package com.github.arorasagar.projectplanner.controller;

import com.github.arorasagar.projectplanner.model.*;
import com.github.arorasagar.projectplanner.service.SprintService;
import com.github.arorasagar.projectplanner.service.TaskService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/sprints")
public class SprintController {

    Logger LOGGER = LoggerFactory.getLogger(SprintController.class);
    private final SprintService sprintService;
    private final TaskService taskService;
    public SprintController() {
        this.sprintService = new SprintService();
        this.taskService = new TaskService();
    }

    @GET
    @Path("/{sprintId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sprint getSprint(@PathParam(value = "sprintId") String sprintId) {
        LOGGER.info("Got request for to get all the sprints for the projectId {}", sprintId);
        Sprint sprint = sprintService.getSprint(sprintId);
        if (sprint == null) {
            LOGGER.info("Sprint {} not found.", sprintId);
            throw new RuntimeException("sprint not found");
        }
        return sprint;
    }

    @GET
    @Path("{sprintId}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getAllTaskForSprint(@PathParam(value = "sprintId") String sprintId) {
        LOGGER.info("Got request for to get all the sprints for the sprintId {}", sprintId);
        Sprint sprint = sprintService.getSprint(sprintId);
        if (sprint == null) {
            LOGGER.info("Sprint {} is not found.", sprintId);
            throw new RuntimeException("sprint not found");
        }

        return sprint.getTasks();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{sprintId}/tasks")
    public Task createTask(@PathParam(value = "sprintId") String sprintId, TaskDto taskDto) {
        Gson gson = new Gson();
        LOGGER.info("Got request for sprintId: {} to create new task {}", sprintId, gson.toJson(taskDto));
        Sprint sprint = sprintService.getSprint(sprintId);

        if (sprint == null) {
            LOGGER.info("Sprint {} is not found.", sprintId);
            throw new RuntimeException("sprint not found");
        }

        Task _task = Task
                .builder()
                .taskName(taskDto.getTaskName())
                .points(taskDto.getPoints())
                .sprint(sprint)
                .build();

        taskService.saveTask(_task);

        return _task;
    }


/*    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectId}/sprints")
    public Sprint postProject(@PathParam(value = "projectId") String projectId,
                              SprintDto sprint) {
        Gson gson = new Gson();
        LOGGER.info("Got request for projectId: {} to create new sprint {}", projectId, gson.toJson(sprint));
        Project project = projectService.getProject(projectId);
        if (project == null) {
            LOGGER.info("Project {} is not found.", projectId);
            throw new RuntimeException("project not found");
        }

        Sprint _sprint = Sprint
                .builder()
                .sprintName(sprint.getSprintName())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .build();

        _sprint.setProject(project);
        sprintService.saveSprint(_sprint);

        return _sprint;
    }*/
}
