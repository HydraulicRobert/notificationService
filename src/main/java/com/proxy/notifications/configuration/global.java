package com.proxy.notifications.configuration;

import java.nio.file.Paths;

public class global {
	//files
	private static final String gStrCfgPath 	= Paths.get(System.getProperty("user.dir"),"configuration").toString();
	private static final String gStrCfgName 	= "vsystem.ini";
	private static final String gStrUserList 	= "userlist.csv";
	
	//sql
	public static final String gStrSQLGetSpecificSettings = 
			"SELECT * from settings where last_Change_By like 'kevin'";
	//variable contents
	//main
	private static final String[] gStrNotAddUser 			= {"-a","--add","if not exists add a user. -a username password","3"};
	private static final String[] gStrNotShowUser 			= {"-s","--show","print a list of every user","1"};
	private static final String[] gStrNotRemoveUser 		= {"-r","--remove","if a user exists remove from list","2"};
	private static final String[] gStrNotConfigure 			= {"-c","--configuration","configure the ini","1"};
	private static final String[] gStrNotHelp 				= {"-h","--help","show commands","1"};
	private static final String gStrNotFlddUserfileCreate 	= "userlist not created";
	private static final String gStrNotUserfileCreate 		= "userlist created";
	private static final String gStrNotShowUsersName 		= "user: ";
	private static final String gStrNotShowUsersPassword	= "; password: ";
	private static final String gStrNotArgumentsAmount		= "arguments required: ";
	public static String getGstrcfgpath() {
		return gStrCfgPath;
	}
	public static String getGstrcfgname() {
		return gStrCfgName;
	}
	public static String getGstruserlist() {
		return gStrUserList;
	}
	public static String getGstrsqlgetspecificsettings() {
		return gStrSQLGetSpecificSettings;
	}
	public static String[] getGstrnotadduser() {
		return gStrNotAddUser;
	}
	public static String[] getGstrnotshowuser() {
		return gStrNotShowUser;
	}
	public static String[] getGstrnotremoveuser() {
		return gStrNotRemoveUser;
	}
	public static String[] getGstrnotconfigure() {
		return gStrNotConfigure;
	}
	public static String[] getGstrnothelp() {
		return gStrNotHelp;
	}
	public static String getGstrnotfldduserfilecreate() {
		return gStrNotFlddUserfileCreate;
	}
	public static String getGstrnotuserfilecreate() {
		return gStrNotUserfileCreate;
	}
	public static String getGstrnotshowusersname() {
		return gStrNotShowUsersName;
	}
	public static String getGstrnotshowuserspassword() {
		return gStrNotShowUsersPassword;
	}
	public static String getGstrnotargumentsamount() {
		return gStrNotArgumentsAmount;
	}
	
}
