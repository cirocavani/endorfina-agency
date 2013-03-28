package cavani.endorfina.agency.web;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_FATAL;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import cavani.endorfina.agency.model.domain.Identity;
import cavani.endorfina.agency.service.IdentityService;

@RequestScoped
public class ViewContext
{

	@Inject
	Logger log;

	@Inject
	FacesContext context;

	@Inject
	IdentityService identityService;

	protected void addMessage(final String id, final Severity level, final String message)
	{
		context.addMessage(id, new FacesMessage(level, message, ""));
	}

	public void info(final String id, final String message)
	{
		addMessage(id, SEVERITY_INFO, message);
	}

	public void warn(final String id, final String message)
	{
		addMessage(id, SEVERITY_WARN, message);
	}

	public void error(final String id, final String message)
	{
		addMessage(id, SEVERITY_ERROR, message);
	}

	public void fatal(final String id, final String message)
	{
		addMessage(id, SEVERITY_FATAL, message);
	}

	public void login(final Identity id)
	{
		try
		{
			final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			request.login(id.getPrincipal(), id.getPrincipal());
			identityService.login(id);
		}
		catch (final ServletException e)
		{
			log.log(Level.INFO, "Login error!", e);
		}
	}

	public void logout()
	{
		try
		{
			identityService.logout();
			final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			request.logout();
		}
		catch (final ServletException e)
		{
			log.log(Level.INFO, "Logout error!", e);
		}
	}
}
