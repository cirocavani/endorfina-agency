package cavani.endorfina.agency.service;

import static cavani.endorfina.agency.util.ConfigConstants.CONFIG_CIPHER_ALGORITHM;
import static cavani.endorfina.agency.util.ConfigConstants.CONFIG_CIPHER_KEY;
import static cavani.endorfina.agency.util.ConfigConstants.CONFIG_EMAIL_FROM;
import static cavani.endorfina.agency.util.ConfigConstants.CONFIG_EMAIL_SMTP;
import static cavani.endorfina.agency.util.ConfigConstants.CONFIG_WEB_ROOT;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;

import com.google.common.io.BaseEncoding;

@Singleton
@Startup
@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigProperties implements ConfigurationService
{

	private static final String DEFAULT_CONFIG_PROPERTIES = "META-INF/config.properties";

	String webRoot;

	String emailFrom;

	String emailSmtp;

	byte[] cipherKey;

	String cipherAlgorithm;

	@PostConstruct
	void setup() throws Exception
	{
		final Properties config = new Properties();
		try (InputStream in = getConfigProperties())
		{
			config.load(in);
		}
		webRoot = config.getProperty(CONFIG_WEB_ROOT);
		emailFrom = config.getProperty(CONFIG_EMAIL_FROM);
		emailSmtp = config.getProperty(CONFIG_EMAIL_SMTP);
		cipherKey = BaseEncoding.base64().decode(config.getProperty(CONFIG_CIPHER_KEY));
		cipherAlgorithm = config.getProperty(CONFIG_CIPHER_ALGORITHM);
	}

	InputStream getConfigProperties() throws IOException
	{
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_CONFIG_PROPERTIES);
	}

	@Override
	public String webRoot()
	{
		return webRoot;
	}

	@Override
	public String emailFrom()
	{
		return emailFrom;
	}

	@Override
	public String emailSmtp()
	{
		return emailSmtp;
	}

	@Override
	public byte[] cipherKey()
	{
		return Arrays.copyOf(cipherKey, cipherKey.length);
	}

	@Override
	public String cipherAlgorithm()
	{
		return cipherAlgorithm;
	}

}
