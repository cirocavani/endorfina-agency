package cavani.endorfina.agency.model.view;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class AccountForm
{

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
	private String name;

	@NotNull
	@Email
	private String principal;

	@NotNull
	@Size(min = 5, max = 15)
	private String secret;

	@Override
	public String toString()
	{
		return "AccountForm [name=" + name + ", principal=" + principal + ", secret=" + secret + "]";
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
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
