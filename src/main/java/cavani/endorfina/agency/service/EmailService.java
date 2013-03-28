package cavani.endorfina.agency.service;

import static cavani.endorfina.agency.util.EmailConstants.EMAIL_FROM;
import static cavani.endorfina.agency.util.EmailConstants.EMAIL_SUBJECT;
import static cavani.endorfina.agency.util.EmailConstants.EMAIL_TEXT;
import static cavani.endorfina.agency.util.EmailConstants.EMAIL_TO;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

@Stateless
@LocalBean
public class EmailService
{

	@Inject
	Logger log;

	@Inject
	ConfigurationService conf;

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/queue/EndorfinaAgencyMail")
	private Queue mailQueue;

	protected void send(final String from, final String to, final String subject, final String text) throws Exception
	{
		final Connection connection = connectionFactory.createConnection();
		try
		{
			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			try
			{
				final MessageProducer producer = session.createProducer(mailQueue);
				try
				{
					connection.start();
					final MapMessage message = session.createMapMessage();
					message.setString(EMAIL_FROM, from);
					message.setString(EMAIL_TO, to);
					message.setString(EMAIL_SUBJECT, subject);
					message.setString(EMAIL_TEXT, text);
					producer.send(message);
				}
				finally
				{
					producer.close();
				}
			}
			finally
			{
				session.close();
			}
		}
		finally
		{
			connection.close();
		}
	}

	public void sendAccountConfirmationMail(final String to, final String name, final String token)
	{
		try
		{
			log.info("Sending AccountConfirmation mail to " + to);

			final StringBuilder out = new StringBuilder();

			out.append(name).append(",\n\n");
			out.append("your account is created.\n\n");
			out.append("Access the follow link and sign in to activate:\n\n");
			out.append(conf.webRoot()).append("/Home/AccountActivation?token=").append(token).append("\n\n");
			out.append("Thanks\n");

			send(conf.emailFrom(), to, "Account confirmation", out.toString());

			log.info("AccountConfirmation mail sent to " + to);
		}
		catch (final Exception e)
		{
			log.log(Level.INFO, "Error sending 'AccountConfirmation' to " + to, e);
		}
	}

	public void sendAccountRecoveryMail(final String to, final String name, final String secret, final String token)
	{
		try
		{
			log.info("Sending AccountRecovery mail to " + to);

			final StringBuilder out = new StringBuilder();

			out.append(name).append(",\n\n");
			out.append("your password is '").append(secret).append("'.\n\n");

			if (token != null)
			{
				out.append("Access the follow link and sign in to activate:\n\n");
				out.append(conf.webRoot()).append("/Home/AccountActivation?token=").append(token).append("\n\n");
			}

			out.append("Thanks\n");

			send(conf.emailFrom(), to, "Account recovery", out.toString());

			log.info("AccountRecovery mail sent to " + to);
		}
		catch (final Exception e)
		{
			log.log(Level.INFO, "Error sending 'AccountRecovery' to " + to, e);
		}
	}

}
