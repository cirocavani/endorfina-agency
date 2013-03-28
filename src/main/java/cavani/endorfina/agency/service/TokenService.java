package cavani.endorfina.agency.service;

import javax.inject.Inject;

import com.google.common.io.BaseEncoding;

public class TokenService
{

	@Inject
	CipherService cipherService;

	public boolean validate(final String token)
	{
		return token != null && !token.trim().isEmpty();
	}

	public String encodeToken(final Long id) throws Exception
	{
		final byte[] data = String.valueOf(id).getBytes("UTF-8");
		final byte[] raw = cipherService.encrypt(data);
		return BaseEncoding.base64Url().encode(raw);
	}

	public Long decodeToken(final String token) throws Exception
	{
		final byte[] data = BaseEncoding.base64Url().decode(token);
		final byte[] raw = cipherService.decrypt(data);
		return Long.parseLong(new String(raw));
	}

}
