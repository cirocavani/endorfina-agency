package cavani.endorfina.agency.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class FacesProvider
{

	@Produces
	@RequestScoped
	private FacesContext getFacesContext()
	{
		return FacesContext.getCurrentInstance();
	}

}
