package com.anonymizer.auth.service.jpa;

import java.util.List;
import java.util.Optional;
import com.anonymizer.auth.model.User;

public interface UserService {
	User createUser(User user);
	List<User> getAllUsers();
	Optional<User> getUserByName(final String userName);
	Optional<User> getUserById(final int id);
	User updateUser(User user, final int id);
	void deleteUserById(int id);
}
