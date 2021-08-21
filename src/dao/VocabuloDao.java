package dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import models.Vocabulo;
import sessao.Sessao;

public class VocabuloDao {
	
	public Serializable criar(Vocabulo vocabulo) {
		Session s1 = Sessao.getSessionFactory().openSession();
		s1.beginTransaction();
		
		Serializable s = s1.save(vocabulo);
		
		s1.getTransaction().commit();
		s1.close();
		return s;
	}
	
	public int contar(String p) {
		Session s2 = Sessao.getSessionFactory().openSession();
		s2.beginTransaction();
		
		Query query = s2.createNativeQuery("SELECT COUNT(*) FROM Vocabulo v WHERE v.palavra = :p");
		query.setParameter("p", p);
		
		int quantidade = (int) query.getSingleResult();
		
		s2.getTransaction().commit();
		s2.close();
		return quantidade;
	}
	
	public Vocabulo encontrarVocabulario(String p) {
		Session s5 = Sessao.getSessionFactory().openSession();
		s5.beginTransaction();
		
		Query query = s5.createQuery("FROM Vocabulo WHERE palavra = :p");
		query.setParameter("p", p);
		
		Vocabulo vocabulo = (Vocabulo) query.getSingleResult();
		
		s5.getTransaction().commit();
		s5.close();
		return vocabulo;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vocabulo> encontrarVocabulos(String p) {
		Session s6 = Sessao.getSessionFactory().openSession();
		s6.beginTransaction();
		
		Query query = s6.createQuery("FROM Vocabulo WHERE palavra LIKE :p");
		query.setParameter("p", p+"%");
		
		List<Vocabulo> vocabulos = query.getResultList();
		
		s6.getTransaction().commit();
		s6.close();
		return vocabulos;
	}
	
	public Vocabulo encontrar(Long id) {
		Session s4 = Sessao.getSessionFactory().openSession();
		s4.beginTransaction();
		
		Vocabulo vocabulo = s4.find(Vocabulo.class, id);
		
		s4.getTransaction().commit();
		s4.close();
		return vocabulo;
	}
	
	public void atualizar(Vocabulo vocabulo) {
		Session s3 = Sessao.getSessionFactory().openSession();
		s3.beginTransaction();
		
		s3.saveOrUpdate(vocabulo);
		
		s3.getTransaction().commit();
		s3.close();
	}

}
