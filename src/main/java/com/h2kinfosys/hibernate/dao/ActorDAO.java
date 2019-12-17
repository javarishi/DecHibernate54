package com.h2kinfosys.hibernate.dao;

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
			actorDAO.fetchAllActors();
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

}
