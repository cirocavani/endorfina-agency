package cavani.endorfina.agency.web;

import static cavani.endorfina.agency.util.ViewConstants.FORMID_ACTIVATION;
import static cavani.endorfina.agency.util.ViewConstants.PAGE_MAIN;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cavani.endorfina.agency.model.domain.Identity;
import cavani.endorfina.agency.model.view.SignInForm;
import cavani.endorfina.agency.service.AccountService;
import cavani.endorfina.agency.service.TokenService;

@Model
public class AccountActivation
{

	@Inject
	Logger log;

	@Inject
	TokenService tokenService;

	@Inject
	AccountService accountService;

	@Inject
	ViewContext context;

	String token;

	@Produces
	@Named
	SignInForm activationForm;

	public String getToken()
	{
		return token;
	}

	public void setToken(final String token)
	{
		this.token = token;
	}

	@PostConstruct
	void setup()
	{
		activationForm = new SignInForm();
	}

	protected void reportFail(final String reason)
	{
		context.error(FORMID_ACTIVATION, "Activation fail! " + reason);
	}

	public String submit()
	{
		log.info("AccountActivation for " + activationForm + " (token " + token + ")");
		try
		{
			final Identity id = accountService.validate(activationForm.getPrincipal(), activationForm.getSecret());

			if (id == null)
			{
				log.info("Invalid ID.");
				reportFail("Invalid ID.");
				return null;
			}

			if (!tokenService.validate(token) || id.getId() != tokenService.decodeToken(token))
			{
				log.info("Invalid token.");
				reportFail("Invalid token.");
				return null;
			}

			accountService.activate(id.getId());

			context.login(id);

			log.info("AccountActivation complete!");
			return PAGE_MAIN;
		}
		catch (final Exception e)
		{
			log.log(Level.INFO, "Activation Account error!", e);
		}
		reportFail("Internal error.");
		return null;
	}

}
