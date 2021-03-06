package com.github.arorasagar.projectplanner.service;

import com.github.arorasagar.projectplanner.HibernateUtil;
import com.github.arorasagar.projectplanner.model.Sprint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SprintService {

    Logger LOGGER = LoggerFactory.getLogger(SprintService.class);
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public Sprint getSprint(String SprintId) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            LOGGER.info("Started transaction to get Sprint with SprintId: {}", SprintId);
            String hql = "FROM Sprint WHERE sprintId = :Sprint_id";
            transaction = session.beginTransaction();

            Query<Sprint> query = session.createQuery(hql, Sprint.class).setParameter("Sprint_id", SprintId);
            List<Sprint> results = query.list();

            transaction.commit();
            if (results != null && results.size() == 1) {
                LOGGER.info("Successfully retrieved the Sprint with SprintId: {}", SprintId);
                return results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveSprint(Sprint Sprint) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            LOGGER.info("Started transaction to save Sprint...");

            session.save(Sprint);
            transaction.commit();
            LOGGER.info("Save Sprint completed successfully.");
        } catch (Exception e) {
            LOGGER.info("Exception occurred while saving Sprint: {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
