package cavani.endorfina.agency.web;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cavani.endorfina.agency.model.domain.Identity;
import cavani.endorfina.agency.service.IdentityService;

public class IdentityProvider
{

	@Inject
	IdentityService identityService;

	@Produces
	@Named
	public Identity getIdentity()
	{
		return identityService.id();
	}

}
