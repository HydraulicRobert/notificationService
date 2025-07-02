package com.proxy.notifications.errorNotifications.Controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.notifications.errorNotifications.entity.notification;
import com.proxy.notifications.errorNotifications.entity.settings;
import com.proxy.notifications.errorNotifications.repository.notificationRepository;

import configuration.cfgInputOutput;
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
		if (cacheMgr.getCache("sqlChkSet").get("normalKey",Boolean.class) == null)
		{
			cacheMgr.getCache("sqlChkSet").put("normalKey",false);
		}
		boolean bolChkSet = (cacheMgr.getCache("sqlChkSet").get("normalKey",Boolean.class));
		String strCacheString = cacheMgr.getCache("settingsTimestamp").get("normalKey", String.class);
		String strSQLDate = "";
		if (bolChkSet == false)
		{
			bolChkSet = true;
			cacheMgr.getCache("sqlChkSet").put("normalKey",bolChkSet);
			strSQLDate = sttCtr.findTop1().getLastChangeOn();
			bolChkSet = false;
			cacheMgr.getCache("sqlChkSet").put("normalKey",bolChkSet);
		}
		if ((strCacheString == null))
		{
			strCacheString = "placeholder";
			System.out.println("settings timestamp is null. placeholder added: "+strCacheString);
		}
		if (!strCacheString.equals(strSQLDate))
		{
			System.out.println(strSQLDate+" S has been put into cache. doesn not equal "+strCacheString);
			strCacheString = strSQLDate;
			cacheMgr.getCache("settingsTimestamp").put("normalKey",strCacheString);
			return false;
		}else 
		{
			System.out.println(strSQLDate+" S has not been put into cache. does equal "+strCacheString);
		}
		return true;
	}
	@GetMapping("/notTop1")
	public boolean checkMostCurrentNotificationTimestampEqualsCache() 
	{
		if (cacheMgr.getCache("sqlChkNotTop1").get("normalKey",Boolean.class) == null)
		{
			cacheMgr.getCache("sqlChkNotTop1").put("normalKey",false);
			System.out.println("cache top 1 boolean null. set to false");
		}
		boolean bolChkNotTop1 = cacheMgr.getCache("sqlChkNotTop1").get("normalKey",Boolean.class);
		String strCacheString = cacheMgr.getCache("mostCurrentNotificationTimestamp").get("normalKey", String.class);
		String strSQLDate = "";
		if (bolChkNotTop1 == false)
		{
			bolChkNotTop1 = true;
			cacheMgr.getCache("sqlChkSet").put("normalKey",bolChkNotTop1);
			strSQLDate = notRep.findTop1ByOrderByStartDateDesc().getStartDate();
			bolChkNotTop1 = false;
			cacheMgr.getCache("sqlChkSet").put("normalKey",bolChkNotTop1);
		}
		if (strCacheString == null)
		{
			strCacheString = "placeholder";
			System.out.println("notification timestamp is null. placeholder added: "+strCacheString);
		}
		if (!strCacheString.equals(strSQLDate))
		{
			System.out.println(strSQLDate+" N1 has been put into cache. doesn not equal "+strCacheString);
			strCacheString = strSQLDate;
			//System.out.println("unequal settings dates. putting new into cache");
			cacheMgr.getCache("mostCurrentNotificationTimestamp").put("normalKey",strCacheString);
			//System.out.println("settings timestamp is: "+tmpString);
			return false;
		}else
		{
			System.out.println(strSQLDate+" N1 has not been put into cache. does equal "+strCacheString);
		}
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
	public List<notification> getNotifications(@RequestHeader(value = "username", required = false) String strUsername, @RequestHeader(value = "password", required = false) String strPassword) 
	{
		if ((strUsername == null) ||(strPassword == null)) {
			return null;
		}
		if (checkUserExistsCache(strUsername, strPassword))
		{
			if (cacheMgr.getCache("sqlChkNotAllTop").get("normalKey",Boolean.class) == null)
			{
				cacheMgr.getCache("sqlChkNotAllTop").put("normalKey",false);
				System.out.println("cache all Not top boolean null. set to false");
			}
			if (cacheMgr.getCache("sqlChkNotAllBottom").get("normalKey",Boolean.class) == null)
			{
				cacheMgr.getCache("sqlChkNotAllBottom").put("normalKey",false);
				System.out.println("cache all Not bottom boolean null. set to false");
			}
			boolean bolChkNotAllTop = cacheMgr.getCache("sqlChkNotAllTop").get("normalKey",Boolean.class);
			boolean bolChkNotAllBottom = cacheMgr.getCache("sqlChkNotAllBottom").get("normalKey",Boolean.class);
			Iterable<notification> iterNotification;
			List<notification> allNotifications = cacheMgr.getCache("notificationList").get("normalKey", ArrayList.class);
			if ((allNotifications == null) && (!bolChkNotAllTop)) {
				bolChkNotAllTop = true;
				cacheMgr.getCache("sqlChkNotAllTop").put("normalKey",bolChkNotAllTop);
				iterNotification = notRep.findAllByOrderByStartDateDesc();
				allNotifications = iteratorToListNotification(iterNotification,allNotifications);
				cacheMgr.getCache("notificationList").put("normalKey", allNotifications);
				bolChkNotAllTop = false;
				cacheMgr.getCache("sqlChkNotAllTop").put("normalKey",bolChkNotAllTop);
			}else {
				allNotifications = cacheMgr.getCache("notificationList").get("normalKey", ArrayList.class);
			}
			
			if (checkTimestampBigger6() == true) {
				System.out.println("timestamp bigger 6");
				if(!checkSettingsTimestampEqualsCache())
				{
					System.out.println("cache settings not equal db");
					if((!checkMostCurrentNotificationTimestampEqualsCache()) && (!bolChkNotAllBottom))
					{
						bolChkNotAllBottom = true;
						cacheMgr.getCache("sqlChkNotAllBottom").put("normalKey",bolChkNotAllTop);
						System.out.println("cache noti not equal db");
						iterNotification = notRep.findAllByOrderByStartDateDesc();
						//allNotifications.clear();
						allNotifications = iteratorToListNotification(iterNotification,allNotifications);
						cacheMgr.getCache("notificationList").put("normalKey", allNotifications);
						bolChkNotAllBottom = false;
						cacheMgr.getCache("sqlChkNotAllBottom").put("normalKey",bolChkNotAllTop);
					}
				}
			}
			return allNotifications;
		}else {
			return null;
		}
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
	public boolean checkUserListExistsCache() {
		try {
			if (cacheMgr.getCache("userList").get("normalKey") == null) {
				cacheMgr.getCache("userList").put("normalKey",cfgInputOutput.getUserList(Paths.get(System.getProperty("user.dir"),"configuration").toString(),"userlist.csv"));
				return true;
			}
		}catch(Exception E) {
			return false;
		}
		return true;
	}
	//public boolean checkUserExistsCache(String username, String password) {
	@GetMapping("/check")
	public boolean checkUserExistsCache(@RequestHeader(value = "username", required = false) String username, @RequestHeader(value = "password", required = false) String password) {	
		if (checkUserListExistsCache())
		{
			List<String[]> stlUserList = cacheMgr.getCache("userList").get("normalKey",ArrayList.class);
			String[] strUserList; 
			for(int i = 0;i<stlUserList.size();i++)
			{
				strUserList = stlUserList.get(i);
				if (strUserList[0].equals(username))
				{
					System.out.println("user "+username+" exists");
					if (BCrypt.checkpw(password, strUserList[1]))
					{
						System.out.println("password exists");
						return true;
					}
					//return true;
				}
			}
		}
		return false;
	}
	
}
