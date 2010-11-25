package be.fomp.jeve.core;

import java.net.Proxy.Type;

import org.junit.Test;

import be.fomp.jeve.core.util.Proxy;

public class ConnectorProxyTest extends APIData{

	@Test
	public void testConnectorProxy() {
		try {
			new Proxy(null,"host",8080);
			fail("should not get here");
		}catch(Exception e){}
		try {
			new Proxy(Type.HTTP,null,8080);
			fail("should not get here");
		}catch(Exception e){}
		try {
			new Proxy(Type.HTTP,"host",-1);
			fail("should not get here");
		}catch(Exception e){}
		try {
			new Proxy(Type.HTTP,"host",8080,null,"test");
			fail("should not get here");
		}catch(Exception e){}
		try {
			new Proxy(Type.HTTP,"host",8080,"test",null);
			fail("should not get here");
		}catch(Exception e){}
		try {
			new Proxy(null,"host",8080,"test","test");
			fail("should not get here");
		}catch(Exception e){}
		try {
			new Proxy(Type.HTTP,null,8080,"test","test");
			fail("should not get here");
		}catch(Exception e){}
		try {
			new Proxy(Type.HTTP,"host",-1,"test","test");
			fail("should not get here");
		}catch(Exception e){}
		
		Proxy p1 = new Proxy(Type.HTTP,"host",8080,"user","pass");
		
		Type t = p1.getType();
		assertEquals(Type.HTTP,t);
	}
}
