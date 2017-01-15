package dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase para automatizar y eliminar la duplicacion de codigo
 * al implementar las clases DAO
 * Fuente: https://www.codeproject.com/articles/251166/the-generic-dao-pattern-in-java-with-spring-3-and 
 * @author Juan Manuel Lomas
 *
 */
public abstract class GenericDAO<T> {	
	
    @PersistenceContext
    protected EntityManager em;

    private Class<T> type;

    @SuppressWarnings("unchecked")
	public GenericDAO() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<T>) pt.getActualTypeArguments()[0];
    }

	@SuppressWarnings("unchecked")
	protected List<T> findAll() {
		return em.createQuery("SELECT e FROM " + type.getSimpleName() + " AS e").getResultList();
	}
    
    public T create(final T t) {
        this.em.persist(t);
        return t;
    }

    public void delete(final Object id) {
        this.em.remove(this.em.getReference(type, id));
    }

    public T find(final Object id) {
        return (T) this.em.find(type, id);
    }

    public T update(final T t) {
        return this.em.merge(t);    
    }    
    
}