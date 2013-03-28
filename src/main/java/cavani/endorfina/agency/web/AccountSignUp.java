package cavani.endorfina.agency.web;

import static cavani.endorfina.agency.util.ViewConstants.FORMID_SIGNUP;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cavani.endorfina.agency.model.domain.Identity;
import cavani.endorfina.agency.model.view.SignUpForm;
import cavani.endorfina.agency.service.AccountService;
import cavani.endorfina.agency.service.EmailService;
import cavani.endorfina.agency.service.TokenService;

@Model
public class AccountSignUp
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
	SignUpForm signUpForm;

	@Inject
	ViewContext context;

	@PostConstruct
	void setup()
	{
		signUpForm = new SignUpForm();
	}

	protected void reportFail(final String reason)
	{
		context.error(FORMID_SIGNUP, "Sign Up fail! " + reason);
	}

	public String submit()
	{
		log.info("AccountSignUp for " + signUpForm);
		try
		{
			final Identity id = accountService.create(signUpForm.getName(), signUpForm.getPrincipal(), signUpForm.getSecret());
			if (id != null)
			{
				final String token = tokenService.encodeToken(id.getId());
				emailService.sendAccountConfirmationMail(id.getPrincipal(), id.getName(), token);
				context.info(FORMID_SIGNUP, "Confirmation email will be sent!");
			}
			else
			{
				reportFail("Invalid ID.");
			}
			setup();
			log.info("AccountSignUp complete!");
			return null;
		}
		catch (final Exception e)
		{
			log.log(Level.INFO, "Activation Account error!", e);
		}
		reportFail("Internal error.");
		return null;
	}

}
