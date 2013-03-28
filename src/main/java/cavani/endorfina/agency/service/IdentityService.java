package cavani.endorfina.agency.service;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import cavani.endorfina.agency.model.domain.Identity;

@SessionScoped
public class IdentityService implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Identity id;

	public void login(final Identity id)
	{
		this.id = id;
	}

	public void logout()
	{
		id = null;
	}

	public Identity id()
	{
		return id;
	}

	public boolean isLogged()
	{
		return id != null;
	}

}
