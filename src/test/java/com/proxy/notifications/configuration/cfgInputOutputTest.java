package com.proxy.notifications.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.AssertTrue;

class cfgInputOutputTest {

	cfgInputOutput tstCfg;
	Path tstFolder = Paths.get(System.getProperty("user.dir"),"JUnit");
	Path fileTxt = Paths.get(System.getProperty("user.dir"),"JUnit","testtext.txt");
	Path fileIni = Paths.get(System.getProperty("user.dir"),"JUnit","test.ini");
	Path fileUser = Paths.get(System.getProperty("user.dir"),"JUnit","testUser.csv");
	@BeforeEach
	void setUp() throws Exception {
		tstCfg = new cfgInputOutput();
		try {
			Files.deleteIfExists(fileUser);
			Files.deleteIfExists(fileTxt);
			Files.deleteIfExists(fileIni);
		} catch (IOException e) {
		}
	}
	@AfterEach
	void destroy() throws Exception{
		try {
			Files.deleteIfExists(fileUser);
			Files.deleteIfExists(fileTxt);
			Files.deleteIfExists(fileIni);
		} catch (IOException e) {
		}
	}

	@Test
	void testCreateFile() {
		assertTrue(cfgInputOutput.createFile(tstFolder.toString(), "testtext.txt"));
		try {
			Files.deleteIfExists(fileTxt);
		} catch (IOException e) {
		}
	//	fail("Not yet implemented");
	}

	@Test
	void testBlankIni() {
		cfgInputOutput.createFile(tstFolder.toString(), "test.ini");
		assertTrue(cfgInputOutput.blankIni(tstFolder.toString(), "test.ini"));
		//fail("Not yet implemented");
	}

	@Test
	void testFillIni() {
		cfgInputOutput.createFile(tstFolder.toString(), "test.ini");
		//tstCfg.fillIni(tstFolder, "test.ini");
		assertTrue(Files.exists(Paths.get(tstFolder.toString(), "test.ini")));
		//fail("Not yet implemented");
	}

	@Test
	void testExitApp() {
		System.out.println("can't be implemented yet");
	}

	@Test
	void testProps() {
		cfgInputOutput.createFile(tstFolder.toString(), "test.ini");
		cfgInputOutput.blankIni(tstFolder.toString(), "test.ini");
		assertNull(cfgInputOutput.props(tstFolder.toString(),"test.ini").getProperty("url"));
		//fail("Not yet implemented");
	}

	@Test
	void testAddUserFile() {
		cfgInputOutput.createFile(tstFolder.toString(), "testUser.csv");
		cfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv");
		assertFalse(cfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv"));
		
		//fail("Not yet implemented");
	}

	@Test
	void testRemoveUserFile() {
		cfgInputOutput.createFile(tstFolder.toString(), "testUser.csv");
		cfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv");
		assertTrue(cfgInputOutput.removeUserFile("max", tstFolder.toString(), "testUser.csv"));
	}

	@Test
	void testExistsUserFile() {
		cfgInputOutput.createFile(tstFolder.toString(), "testUser.csv");
		cfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv");
		assertTrue(cfgInputOutput.existsUserFile("max", "password", tstFolder.toString(), "testUser.csv"));
	}

	@Test
	void testGetUserList() {
		cfgInputOutput.createFile(tstFolder.toString(), "testUser.csv");
		cfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv");
		assertEquals(1,cfgInputOutput.getUserList(tstFolder.toString(), "testUser.csv").size());
		//fail("Not yet implemented");
	}

	@Test
	void testLogRequests() {
		fail("interface später");
	}

}
