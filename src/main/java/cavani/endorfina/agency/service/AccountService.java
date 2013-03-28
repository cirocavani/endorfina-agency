package cavani.endorfina.agency.service;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import cavani.endorfina.agency.dao.AccountDAO;
import cavani.endorfina.agency.model.domain.Identity;
import cavani.endorfina.agency.model.persistence.Account;

@Stateless
@LocalBean
public class AccountService
{

	@Inject
	AccountDAO dao;

	protected Identity authenticate(final String principal, final String secret, final boolean checkActive)
	{
		final Account account = dao.findByPrincipal(principal);

		boolean valid = account != null;
		valid &= !checkActive || account.getActive() != null && account.getActive();
		valid &= account.getSecret().equals(secret);

		return valid ? new Identity(account.getId(), account.getName(), account.getPrincipal()) : null;
	}

	public Identity validate(final String principal, final String secret)
	{
		return authenticate(principal, secret, false);
	}

	public Identity authenticate(final String principal, final String secret)
	{
		return authenticate(principal, secret, true);
	}

	public Identity create(final String name, final String principal, final String secret)
	{
		Account account = dao.findByPrincipal(principal);

		if (account != null)
		{
			return null;
		}

		account = new Account();
		account.setName(name);
		account.setPrincipal(principal);
		account.setSecret(secret);
		account.setActive(false);
		account.setCreateDate(new Date());

		account = dao.makePersistent(account);

		return new Identity(account.getId(), account.getName(), account.getPrincipal());
	}

	public Account retrieve(final Long id)
	{
		return dao.findById(id);
	}

	public Account retrieve(final String principal)
	{
		return dao.findByPrincipal(principal);
	}

	protected void updateActive(final Long id, final boolean active)
	{
		final Account account = dao.findById(id);

		if (account == null)
		{
			return;
		}

		account.setActive(active);

		dao.makePersistent(account);
	}

	public void activate(final Long id)
	{
		updateActive(id, true);
	}

	public void deactivate(final Long id)
	{
		updateActive(id, false);
	}

	public Identity resolveId(final Long _id)
	{
		final Account account = retrieve(_id);
		return account == null ? null : new Identity(account.getId(), account.getName(), account.getPrincipal());
	}

}
