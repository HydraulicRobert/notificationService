package com.proxy.notifications.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class cfgInputOutput {
	
	public static boolean createFile(String strPath, String strFilename) {
		String strCfgPath = strPath.toString();
		String strUserListPath = strFilename;
		File checkExists = new File(strCfgPath);
		if (!(checkExists.exists() && checkExists.isDirectory())) {
			System.out.println(global.getGstrcfgdirnotfound());
			try {
				Files.createDirectories(Paths.get(strCfgPath));
				System.out.println(global.getGstrcfgdircreated());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(global.getGstrcfgdirnotcreated());
				return false;
			}
		}else {
			System.out.println(global.getGstrcfgdirexists());
		}
		checkExists = new File(Paths.get(strCfgPath,strUserListPath).toString());
		if (!checkExists.exists()) {
			try {
				System.out.println(global.getGstrcfgcfgnotexists());
				Files.createFile(Paths.get(strCfgPath,strUserListPath));
				System.out.println(global.getGstrcfgcfgcreated());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(global.getGstrcfgcfgcouldnotcreate());
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
			System.out.println(global.getGstrcfgcfgwritetoempty());
			Ini ini = new Ini(new File( strFilePath));
			ini.put("Database", "url","");
			ini.put("Database", "username","");
			ini.put("Database", "password","");
			ini.put("Database", "driverClassName","");
			ini.put("Database", "dialect","");
			ini.put("Database", "show-sql","");
			ini.put("Database", "ddl-auto","");
			ini.put("Server", "port","");
			ini.put("Server", "logLevelRoot","");
			ini.put("Server", "logLevelSpring","");
			ini.put("Server", "logLevelHibernate","");
			ini.put("Server", "showSQLQueries","");
			ini.store();
			return true;
		} catch (NullPointerException e1) {
			// TODO Auto-generated catch block
			System.out.println(global.getGstrcfgcfgemptyaddvalues()
											.replace(
													"$s1", 
													strFilePath
													)
											);
			return false;
		} catch(InvalidFileFormatException e1) {
			System.out.println(global.getGstrcfgcfginvalidformat());
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(global.getGstrcfgcfgnotfound()
								.replace(
										"$s1", 
										Paths.get(
												global.getGstrcfgpath(),
												global.getGstrcfgname()
												).toString()
										)
					);
			return false;
		}
	}
	
	public static void fillIni(String strPath, String strFilename) {
		List<String[]> stlArgsList = new ArrayList<String[]>();
		
		Ini ini;
		try {
			String strDirPath = strPath;
			String strFileName = strFilename;
			String strFilePath = Paths.get(strDirPath, strFileName).toString();
			Scanner scnUserInput = new Scanner(System.in);
			String strUserInput = "";
			ini = new Ini(new File( strFilePath));
			stlArgsList.add(new String[] {"Database",	"url",				"sql ip address",								"jdbc:sqlserver://192.168.0.x:8080;databaseName=myDatabase;trustServerCertificate=true",ini.get("Database","url")});
			stlArgsList.add(new String[] {"Database",	"username",			"username to log into the database server",		"ADMIN",ini.get("Database","username")});
			stlArgsList.add(new String[] {"Database",	"password",			"password to log into the database server",		"***",ini.get("Database","password")});
			stlArgsList.add(new String[] {"Database",	"driverClassName",	"driver to be used",							"com.microsoft.sqlserver.jdbc.SQLServerDriver",ini.get("Database","driverClassName")});
			stlArgsList.add(new String[] {"Database",	"dialect",			"component for converting database objects into hibernate objects", "org.hibernate.dialect.SQLServer2012Dialect",ini.get("Database","dialect")});
			stlArgsList.add(new String[] {"Database",	"show-sql",			"show sql queries in console",					"\n'true' = show; \n'false' = dont show;",ini.get("Database","show-sql")});
			stlArgsList.add(new String[] {"Database",	"ddl-auto",			"how hibernate manages the tables etc.",		"'none' = no database changes; \n'validate' = check if database shema matches hibernate. if not, change nothing; \n'update' = automatically change database shema to hibernate; \n'create' = drop previous and create new on app start; \n'create-drop' = same as create, but drops at shutdown",ini.get("Database","ddl-auto")});
			stlArgsList.add(new String[] {"Server",		"port",				"port for server to be accessed from",			"80",ini.get("Server","port")});
			stlArgsList.add(new String[] {"Server",		"logLevelRoot",		"global logging level",							"'OFF' = none; \n'FATAL' = only fatal; \n'ERROR' = only error; \n'WARN' = only warnings; \n'INFO' = only information messages; \n'DEBUG' = detailled debug messages; \n'TRACE' = detailled general informations; \n'ALL' = everything",ini.get("Server","logLevelRoot")});
			stlArgsList.add(new String[] {"Server",		"logLevelSpring",	"spring web related logging level",				"'OFF' = none; \n'FATAL' = only fatal; \n'ERROR' = only error; \n'WARN' = only warnings; \n'INFO' = only information messages; \n'DEBUG' = detailled debug messages; \n'TRACE' = detailled general informations; \n'ALL' = everything",ini.get("Server","logLevelSpring")});
			stlArgsList.add(new String[] {"Server",		"logLevelHibernate","Hibernate related logging level",				"'OFF' = none; \n'FATAL' = only fatal; \n'ERROR' = only error; \n'WARN' = only warnings; \n'INFO' = only information messages; \n'DEBUG' = detailled debug messages; \n'TRACE' = detailled general informations; \n'ALL' = everything",ini.get("Server","logLevelHibernate")});			
			for(int i = 0; i<stlArgsList.size();i++)
			{
				String[] stlLine = stlArgsList.get(i);
				System.out.println("category: "+stlLine[0]);
				System.out.println("\nobject: "+stlLine[1]);
				System.out.println("\ndescription: \n"+stlLine[2]);
				System.out.println("\nexample: "+stlLine[3]);
				System.out.println("\ncurrently: "+stlLine[4]);
				System.out.println("\n\nkeep empty to keep value. else type something in");
				strUserInput = "";
				System.out.print("input: ");
				strUserInput = scnUserInput.nextLine();
				var res = strUserInput.isEmpty()?
						ini.put(stlLine[0], stlLine[1],stlLine[4]):
						ini.put(stlLine[0], stlLine[1],strUserInput);
			}
			ini.put("Server", "showSQLQueries",Boolean.valueOf(stlArgsList.get(10)[4]));
			ini.store();
			scnUserInput.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	public static void exitApp() {
		System.out.println("closing app");
		System.exit(0);
	}
	public static Properties props() {
	    Properties properties = new Properties();
		try {
			Ini ini = new Ini(
						new File(
							Paths.get(
									global.getGstrcfgpath(), 
									global.getGstrcfgname()
							).toString()
						)
			);
			properties.setProperty("spring.datasource.url",ini.get("Database", "url"));
			properties.setProperty("spring.datasource.username",ini.get("Database","username"));
			properties.setProperty("spring.datasource.password",ini.get("Database","password"));
			properties.setProperty("spring.datasource.driverClassName",ini.get("Database","driverClassName"));
			properties.setProperty("spring.jpa.hibernate.dialect",ini.get("Database","dialect"));
			properties.setProperty("spring.jpa.show-sql",ini.get("Database","show-sql"));
			properties.setProperty("spring.jpa.hibernate.ddl-auto",ini.get("Database","ddl-auto"));
			properties.setProperty("server.port",ini.get("Server","port"));
			properties.setProperty("logging.level.root",ini.get("Server","logLevelRoot"));
			properties.setProperty("logging.level.org.springframework.web",ini.get("Server","logLevelSpring"));
			properties.setProperty("logging.level.org.hibernate",ini.get("Server","logLevelHibernate"));
			properties.setProperty("spring.jpa.show-sql",ini.get("Server","showSQLQueries"));
			properties.setProperty("spring.datasource.hikari.connectionTimeout","30000");
			properties.setProperty("spring.datasource.hikari.idleTimeout","600000");
			properties.setProperty("spring.datasource.hikari.maxLifetime","1800000");
			
			//podman
			properties.setProperty("server.address","0.0.0.0");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(global.getGstrcfgcfgnotfound()
											.replace(
													"$s1", 
													Paths.get(
															global.getGstrcfgpath(),
															global.getGstrcfgname()
															).toString()
													)
											);
			String strPath = global.getGstrcfgpath();
			String strFileName = global.getGstrcfgname();
			createFile(strPath,strFileName);
			blankIni(strPath,strFileName);
			exitApp();
			
			
		}
	    return properties;
	  }
	
	public static boolean addUserFile(String username, String password, String strCfgPath, String strFileName) {
		String strFilePath = Paths.get(strCfgPath, strFileName).toString(); 			
		try {
			if(!existsUserFile(username, password, strCfgPath, strFileName))
			{
				FileWriter fw = new FileWriter(strFilePath, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    bw.write(username+";"+BCrypt.hashpw(password,BCrypt.gensalt(12)));
			    bw.newLine();
			    bw.close();
			    System.out.println(global.getGstrcfguseradded()
			    							.replace(
			    									"$s1", 
			    									username
			    									)
			    							);
			    return true;
			}else {

			    System.out.println(global.getGstrcfgusernotadded()
						.replace(
								"$s1", 
								username
								)
						);
			    return false;
			}
		}catch(IOException E) {

			System.out.println(global.getGstrcfguseraddederror());
		}
		return false;
	}
	
	public static boolean removeUserFile(String username, String strCfgPath, String strFileName) {
		String filePath = Paths.get(strCfgPath, strFileName).toString();
		File flOrig = new File(filePath);
		File flTmp = new File(filePath+".tmp");
		boolean bolUserFound = false;
		try {
			BufferedReader readOrig = new BufferedReader(new FileReader(flOrig));
			ArrayList<String> lines = new ArrayList<>();
			String line;
			
			while((line = readOrig.readLine()) != null) {
				if (!line.split(";")[0].equals(username)) {
					
					lines.add(line);
				}else {
					bolUserFound = true;
					
				}
			}
			readOrig.close();
			BufferedWriter writeTemp = new BufferedWriter(new FileWriter(flTmp));
			for(String l: lines)
			{
				writeTemp.write(l);
				writeTemp.newLine();
			}
			writeTemp.close();
			if(flOrig.delete())
			{
				flTmp.renameTo(flOrig);
			}else {
				System.out.println(global.getGstrcfgerror());
			}
			if(bolUserFound)
			{
				System.out.println(global.getGstrcfguserdeleted()
						.replace(
								"$s1", 
								username
								)
						);
			}else
			{
				System.out.println(global.getGstrcfgusernotdeleted()
						.replace(
								"$s1", 
								username
								)
						);
			}
			/*for (String strForLoop : lines) {
				System.out.println(
						strForLoop+"; "
				);
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bolUserFound;
	}
	
	public static boolean existsUserFile(String username, String password, String strCfgPath, String strFileName) {
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
					System.out.println(global.getGstrcfguserexists()
							.replace(
									"$s1", 
									username
									)
							);
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
	/*public static void log(LocalDateTime datetime, int severity, String message)
	{
		DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		System.out.println(datetime.format(datetimeFormat)+";"+severity+";"+message);
	}*/
	public static void logRequests(
			HttpServletRequest request,
			Authentication authentication
	) throws ServletException, IOException {
		String strMMethod = request.getMethod();
		String strMUri = request.getRequestURI();
		String strMQuery = request.getQueryString();
		String strMName = !authentication.getName().isEmpty() ? authentication.getName():global.getGstrcfgauthnameunknown();
		String strMIp = request.getRemoteAddr();
		String strMAuthenticated = authentication.isAuthenticated()?global.getGstrcfgauthenticated():global.getGstrcfgnotauthenticated();
		DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		System.out.println(LocalDateTime.now().format(datetimeFormat)+";"+strMIp+";"+strMMethod+";"+strMUri+";"+strMName+";"+strMAuthenticated);
	}
}
