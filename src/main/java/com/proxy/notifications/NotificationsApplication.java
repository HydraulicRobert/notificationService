package com.proxy.notifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

import com.proxy.notifications.configuration.cfgInputOutput;
import com.proxy.notifications.configuration.global;
import com.proxy.notifications.configuration.startArgs;

@SpringBootApplication(scanBasePackages={"com.proxy.notifications"}
		,exclude = {SecurityAutoConfiguration.class}
)
@AutoConfigureBefore(CacheAutoConfiguration.class)
@EnableCaching
public class NotificationsApplication {
	
	public static void main(String[] args) {
		//cfgInputOutput CfgInputOutput = new cfgInputOutput();
		String strCfgPath = global.getGstrcfgpath();
		String strFileName = global.getGstruserlist();
		startArgs strtArgs = new startArgs();
       // List<String[]> stlArgsList = strtArgs.getStlArgsList();
		cfgInputOutput.createFile(strCfgPath, strFileName);
		if (args.length <= 0) {
			new SpringApplicationBuilder(NotificationsApplication.class)
	        .properties(cfgInputOutput.props())
	        .web(WebApplicationType.SERVLET)
	        .build()
	        .run(args);
		}else {
			strtArgs.pickStartArgs(args);
		}
		
		//add
		
			
		
	}
	

}
