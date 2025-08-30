package com.proxy.notifications.errorNotifications.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.proxy.notifications.errorNotifications.entity.settings;

@ExtendWith(MockitoExtension.class)
class settingsRepositoryTest {
	@Mock
	private settingsRepository tstSttRep;
	@Mock
	private settings tstStt;
	@Mock
	private settings tstStt2;
	
	@BeforeEach
	void setUp() throws Exception {
		tstSttRep = mock(settingsRepository.class,RETURNS_SMART_NULLS);
		tstStt = mock(settings.class, RETURNS_SMART_NULLS);
		tstStt2 = mock(settings.class, RETURNS_SMART_NULLS);
	}

	@Test
	void testFindTop1() {
		tstStt = new settings();
		tstStt.setId(0);
		tstStt.setLastChangeBy("kevin");
		tstStt.setLastChangeOn("2025-01-01 23:00:01");
		when(tstSttRep.findTop1By()).thenReturn(tstStt);
		assertEquals(tstStt,tstSttRep.findTop1By());
	}
	
	@Test
	void testFindTopNoContent() {
		tstStt = new settings();
		when(tstSttRep.findTop1By()).thenReturn(tstStt);
		assertNotNull(tstSttRep.findTop1By().getId());
		assertNull(tstSttRep.findTop1By().getLastChangeBy());
		assertNull(tstSttRep.findTop1By().getLastChangeOn());
	}
	
	@Test
	void testFindTop1NoObject() {
		when(tstSttRep.findTop1By()).thenReturn(null);
		assertNull(tstSttRep.findTop1By());
	}
	
	@Test
	void testFindAllObject() {
		tstStt = new settings();
		tstStt.setId(0);
		tstStt.setLastChangeBy("kevin");
		tstStt.setLastChangeOn("2025-01-01 23:00:01");
		tstStt2 = new settings();
		tstStt2.setId(1);
		tstStt2.setLastChangeBy("max");
		tstStt2.setLastChangeOn("2026-01-01 23:00:01");
		List<settings> itlSettings = new ArrayList<>();
		itlSettings.add(tstStt);
		itlSettings.add(tstStt2);
		when(tstSttRep.findAll()).thenReturn(itlSettings);
		List<settings> objectList = (List<settings>)tstSttRep.findAll();
		assertEquals(objectList, tstSttRep.findAll());
	}
	@Test
	void testFindAllObjectNone() {
		List<settings> itlSettings = new ArrayList<>();
		when(tstSttRep.findAll()).thenReturn(itlSettings);
		List<settings> objectList = (List<settings>)tstSttRep.findAll();
		assertThat(objectList.isEmpty());
	}

}
