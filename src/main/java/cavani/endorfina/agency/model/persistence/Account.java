package cavani.endorfina.agency.model.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Account
{

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String principal;

	@Column(nullable = false)
	private String secret;

	private Boolean active;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
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

	public Boolean getActive()
	{
		return active;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(final Date createDate)
	{
		this.createDate = createDate;
	}

}
