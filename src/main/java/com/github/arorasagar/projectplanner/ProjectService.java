package com.github.arorasagar.projectplanner;

import com.github.arorasagar.projectplanner.controller.ProjectController;
import com.github.arorasagar.projectplanner.model.Project;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProjectService {

    Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);
    public Project getProject(String projectId) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Project WHERE projectId = :project_id";
           // session.createQuery("FROM project").
            transaction = session.beginTransaction();
            Query<Project> query = session.createQuery(hql, Project.class).setParameter("project_id", projectId);
            List<Project> results = query.list();

            if (results != null && results.size() == 1) {
                return results.get(0);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public void writeProject(Project project) {

        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(project);

            transaction.commit();
            LOGGER.info("Transcation complete.");
        } catch (Exception e) {
            LOGGER.info("Exception occured : {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
