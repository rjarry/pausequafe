package be.fomp.jeve.core.config;

import java.net.Proxy.Type;
import java.security.InvalidParameterException;

import org.junit.Test;

import be.fomp.jeve.core.exceptions.JEveException;

import junit.framework.TestCase;

public class ConfigurationTest extends TestCase{

	@Test
	public void testConfiguration() {
		try {
			Configuration configuration = Configuration.getInstance();
			configuration.loadConfiguration();
			configuration.storeConfiguration();
			configuration.getProxyConfiguration();
			try {
				configuration.setProxyconfiguration(true, Type.DIRECT, "", 0, false, null, null);
			} catch(InvalidParameterException ipe) {}
			configuration.setProxyconfiguration(true, Type.DIRECT, "", 0, false, "user", "pass");
			configuration.getProxyConfiguration();
			
			try {
				configuration.setProxyconfiguration(true, Type.DIRECT, null, -1, true, "", "");
				configuration.getProxyConfiguration();
			} catch(InvalidParameterException ipe) {}
			
			configuration.setProxyconfiguration(true, Type.DIRECT, "proxy", 8080, true, "user", "pass");
			configuration.getProxyConfiguration();
		} catch (JEveException je) {
			fail("should not get here");
		}
	}
}
