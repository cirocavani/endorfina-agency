package cavani.endorfina.agency.service;

import java.security.Key;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Singleton
@Startup
@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CipherService
{

	@Inject
	Logger log;

	@Inject
	ConfigurationService conf;

	private Cipher encryptor;

	private Cipher decryptor;

	@PostConstruct
	void setup() throws Exception
	{
		log.info("Initializing CipherService...");

		final String algorithm = conf.cipherAlgorithm();
		final Key key = new SecretKeySpec(conf.cipherKey(), algorithm);

		encryptor = Cipher.getInstance(algorithm);
		encryptor.init(Cipher.ENCRYPT_MODE, key);

		decryptor = Cipher.getInstance(algorithm);
		decryptor.init(Cipher.DECRYPT_MODE, key);

		log.info("CipherService complete.");
	}

	public byte[] encrypt(final byte[] data) throws Exception
	{
		return encryptor.doFinal(data);
	}

	public byte[] decrypt(final byte[] data) throws Exception
	{
		return decryptor.doFinal(data);
	}
}
