package com.proxy.notifications;

import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableJpaRepositories(basePackages = 
//{"com.proxy.notifications"})
@SpringBootApplication(scanBasePackages={"com.proxy.notifications"},
		exclude = {SecurityAutoConfiguration.class})
@EnableCaching
public class NotificationsApplication {
	
	public static void main(String[] args) {	
		
		//ConfigurableApplicationContext appContext = SpringApplication.run(NotificationsApplication.class, args);
		
		//notificationRepository notRep = appContext.getBean(notificationRepository.class);
		new SpringApplicationBuilder(NotificationsApplication.class)
        .properties(props())
        .build()
        .run(args);
	}
	private static Properties props() {
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
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("configuration/vsystem.ini not found or incomplete.");
			File checkExists = new File(Paths.get(System.getProperty("user.dir"),"configuration").toString());
			if (!(checkExists.exists() && checkExists.isDirectory())) {
				System.out.println("directory configuration doesn't exist. creating");
				try {
					Files.createDirectories(Paths.get(System.getProperty("user.dir"),"configuration"));
					System.out.println("directory created");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("couldn't create directory");
				}
			}else {
				System.out.println("Directory exists");
			}
			checkExists = new File(Paths.get(System.getProperty("user.dir"),"configuration","vsystem.ini").toString());
			if (!checkExists.exists()) {
				try {
					System.out.println("configuration file doesn't exist. creating");
					Files.createFile(Paths.get(System.getProperty("user.dir"),"configuration","vsystem.ini"));
					System.out.println("file created");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("couldn't create ini file");
				}
			}
			else{
				System.out.println("file exists");
			}
			Ini ini;
			String strIniPath = Paths.get(System.getProperty("user.dir"),"configuration","vsystem.ini").toString();
			try {
				System.out.println("Writing empty stuff to ini if empty");
				ini = new Ini(new File(strIniPath));
				ini.put("Database", "url","");
				ini.put("Database", "username","");
				ini.put("Database", "password","");
				ini.put("Database", "driverClassName","");
				ini.put("Database", "dialect","");
				ini.put("Database", "show-sql","");
				ini.put("Database", "ddl-auto","");
				ini.store();
			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				System.out.println("file at '"+strIniPath+"' is empty. add values");
			} catch(InvalidFileFormatException e1) {
				System.out.println("invalid file format");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("file at '"+strIniPath+"' not found");
				
			}
			System.out.println("closing application");
			System.exit(0);
		}
	    //properties.setProperty("spring.datasource.url", "jdbc:sqlserver://localhost:51812;databaseName=meldungstabelle;trustServerCertificate=true");
	    /*properties.setProperty("spring.datasource.username", "sa");
	    properties.setProperty("spring.datasource.password", "sa2007");
	    properties.setProperty("spring.datasource.driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    properties.setProperty("spring.jpa.hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
	    properties.setProperty("spring.jpa.show-sql", "true");
	    properties.setProperty("spring.jpa.hibernate.ddl-auto", "none");*/
	    return properties;
	  }

}
