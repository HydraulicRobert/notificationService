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
        List<String[]> stlArgsList = strtArgs.getStlArgsList();
		cfgInputOutput.createFile(strCfgPath, strFileName);
		if (args.length <= 0) {
			new SpringApplicationBuilder(NotificationsApplication.class)
	        .properties(cfgInputOutput.props())
	        .web(WebApplicationType.SERVLET)
	        .build()
	        .run(args);
		}else
		
		//add
		if (args[0].trim().equals(stlArgsList.get(0)[0]) ||
			args[0].trim().equals(stlArgsList.get(0)[1]) )
		{
			if (!(args.length<Integer.valueOf(stlArgsList.get(0)[3])))
			{
				strtArgs.addUser(args);
			}else
			{
				System.out.println(global.getGstrnotargumentsamount()+stlArgsList.get(0)[3]);
			}
		}else 
		//remove
		if (args[0].trim().equals(stlArgsList.get(1)[0]) ||
				args[0].trim().equals(stlArgsList.get(1)[1]) )
		{
			if (!(args.length<Integer.valueOf(stlArgsList.get(1)[3])))
			{
				strtArgs.removeUser(args);
			}else
			{
				System.out.println(global.getGstrnotargumentsamount()+stlArgsList.get(1)[3]);
			}
		}else 
			//show
			if (args[0].trim().equals(stlArgsList.get(2)[0]) ||
				args[0].trim().equals(stlArgsList.get(2)[1]) )
			{
				if (!(args.length<Integer.valueOf(stlArgsList.get(2)[3])))
				{
					strtArgs.showUsers(args);
				}else
				{
					System.out.println(global.getGstrnotargumentsamount()+stlArgsList.get(2)[3]);
				}
			}else
			
		//help
		if (args[0].trim().equals(stlArgsList.get(4)[0]) ||
				args[0].trim().equals(stlArgsList.get(4)[1]) )
		{
			if (!(args.length<Integer.valueOf(stlArgsList.get(4)[3])))
			{
				strtArgs.help(args, stlArgsList);
			}else
			{
				System.out.println(global.getGstrnotargumentsamount()+stlArgsList.get(4)[3]);
			}
		}
			
		
	}
	

}
