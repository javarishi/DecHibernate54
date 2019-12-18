package com.h2kinfosys.hibernate.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.h2kinfosys.hibernate.dto.ActorDTO;

public class ActorDAO {
	
	private SessionFactory sessionFactory = null;
	
	protected void setUp() throws Exception {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}
	
	public ActorDAO() throws Exception {
		setUp();
	}

	public static void main(String[] args) {
		ActorDAO actorDAO;
		try {
			actorDAO = new ActorDAO();
			// actorDAO.fetchAllActors();
			ActorDTO actor = actorDAO.fetchActorWithId(10);
			actor.setFirstName("Ryan");
			actor.setLastName("Coble");
			//actorDAO.updateActorFirstName(actor);
			actorDAO.updateActorWithSession(actor);
			//actorDAO.deleteActor(301);
			
			ActorDTO newActor = new ActorDTO();
			newActor.setFirstName("Niel");
			newActor.setLastName("ArmStrong");
			newActor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
			actorDAO.saveOrUpdateActorWithSession(newActor);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	public void fetchAllActors() {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			TypedQuery<ActorDTO> query = session.createQuery("from ActorDTO");
			List<ActorDTO> actors = query.getResultList();
			for(ActorDTO actor : actors) {
				System.out.println("ActorID :: " + actor.getActorId() + " Actor Name " + actor.getFirstName() + " " + actor.getLastName());
			}
			tx.commit();
			session.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public ActorDTO fetchActorWithId(int actorId) throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			TypedQuery<ActorDTO> query = session.createQuery("from ActorDTO AS A where A.actorId = :actor_key");
			query.setParameter("actor_key", actorId);
			ActorDTO actor = query.getSingleResult(); // for single result
			System.out.println("ActorID :: " + actor.getActorId() + " Actor Name " + actor.getFirstName() + " " + actor.getLastName());
			tx.commit();
			session.close();
			return actor;
		}catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
	}
	
	
	public void updateActorFirstName(ActorDTO actor) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			TypedQuery<ActorDTO> query = session.createQuery("Update ActorDTO AS A set A.firstName = : fName where A.actorId = :actorId");
			query.setParameter("fName", actor.getFirstName());
			query.setParameter("actorId", actor.getActorId());
			int rowsAffected = query.executeUpdate();
			System.out.println("Rows Affected :: " + rowsAffected);
			tx.commit();
			session.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void updateActorWithSession(ActorDTO actor) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.update(actor);
			System.out.println("Object saved successfully");
			tx.commit();
			session.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void saveOrUpdateActorWithSession(ActorDTO actor) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(actor);
			System.out.println("Object saveOrUpdate successfully");
			tx.commit();
			session.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void saveActorWithSession(ActorDTO actor) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(actor);
			System.out.println("Object session.save successfully");
			tx.commit();
			session.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteActor(int actorId) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			TypedQuery<ActorDTO> query = session.createQuery("Delete ActorDTO AS A where A.actorId = :actorId");
			query.setParameter("actorId", actorId);
			int rowsAffected = query.executeUpdate();
			System.out.println("Rows Affected :: " + rowsAffected);
			tx.commit();
			session.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void countQueryExample() {
		// count(property name)
	}

}
