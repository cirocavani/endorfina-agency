package cavani.endorfina.agency.web;

import static cavani.endorfina.agency.util.ViewConstants.FORMID_SIGNIN;
import static cavani.endorfina.agency.util.ViewConstants.PAGE_MAIN;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cavani.endorfina.agency.model.domain.Identity;
import cavani.endorfina.agency.model.view.SignInForm;
import cavani.endorfina.agency.service.AccountService;

@Model
public class AccountSignIn
{

	@Inject
	Logger log;

	@Inject
	AccountService accountService;

	@Produces
	@Named
	SignInForm signInForm;

	@Inject
	ViewContext context;

	@PostConstruct
	void setup()
	{
		signInForm = new SignInForm();
	}

	public String submit()
	{
		log.info("AccountSignIn for " + signInForm);
		final Identity id = accountService.authenticate(signInForm.getPrincipal(), signInForm.getSecret());
		final String outcome;
		if (id != null)
		{
			setup();
			context.login(id);
			outcome = PAGE_MAIN;
		}
		else
		{
			context.error(FORMID_SIGNIN, "Sign In fail!");
			signInForm.setSecret("");
			outcome = null;
		}
		log.info("AccountSignIn complete!");
		return outcome;
	}

}
