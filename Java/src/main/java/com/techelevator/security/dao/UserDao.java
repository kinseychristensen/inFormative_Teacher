package com.techelevator.security.dao;

import com.techelevator.security.model.RegisterUserDto;
import com.techelevator.security.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    boolean updatePassword (RegisterUserDto user, int userId);
    boolean updateUser (RegisterUserDto user, int userId);
    User createUser(RegisterUserDto user);
}
