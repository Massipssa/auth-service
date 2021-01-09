package com.anonymizer.auth.services.jpa;

import java.util.List;
import java.util.Optional;
import com.anonymizer.auth.models.User;

public interface UserService {
	User createUser(User user);
	List<User> getAllUsers();
	Optional<User> getUserByName(String userName);
	User updateUser(User user, int id);
	void deleteUser(String userName);
}
