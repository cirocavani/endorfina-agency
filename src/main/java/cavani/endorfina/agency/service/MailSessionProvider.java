package cavani.endorfina.agency.service;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.mail.Session;


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
			case "default":
				return defaultSession;
			case "gmail":
				return gmailSession;
			default:
				return null;
		}
	}
}
