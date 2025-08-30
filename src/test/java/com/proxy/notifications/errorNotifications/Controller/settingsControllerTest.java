package com.proxy.notifications.errorNotifications.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxy.notifications.errorNotifications.entity.settings;
import com.proxy.notifications.errorNotifications.repository.settingsRepository;


@ExtendWith(MockitoExtension.class)
class settingsControllerTest {


	private settingsController tstSttCon;
	@Mock
	private settingsRepository tstSttRep;
	@Mock
	private settings tstStt;
	@Mock
	private settings tstStt2;
	
	@BeforeEach
	void setUp() throws Exception {
		tstSttCon = new settingsController();
		tstSttRep = mock(settingsRepository.class, RETURNS_SMART_NULLS);
		tstSttCon.sttRep = tstSttRep;
		tstStt = mock(settings.class, RETURNS_SMART_NULLS);
		tstStt2 = mock(settings.class, RETURNS_SMART_NULLS);
	}

	@Test
	void testFindTop1() {
		tstStt = new settings();
		tstStt.setLastChangeBy("kevin");
		tstStt.setLastChangeOn("2025-01-01 23:00:01");
		when(tstSttRep.findTop1By()).thenReturn(tstStt);
		assertEquals(tstStt, tstSttCon.findTop1());
	}
	@Test
	void testFindTop1EmptyObject() {
		tstStt = new settings();
		when(tstSttRep.findTop1By()).thenReturn(tstStt);
		assertEquals(0,tstSttCon.findTop1().getId());
		assertNull(tstSttCon.findTop1().getLastChangeBy());
		assertNull(tstSttCon.findTop1().getLastChangeOn());
	}
	
	@Test
	void testFindTop1Null() {
		when(tstSttRep.findTop1By()).thenReturn(null);
		assertNull(tstSttCon.findTop1());
	}
	

}
