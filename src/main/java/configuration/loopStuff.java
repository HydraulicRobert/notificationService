package configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.proxy.notifications.errorNotifications.Controller.notificationController;

@Component
public class loopStuff {
	/*@Autowired 
	notificationController nc;
	@Scheduled(fixedRate = 3000, initialDelay = 0)
	public void getNots() {
		nc.loopAble();
	}*/
}
