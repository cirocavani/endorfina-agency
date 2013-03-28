package cavani.endorfina.agency.service;

public interface ConfigurationService
{

	String webRoot();

	String emailFrom();

	String emailSmtp();

	byte[] cipherKey();

	String cipherAlgorithm();

}
