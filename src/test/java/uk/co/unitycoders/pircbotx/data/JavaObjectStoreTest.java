/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.data;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author webpigeon
 */
public class JavaObjectStoreTest
{
	private ObjectStorage instance;

	public JavaObjectStoreTest()
	{
	}

	@Before
	public void setUp()
	{
		this.instance = JavaObjectStore.build("data/");
	}

	@After
	public void tearDown()
	{
	}

	/**
	 * Test of store method, of class JavaObjectStore.
	 * 
	 * @throws Exception if the test fails because something blew up.
	 */
	@Test
	public void testStoreLoad() throws Exception
	{
		System.out.println("store");
		String name = "testObject";

		Serializable object = "myTestObject";

		instance.store(name, object);
		Object result = instance.load(name);

		assertEquals(object, result);
	}

}
