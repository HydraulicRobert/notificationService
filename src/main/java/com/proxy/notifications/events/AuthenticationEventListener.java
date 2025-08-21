package com.proxy.notifications.events;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.proxy.notifications.configuration.cfgInputOutput;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

   //private static Logger logger = Logger.getLogger(AuthenticationEventListener.class);
	@Autowired
    HttpServletRequest httpReq;
   @Override
   public void onApplicationEvent(AbstractAuthenticationEvent authenticationEvent) {
	  if (authenticationEvent instanceof InteractiveAuthenticationSuccessEvent) {
		  System.out.println("success");
         return;
      }
      Authentication authentication = authenticationEvent.getAuthentication();
      //WebAuthenticationDetails properties = (WebAuthenticationDetails) authentication.getDetails();
// property = authentication.getPrincipal();
      try 
      {
    	 cfgInputOutput.logRequests(httpReq, authentication);
	      
	      
      }catch (Exception e) {
		// TODO: handle exception
	}
   }

}