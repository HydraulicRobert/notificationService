package com.proxy.notifications.errorNotifications.Controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.notifications.errorNotifications.entity.notification;
import com.proxy.notifications.errorNotifications.entity.settings;
import com.proxy.notifications.errorNotifications.repository.notificationRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
public class notificationController {
	@Autowired
	notificationRepository notRep;
	
	@Autowired
	settingsController sttCtr;
	
	@Autowired
	CacheManager cacheMgr;
	
	
	
	public List<notification> iteratorToListNotification(Iterable<notification> tmpIterNotification,List<notification> tmpAllNotifications)
	{

		tmpAllNotifications = new ArrayList<>();
		for (notification curNotification: tmpIterNotification) {
			tmpAllNotifications.add(curNotification);
		}
		return tmpAllNotifications;
	}
	
	@GetMapping("/set")
	public boolean checkSettingsTimestampEqualsCache() 
	{
		String tmpString = cacheMgr.getCache("settingsTimestamp").get("normalKey", String.class);
		settings setting = sttCtr.findTop1();
		if ((tmpString == null))
		{
			tmpString = "placeholder";
			System.out.println("settings timestamp is null. placeholder added: "+tmpString);
		}
		if (!tmpString.equals(setting.getLastChangeOn()))
		{
			System.out.println(setting.getLastChangeOn()+" has been put into cache. doesn not equal "+tmpString);
			tmpString = setting.getLastChangeOn();
			cacheMgr.getCache("settingsTimestamp").put("normalKey",tmpString);
			return false;
		}
		System.out.println(setting.getLastChangeOn()+" S has not been put into cache. does equal "+tmpString);
		return true;
	}
	@GetMapping("/notTop1")
	public boolean checkMostCurrentNotificationTimestampEqualsCache() 
	{
		String tmpString = cacheMgr.getCache("mostCurrentNotificationTimestamp").get("normalKey", String.class);
		notification mostCurrent = notRep.findTop1ByOrderByStartDateDesc();
		if (tmpString == null)
		{
			tmpString = "placeholder";
			System.out.println("notification timestamp is null. placeholder added: "+tmpString);
		}
		if (!tmpString.equals(mostCurrent.getStartDate()))
		{
			System.out.println(mostCurrent.getStartDate()+" N1 has been put into cache. doesn not equal "+tmpString);
			tmpString = mostCurrent.getStartDate();
			//System.out.println("unequal settings dates. putting new into cache");
			cacheMgr.getCache("mostCurrentNotificationTimestamp").put("normalKey",tmpString);
			//System.out.println("settings timestamp is: "+tmpString);
			return false;
		}
		System.out.println(mostCurrent.getStartDate()+" has not been put into cache. does equal "+tmpString);
		//checkTimestampBigger6();
//		System.out.println(System.getProperty("user.dir"));
		return true;
	}
	@GetMapping("/notFin")
	public List<notification> getNotificationsWhenFinished()
	{
		return cacheMgr.getCache("notificationList").get("normalKey", ArrayList.class);
	}
	@GetMapping("/notAll")
	public List<notification> getNotifications() 
	{
		Iterable<notification> iterNotification;
		List<notification> allNotifications = cacheMgr.getCache("notificationList").get("normalKey", ArrayList.class);
		if (allNotifications == null) {
			iterNotification = notRep.findAllByOrderByStartDateDesc();
			allNotifications = iteratorToListNotification(iterNotification,allNotifications);
			cacheMgr.getCache("notificationList").put("normalKey", allNotifications);
		}else {
			allNotifications = cacheMgr.getCache("notificationList").get("normalKey", ArrayList.class);
		}
		
		if (checkTimestampBigger6() == true) {
			System.out.println("timestamp bigger 6");
			if(!checkSettingsTimestampEqualsCache())
			{
				System.out.println("cache settings not equal db");
				if(!checkMostCurrentNotificationTimestampEqualsCache())
				{
					System.out.println("cache noti not equal db");
					iterNotification = notRep.findAllByOrderByStartDateDesc();
					//allNotifications.clear();
					allNotifications = iteratorToListNotification(iterNotification,allNotifications);
					cacheMgr.getCache("notificationList").put("normalKey", allNotifications);
				}
			}
		}
		return allNotifications;
		
	}
	@GetMapping("/chk6")
	public boolean checkTimestampBigger6() 
	{
		if ( cacheMgr.getCache("sixSecondTimestamp").get("longValue", Long.class) == null) {
			cacheMgr.getCache("sixSecondTimestamp").put("longValue", System.currentTimeMillis()/1000L);
		}
		long lngTmp = cacheMgr.getCache("sixSecondTimestamp").get("longValue", Long.class);
		if (Long.toString(lngTmp).trim().isEmpty()) {
			lngTmp = System.currentTimeMillis()/1000L;
		}
		long i = System.currentTimeMillis()/1000L-lngTmp;
		System.out.println("checkTimestampSmallerthan "+i);
		if (i >= 6) {
			cacheMgr.getCache("sixSecondTimestamp").clear();
			cacheMgr.getCache("sixSecondTimestamp").put("longValue", System.currentTimeMillis()/1000L);
			System.out.println("refresh timestamp refreshed");
			//System.out.println(cacheMgr.getCache("sixSecondTimestamp").get("longValue", Long.class));
			return true;
		}
		return false;
	}
}
