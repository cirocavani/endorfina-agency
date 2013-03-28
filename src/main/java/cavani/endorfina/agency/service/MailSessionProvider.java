package cavani.endorfina.agency.service;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.mail.Session;

import cavani.endorfina.agency.util.ConfigConstants;


public class MailSessionProvider
{

	@Inject
	ConfigurationService conf;

	@Resource(mappedName = "java:jboss/mail/Default")
	private Session defaultSession;

	@Resource(mappedName = "java:jboss/mail/GMail")
	private Session gmailSession;

	@Produces
	Session getMailSession()
	{
		switch (conf.emailSmtp())
		{
			case ConfigConstants.CONFIG_EMAIL_SMTP_DEFAULT:
				return defaultSession;
			case ConfigConstants.CONFIG_EMAIL_SMTP_GMAIL:
				return gmailSession;
			default:
				return null;
		}
	}
}
