package com.accenture.kubernetesdemo.queueapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.accenture.kubernetesdemo.queueapp.domain.Act;

/**
 * This class is used to access data for the Act entity.
 * Repository annotation allows the component scanning support to find and 
 * configure the DAO wihtout any XML configuration and also provide the Spring 
 * exceptiom translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the
 * method. If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class ActDao {
  
  /**
   * Save the Act in the database.
   */
  public void create(Act Act) {
    entityManager.persist(Act);
    return;
  }
  
  /**
   * Delete the Act from the database.
   */
  public void delete(Act Act) {
    if (entityManager.contains(Act))
      entityManager.remove(Act);
    else
      entityManager.remove(entityManager.merge(Act));
    return;
  }
  
  /**
   * Return all the Acts stored in the database.
   */
  public List<Act> getAll() {
    return entityManager.createQuery("from Act", Act.class).getResultList();
  }
  
  /**
   * Return the Act having the passed id.
   */
  public Act getById(long id) {
    return entityManager.find(Act.class, id);
  }

  /**
   * Update the passed Act in the database.
   */
  public void update(Act act) {
    entityManager.merge(act);
    return;
  }
  
  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
  
}