package com.proxy.notifications.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class cfgInputOutput {
	
	public boolean createFile(String strPath, String strFilename) {
		String strCfgPath = strPath.toString();
		String strUserListPath = strFilename;
		File checkExists = new File(strCfgPath);
		if (!(checkExists.exists() && checkExists.isDirectory())) {
			System.out.println("directory configuration doesn't exist. creating");
			try {
				Files.createDirectories(Paths.get(strCfgPath));
				System.out.println("directory created");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("couldn't create directory");
				return false;
			}
		}else {
			System.out.println("Directory exists");
		}
		checkExists = new File(Paths.get(strCfgPath,strUserListPath).toString());
		if (!checkExists.exists()) {
			try {
				System.out.println("configuration file doesn't exist. creating");
				Files.createFile(Paths.get(strCfgPath,strUserListPath));
				System.out.println("file created");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("couldn't create ini file");
				return false;
			}
		}
		return true;
	}
	public static boolean blankIni(String strPath, String strFilename) {
		String strDirPath = strPath;
		String strFileName = strFilename;
		String strFilePath = Paths.get(strDirPath, strFileName).toString();
		try {
			System.out.println("Writing empty stuff to ini if empty");
			Ini ini = new Ini(new File( strFilePath));
			ini.put("Database", "url","");
			ini.put("Database", "username","");
			ini.put("Database", "password","");
			ini.put("Database", "driverClassName","");
			ini.put("Database", "dialect","");
			ini.put("Database", "show-sql","");
			ini.put("Database", "ddl-auto","");
			ini.put("Server", "port","");
			ini.put("Server", "logLevel","");
			ini.put("Server", "showSQLQueries","");
			ini.store();
			return true;
		} catch (NullPointerException e1) {
			// TODO Auto-generated catch block
			System.out.println("file at '"+strFilePath+"' is empty. add values");
			return false;
		} catch(InvalidFileFormatException e1) {
			System.out.println("invalid file format");
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("file at '"+strFilePath+"' not found");
			return false;
		}
	}
	public void exitApp() {
		System.out.println("closing app");
		System.exit(0);
	}
	public Properties props() {
	    Properties properties = new Properties();
		try {
			Ini ini = new Ini(new File("configuration/vsystem.ini"));
			properties.setProperty("spring.datasource.url",ini.get("Database", "url"));
			properties.setProperty("spring.datasource.username",ini.get("Database","username"));
			properties.setProperty("spring.datasource.password",ini.get("Database","password"));
			properties.setProperty("spring.datasource.driverClassName",ini.get("Database","driverClassName"));
			properties.setProperty("spring.jpa.hibernate.dialect",ini.get("Database","dialect"));
			properties.setProperty("spring.jpa.show-sql",ini.get("Database","show-sql"));
			properties.setProperty("spring.jpa.hibernate.ddl-auto",ini.get("Database","ddl-auto"));
			properties.setProperty("server.port",ini.get("Server","port"));
			properties.setProperty("logging.level.root",ini.get("Server","logLevel"));
			properties.setProperty("logging.level.org.springframework.web",ini.get("Server","logLevel"));
			properties.setProperty("logging.level.org.hibernate",ini.get("Server","logLevel"));
			properties.setProperty("spring.jpa.show-sql",ini.get("Server","showSQLQueries"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("configuration/vsystem.ini not found or incomplete.");
			String strPath = Paths.get(System.getProperty("user.dir"),"configuration").toString();
			String strFileName = "vsystem.ini";
			createFile(strPath,strFileName);
			blankIni(strPath,strFileName);
			exitApp();
			
			
		}
	    return properties;
	  }
	
	public boolean addUserFile(String username, String password, String strCfgPath, String strFileName) {
		String strFilePath = Paths.get(strCfgPath, strFileName).toString(); 			
		try {
			if(!existsUserFile(username, password, strCfgPath, strFileName))
			{
				FileWriter fw = new FileWriter(strFilePath, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    bw.write(username+";"+BCrypt.hashpw(password,BCrypt.gensalt(12)));
			    bw.newLine();
			    bw.close();
			    return true;
			}
		}catch(IOException E) {

			System.out.println("error adding.");
		}
		return false;
	}
	public boolean existsUserFile(String username, String password, String strCfgPath, String strFileName) {
		String strFilePath = Paths.get(strCfgPath, strFileName).toString(); 
		try {
			FileReader FR = new FileReader(strFilePath);
			BufferedReader BR = new BufferedReader(FR);
			String line;
			String[] userList;
			while((line = BR.readLine()) != null) {
				userList = line.split(";");
				if (userList[0].trim().equals(username))
				{
					System.out.println("user exists");
					return true;
				}
			}
		return false;
		}catch(IOException E)
		{
			return false;
		}
	}
	public static List<String[]> getUserList(String strCfgPath, String strFileName)
	{	
		List<String[]> stllUserList = new ArrayList<String[]>();
		String strFilePath = Paths.get(strCfgPath, strFileName).toString(); 
		try {
			FileReader FR = new FileReader(strFilePath);
			BufferedReader BR = new BufferedReader(FR);
			String line;
			String[] strUserList;
			int i = 0;
			while((line = BR.readLine()) != null) {
				strUserList = line.split(";");
				stllUserList.add(strUserList);
			}

			return stllUserList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void log(LocalDateTime datetime, int severity, String message)
	{
		DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		System.out.println(datetime.format(datetimeFormat)+";"+severity+";"+message);
	}
}
