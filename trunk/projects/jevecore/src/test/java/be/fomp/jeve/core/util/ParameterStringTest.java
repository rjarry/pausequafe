package be.fomp.jeve.core.util;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class ParameterStringTest extends TestCase{

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testParameterString() {
		try {
			@SuppressWarnings("unused")
			ParameterString p1,p2,p3,p4,p5,p6;
			
			
			p1 = new ParameterString();
			p2 = new ParameterString(p1);
			p3 = new ParameterString("test","value");
			p4 = new ParameterString("test2",1);
			p4 = new ParameterString(p1,p2);
			p5 = new ParameterString("test","value&");
			p6 = p1.addParameter(p2).addParameter(p3).addParameter("&key", "val&").addParameter("key", 1);
			p5.addParameter(p6);
			
			assertEquals("test=value&test=value&key=val&key=1", p5.toString());
			assertEquals("test=value&key=val&key=1&test=value",p1.encode(p3).toString());
		} catch (Exception e) {
			fail("should not get here");
		}
	}

}
