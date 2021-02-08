package com.engagetech.expenses.services.impl;

import com.engagetech.expenses.dao.UserRepository;
import com.engagetech.expenses.entities.User;
import com.engagetech.expenses.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Haytham DAHRI
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }
}
