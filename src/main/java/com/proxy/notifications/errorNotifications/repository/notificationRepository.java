package com.proxy.notifications.errorNotifications.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.proxy.notifications.errorNotifications.entity.notification;

@Repository
@Component
public interface notificationRepository extends CrudRepository<notification, Long> {
	
	//notifications findByID();
	Iterable<notification> findAllByOrderByStartDateDesc();
	notification findTop1ByOrderByStartDateDesc();
}
