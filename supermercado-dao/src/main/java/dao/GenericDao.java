package dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> type;

    @SuppressWarnings("unchecked")
	public GenericDao() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<T>) pt.getActualTypeArguments()[0];
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
    
	@SuppressWarnings("unchecked")
	protected List<T> getAll() {
		return em.createQuery("SELECT e FROM " + type.getSimpleName() + " AS e").getResultList();
	}
    
}