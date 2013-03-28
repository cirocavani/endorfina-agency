package cavani.endorfina.agency.model.domain;

public class Identity
{

	private final Long id;

	private final String name;

	private final String principal;

	public Identity(final Long id, final String name, final String principal)
	{
		this.id = id;
		this.name = name;
		this.principal = principal;
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getPrincipal()
	{
		return principal;
	}

}
