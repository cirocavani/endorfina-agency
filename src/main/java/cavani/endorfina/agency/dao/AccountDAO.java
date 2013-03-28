package cavani.endorfina.agency.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cavani.endorfina.agency.model.persistence.Account;
import cavani.endorfina.agency.model.persistence.Account_;

public class AccountDAO extends AbstractDAO<Account, Long>
{

	public AccountDAO()
	{
		super(Account.class);
	}

	public Account findByPrincipal(final String principal)
	{
		final CriteriaBuilder b = getEm().getCriteriaBuilder();

		final CriteriaQuery<Account> q = b.createQuery(Account.class);

		final Root<Account> a = q.from(Account.class);
		q.select(a);
		q.where(b.equal(a.get(Account_.principal), principal));

		final TypedQuery<Account> query = getEm().createQuery(q);
		try
		{
			return query.getSingleResult();
		}
		catch (final NoResultException e)
		{
			return null;
		}

	}
}
