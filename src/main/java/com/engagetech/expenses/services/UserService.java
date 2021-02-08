package com.engagetech.expenses.services;

import com.engagetech.expenses.entities.User;

import java.util.List;

/**
 * @author Haytham DAHRI
 */
public interface UserService {

    User saveUser(User user);

    void deleteUser(Long id);

    List<User> getUsers();

}
