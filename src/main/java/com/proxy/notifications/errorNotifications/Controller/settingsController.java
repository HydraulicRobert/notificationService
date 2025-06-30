package com.proxy.notifications.errorNotifications.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.notifications.errorNotifications.entity.settings;
import com.proxy.notifications.errorNotifications.repository.settingsRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
public class settingsController {

	@Autowired
	settingsRepository sttRep;
	
	@Autowired
	CacheManager cacheMgr;
	public settings findTop1() {
		return sttRep.findTop1ByOrderByLastChangeOnDesc();
	}
	public Iterable<settings> findAll() {
		//cacheMgr.getCache("settingsList").put("normalKey", sttRep.findAll()); 
		return sttRep.findAll();
	}
	
}
