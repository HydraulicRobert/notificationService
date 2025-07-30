package com.proxy.notifications;

import java.util.Arrays;
import java.util.Properties;
import java.io.File;
import java.nio.file.Paths;

import org.ini4j.Ini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import com.proxy.notifications.configuration.cfgInputOutput;
import com.proxy.notifications.configuration.websecurityConfig;

import org.apache.commons.logging.LogFactory;

//@EnableJpaRepositories(basePackages = 
//{"com.proxy.notifications"})
@SpringBootApplication(scanBasePackages={"com.proxy.notifications"}
		,exclude = {SecurityAutoConfiguration.class}
)
@AutoConfigureBefore(CacheAutoConfiguration.class)
@EnableCaching
public class NotificationsApplication {
	
	public static void main(String[] args) {
		cfgInputOutput CfgInputOutput = new cfgInputOutput();
		String strCfgPath = Paths.get(System.getProperty("user.dir"),"configuration").toString();
		String strFileName = "userlist.csv";
		if (args.length <= 0) {
			new SpringApplicationBuilder(NotificationsApplication.class)
	        .properties(CfgInputOutput.props())
	        .web(WebApplicationType.SERVLET)
	        .build()
	        .run(args);
		}else
		if (args[0].trim().equals("--add") ||
			args[0].trim().equals("-a") )
		{
			if (!CfgInputOutput.createFile(strCfgPath, strFileName))
			{
				System.out.println("userlist not created");
			}else {
				System.out.println("userlist created");
			}
			CfgInputOutput.addUserFile(args[1],args[2],strCfgPath,strFileName);
		}else 
		if (args[0].trim().equals("--get")||
			args[0].trim().equals("-g") ){
			cfgInputOutput.getUserList(strCfgPath, strFileName);
		}
			
		
	}
	

}
