package cavani.endorfina.agency.web;

import static cavani.endorfina.agency.util.ViewConstants.PAGE_HOME;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cavani.endorfina.agency.model.domain.Identity;
import cavani.endorfina.agency.model.persistence.Account;
import cavani.endorfina.agency.model.view.AccountForm;
import cavani.endorfina.agency.service.AccountService;

@Model
public class AccountManagement
{

	@Inject
	Logger log;

	@Inject
	AccountService accountService;

	@Inject
	Identity identity;

	@Produces
	@Named
	AccountForm accountForm;

	@Inject
	ViewContext context;

	@PostConstruct
	void setup()
	{
		final Account account = accountService.retrieve(identity.getId());

		accountForm = new AccountForm();
		accountForm.setName(account.getName());
		accountForm.setPrincipal(account.getPrincipal());
		accountForm.setSecret(account.getSecret());
	}

	public String delete()
	{
		log.info("AccountManagement.delete for " + identity.getPrincipal());
		accountService.deactivate(identity.getId());
		context.logout();
		return PAGE_HOME;
	}

}
