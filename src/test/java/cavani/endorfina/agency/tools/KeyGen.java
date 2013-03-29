package cavani.endorfina.agency.tools;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.google.common.io.BaseEncoding;

public final class KeyGen
{

	private KeyGen()
	{
	}

	public static void main(final String[] args) throws Exception
	{
		final String algorithm;
		if (args.length > 0)
		{
			algorithm = args[0];
		}
		else
		{
			algorithm = "DESede";
		}

		final SecretKey key = KeyGenerator.getInstance(algorithm).generateKey();
		final byte[] raw = key.getEncoded();
		final String out = BaseEncoding.base64().encode(raw);

		System.out.println(out);
	}

}
