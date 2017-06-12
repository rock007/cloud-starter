package org.cloud.db.repository;

import org.cloud.db.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
	
	UserAccount findById(Long id);
	UserAccount findByName(String username);
	
}