package com.github.arorasagar.projectplanner.service;

import com.github.arorasagar.projectplanner.HibernateUtil;
import com.github.arorasagar.projectplanner.model.Project;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProjectService {

    Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public Project getProject(String projectId) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            LOGGER.info("Started transaction to get project with projectId: {}", projectId);
            String hql = "FROM Project WHERE projectId = :project_id";
            transaction = session.beginTransaction();

            Query<Project> query = session.createQuery(hql, Project.class).setParameter("project_id", projectId);
            List<Project> results = query.list();

            transaction.commit();
            if (results != null && results.size() == 1) {
                LOGGER.info("Successfully retrieved the project with projectId: {}, {}", projectId,
                        gson.toJson(results.get(0)));
                return results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Project> getProjects() {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            LOGGER.info("Started transaction to get all projects");
            String hql = "FROM Project";
            transaction = session.beginTransaction();

            Query<Project> query = session.createQuery(hql, Project.class);
            List<Project> results = query.list();

            transaction.commit();

            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveProject(Project project) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            LOGGER.info("Started transaction to save project...");
            session.save(project);

            transaction.commit();
            LOGGER.info("Save Project completed successfully.");
        } catch (Exception e) {
            LOGGER.info("Exception occurred while saving project: {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
