package cavani.endorfina.agency.util;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggerProvider
{

	@Produces
	Logger getLogger(final InjectionPoint ip)
	{
		return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
	}

}
