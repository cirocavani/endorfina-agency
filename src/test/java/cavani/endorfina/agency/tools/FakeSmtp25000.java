package cavani.endorfina.agency.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.server.SMTPServer;

public final class FakeSmtp25000
{

	static class OutputHandler implements MessageHandler
	{

		MessageContext ctx;

		public OutputHandler(final MessageContext ctx)
		{
			this.ctx = ctx;
		}

		@Override
		public void from(final String from) throws RejectException
		{
			System.out.println("FROM: " + from);
		}

		@Override
		public void recipient(final String recipient) throws RejectException
		{
			System.out.println("RECIPIENT: " + recipient);
		}

		@Override
		public void data(final InputStream data) throws IOException
		{
			System.out.println("MAIL DATA");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
			print(data, System.out);
			System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
		}

		@Override
		public void done()
		{
			System.out.println("Finished");
		}

		public void print(final InputStream in, final PrintStream out)
		{
			try (final Reader _reader = new InputStreamReader(in);
				final BufferedReader reader = new BufferedReader(_reader))
			{
				String line = null;
				while ((line = reader.readLine()) != null)
				{
					out.println(line);
				}
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	static class OutputHandlerFactory implements MessageHandlerFactory
	{

		@Override
		public MessageHandler create(final MessageContext ctx)
		{
			return new OutputHandler(ctx);
		}

	}

	public static void main(final String[] args) throws Exception
	{
		final OutputHandlerFactory outputFactory = new OutputHandlerFactory();
		final SMTPServer smtpServer = new SMTPServer(outputFactory);
		smtpServer.setPort(25000);
		smtpServer.start();
	}

}
