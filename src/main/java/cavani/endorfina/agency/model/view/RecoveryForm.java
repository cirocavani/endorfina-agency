package cavani.endorfina.agency.model.view;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class RecoveryForm
{

	@NotNull
	@Email
	private String principal;

	@Override
	public String toString()
	{
		return "RecoveryForm [principal=" + principal + "]";
	}

	public String getPrincipal()
	{
		return principal;
	}

	public void setPrincipal(final String principal)
	{
		this.principal = principal;
	}

}
