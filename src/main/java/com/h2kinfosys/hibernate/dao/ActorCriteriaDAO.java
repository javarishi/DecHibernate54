package com.h2kinfosys.hibernate.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.h2kinfosys.hibernate.dto.ActorDTO;

public class ActorCriteriaDAO {
	
	public ActorCriteriaDAO() throws Exception {
		setUp();
	}
	
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

	public static void main(String[] args) throws Exception {
		ActorCriteriaDAO actorDAO = new ActorCriteriaDAO();
		actorDAO.fecthActorWithID(100);

	}
	
	
	public void fecthActorWithID(int actorID) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			Criteria cr = session.createCriteria(ActorDTO.class);
			LogicalExpression logic = Restrictions.or(Restrictions.gt("actorId", actorID), Restrictions.like("firstName", "A%"));
			cr.add(logic);
			List<ActorDTO> actors = cr.list();
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
