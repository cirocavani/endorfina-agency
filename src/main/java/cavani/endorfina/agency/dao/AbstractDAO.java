package cavani.endorfina.agency.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public abstract class AbstractDAO<T, ID extends Serializable>
{

	private final Class<T> persistentClass;

	@PersistenceContext
	private EntityManager em;

	public AbstractDAO(final Class<T> persistentClass)
	{
		this.persistentClass = persistentClass;
	}

	protected EntityManager getEm()
	{
		return em;
	}

	public Class<T> getPersistentClass()
	{
		return persistentClass;
	}

	public T findById(final ID id)
	{
		return em.find(getPersistentClass(), id);
	}

	public List<T> findAll()
	{
		final CriteriaQuery<T> q = em.getCriteriaBuilder().createQuery(persistentClass);
		final TypedQuery<T> query = em.createQuery(q.select(q.from(persistentClass)));
		return query.getResultList();
	}

	public T makePersistent(final T entity)
	{
		return em.merge(entity);
	}

	public void makeTransient(final T entity)
	{
		em.remove(entity);
	}

	public void makeTransient(final ID id)
	{
		final T entity = findById(id);
		makeTransient(entity);
	}

	public void refresh(final T entity)
	{
		em.refresh(entity);
	}

}
