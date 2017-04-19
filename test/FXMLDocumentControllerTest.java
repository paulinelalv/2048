/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guy Ju
 */
public class FXMLDocumentControllerTest {
	
	public FXMLDocumentControllerTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of initialize method, of class FXMLDocumentController.
	 */
	@Test
	public void testInitialize() {
		System.out.println("initialize");
		URL url = null;
		ResourceBundle rb = null;
		FXMLDocumentController instance = new FXMLDocumentController();
		instance.initialize(url, rb);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of keyPressed method, of class FXMLDocumentController.
	 */
	@Test
	public void testKeyPressed() {
		System.out.println("keyPressed");
		KeyEvent ke = null;
		FXMLDocumentController instance = new FXMLDocumentController();
		instance.keyPressed(ke);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
	
}
