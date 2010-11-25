package be.fomp.jeve.core.api.connectors;

import be.fomp.jeve.core.api.FullAPI;
import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.StandardAPI;
import be.fomp.jeve.core.config.Configuration;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveException;
/**
 * A factory which provides the connectors to the API.
 * @author meyssven
 *
 */
public final class JEveConnectionFactory {
	/**
	 * A unique instance of the factory..only need one
	 */
	private static JEveConnectionFactory instance = null;
	
	/**
	 * The configuration object.
	 */
	private static Configuration config;
	
	/**
	 * This method initializes the factory and returns an instance of it.
	 * @return An instance of the factory
	 * @throws JEveException When the configuration object failed to initialize.
	 */
	public static JEveConnectionFactory createFactory() throws JEveConfigurationException{
		if(instance == null) {
			instance = new JEveConnectionFactory();
		}
		return instance;
	}
	
	/**
	 * Private constructor which loads the configuration
	 * 
	 * @throws JEveException when initializing the configuration failed
	 */
	private JEveConnectionFactory() throws JEveConfigurationException{
		config = Configuration.getInstance();
	}
	
	/**
	 * This method returns a connector to use the standard API
	 * @return a standard API object
	 */
	public StandardAPI getStandardConnection()
	{
		return new StandardConnector(config);
	}
	
	/**
	 * This method returns a connector to use the limited API
	 * @return a limited API object
	 */
	public LimitedAPI getLimitedConnection()
	{
		return new LimitedConnector(config);
	}
	
	/**
	 * This method returns a connector to use the full API
	 * @return a full API object
	 */
	public FullAPI getFullConnection()
	{
		return new FullConnector(config);
	}
	
	/**
	 * Getter for the configuration
	 * @return The configuration object
	 */
	public Configuration getConfiguration(){
		return config;
	}
	
}
