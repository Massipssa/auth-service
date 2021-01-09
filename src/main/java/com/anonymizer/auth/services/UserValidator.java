package com.anonymizer.auth.services;

import com.anonymizer.auth.models.User;

import java.util.function.Function;

import static com.anonymizer.auth.services.UserValidator.ValidationResult.*;
import static com.anonymizer.auth.services.UserValidator.*;
import static com.anonymizer.auth.services.UserValidator.ValidationResult.*;

public interface UserValidator extends Function<User, UserValidator.ValidationResult> {

    static UserValidator isValidName() {
        return user -> user.getUserName().length() >= 8   ? SUCCESS : INVALID_USERNAME;
    }

    static UserValidator  isValidPassword() {
        return user -> user.getPassword().length() >= 8  ? SUCCESS : INVALID_PASSWORD;
    }

    default UserValidator and(UserValidator other) {
        return user ->  {
            ValidationResult result = this.apply(user);
            return result.equals(SUCCESS) ? other.apply(user) : result;
        };
    }

    enum ValidationResult {
        SUCCESS,
        INVALID_USERNAME,
        INVALID_PASSWORD,
    }
}
