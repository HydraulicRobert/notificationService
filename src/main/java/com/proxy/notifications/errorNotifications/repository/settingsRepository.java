package com.proxy.notifications.errorNotifications.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.proxy.notifications.errorNotifications.entity.settings;

@Repository
@Component
public interface settingsRepository extends CrudRepository<settings, Long>{
	@Query(value = "SELECT * from settings where last_Change_By like 'kevin'",
			nativeQuery = true)
	settings findTop1By();
	settings findTop1ByOrderByLastChangeOnDesc();
}
