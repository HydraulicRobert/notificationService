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
		String strCfgName = global.getGstrcfgname();
		startArgs strtArgs = new startArgs();
       // List<String[]> stlArgsList = strtArgs.getStlArgsList();
		cfgInputOutput.createFile(strCfgPath, strFileName);
		if (args.length <= 0) {
			try {
				new SpringApplicationBuilder(NotificationsApplication.class)
		        .properties(cfgInputOutput.props(
		        		global.getGstrcfgpath(), 
						global.getGstrcfgname()
						))
		        .web(WebApplicationType.SERVLET)
		        .build()
		        .run(args);
			}catch(Exception e)
			{
				int i = 0;
				System.out.println("change config. startargument -c");
				while(i == 0) {
					
				}
				//cfgInputOutput.blankIni(strCfgPath, strCfgName);
				//cfgInputOutput.fillIni(global.getGstrcfgpath(), global.getGstrcfgname());
			}
		}else {
			strtArgs.pickStartArgs(args);
		}
		
		//add
		
			
		
	}
	

}
