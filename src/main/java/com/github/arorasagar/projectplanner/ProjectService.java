package com.github.arorasagar.projectplanner;

import com.github.arorasagar.projectplanner.model.Project;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProjectService {

    public Project getProject(String projectId) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Project WHERE projectId = :project_id";
           // session.createQuery("FROM project").
            Query<Project> query = session.createQuery(hql, Project.class).setParameter("project_id", projectId);
            List<Project> results = query.list();

            if (results != null && results.size() == 1) {
                return results.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public void writeLogfile(Project project) {

        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(project);

            transaction.commit();
            //logger.info("Transcation complete.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
