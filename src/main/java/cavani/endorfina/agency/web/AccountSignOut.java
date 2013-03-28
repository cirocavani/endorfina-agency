package cavani.endorfina.agency.web;

import static cavani.endorfina.agency.util.ViewConstants.PAGE_HOME;

import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import cavani.endorfina.agency.model.domain.Identity;

@Model
public class AccountSignOut
{

	@Inject
	Logger log;

	@Inject
	Identity id;

	@Inject
	ViewContext context;

	public String submit()
	{
		log.info("AccountSignOut for " + id.getPrincipal());
		context.logout();
		return PAGE_HOME;
	}

}
