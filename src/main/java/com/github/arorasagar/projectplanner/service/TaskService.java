package com.github.arorasagar.projectplanner.service;

import com.github.arorasagar.projectplanner.HibernateUtil;
import com.github.arorasagar.projectplanner.model.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TaskService {

    Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


    public Task getTask(String taskId) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            LOGGER.info("Started transaction to get task with taskId: {}", taskId);
            String hql = "FROM Task WHERE taskId = :task_id";
            transaction = session.beginTransaction();

            Query<Task> query = session.createQuery(hql, Task.class).setParameter("task_id", taskId);
            List<Task> results = query.list();

            transaction.commit();
            if (results != null && results.size() == 1) {
                LOGGER.info("Successfully retrieved the Task with taskId: {}", taskId);
                return results.get(0);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public void saveTask(Task Task) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            LOGGER.info("Started transaction to save Task...");
            
            session.save(Task);
            transaction.commit();
            
            LOGGER.info("Save Task completed successfully.");
        } catch (Exception e) {
            LOGGER.info("Exception occurred while saving Task: {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
