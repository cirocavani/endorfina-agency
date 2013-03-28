package cavani.endorfina.agency.service;

import static cavani.endorfina.agency.util.EmailConstants.EMAIL_FROM;
import static cavani.endorfina.agency.util.EmailConstants.EMAIL_SUBJECT;
import static cavani.endorfina.agency.util.EmailConstants.EMAIL_TEXT;
import static cavani.endorfina.agency.util.EmailConstants.EMAIL_TO;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@MessageDriven(activationConfig =
{
	@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
	@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/EndorfinaAgencyMail"),
	@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "auto-acknowledge")
})
public class EmailHandler implements MessageListener
{

	@Inject
	Logger log;

	@Resource(mappedName = "java:jboss/mail/Default")
	private Session mailSession;

	protected void send(final String from, final String to, final String subject, final String text) throws Exception
	{
		final MimeMessage message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject, "UTF-8");
		message.setText(text, "UTF-8");
		Transport.send(message);
	}

	@Override
	public void onMessage(final Message message)
	{
		if (!(message instanceof MapMessage))
		{
			log.info("Ignoring no MapMessage: " + message.getClass().getName());
			return;
		}

		try
		{
			final MapMessage m = (MapMessage) message;
			final String from = m.getString(EMAIL_FROM);
			final String to = m.getString(EMAIL_TO);
			final String subject = m.getString(EMAIL_SUBJECT);
			final String text = m.getString(EMAIL_TEXT);
			send(from, to, subject, text);
		}
		catch (final Exception e)
		{
			log.log(Level.INFO, "Error sending mail!", e);
			throw new EJBException("Error sending mail!", e);
		}
	}

}
