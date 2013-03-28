package cavani.endorfina.agency.web;

import static cavani.endorfina.agency.util.ViewConstants.FORMID_RECOVERY;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cavani.endorfina.agency.model.persistence.Account;
import cavani.endorfina.agency.model.view.RecoveryForm;
import cavani.endorfina.agency.service.AccountService;
import cavani.endorfina.agency.service.EmailService;
import cavani.endorfina.agency.service.TokenService;

@Model
public class AccountRecovery
{

	@Inject
	Logger log;

	@Inject
	AccountService accountService;

	@Inject
	TokenService tokenService;

	@Inject
	EmailService emailService;

	@Produces
	@Named
	RecoveryForm recoveryForm;

	@Inject
	ViewContext context;

	@PostConstruct
	void setup()
	{
		recoveryForm = new RecoveryForm();
	}

	protected void reportFail(final String reason)
	{
		context.error(FORMID_RECOVERY, "Recovery fail! " + reason);
	}

	public String submit()
	{
		log.info("AccountRecovery for " + recoveryForm);
		try
		{
			final Account account = accountService.retrieve(recoveryForm.getPrincipal());
			if (account != null)
			{
				final String token = account.getActive() == null || !account.getActive() ? tokenService.encodeToken(account.getId()) : null;
				emailService.sendAccountRecoveryMail(account.getPrincipal(), account.getName(), account.getSecret(), token);
				context.info(FORMID_RECOVERY, "Recovery email will be sent!");
			}
			else
			{
				reportFail("Invalid ID.");
			}
			setup();
			log.info("AccountRecovery complete!");
			return null;
		}
		catch (final Exception e)
		{
			log.log(Level.INFO, "Recovery Account error!", e);
		}
		reportFail("Internal error.");
		return null;
	}

}
