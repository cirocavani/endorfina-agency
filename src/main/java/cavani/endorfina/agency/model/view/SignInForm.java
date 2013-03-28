package cavani.endorfina.agency.model.view;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class SignInForm
{

	@NotNull
	@Email
	private String principal;

	@NotNull
	@Size(min = 5, max = 15)
	private String secret;

	@Override
	public String toString()
	{
		return "SignInForm [principal=" + principal + ", secret=" + secret + "]";
	}

	public String getPrincipal()
	{
		return principal;
	}

	public void setPrincipal(final String principal)
	{
		this.principal = principal;
	}

	public String getSecret()
	{
		return secret;
	}

	public void setSecret(final String secret)
	{
		this.secret = secret;
	}

}
