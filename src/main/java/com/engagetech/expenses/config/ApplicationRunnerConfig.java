package com.engagetech.expenses.config;

import com.engagetech.expenses.dao.ExpenseRepository;
import com.engagetech.expenses.dao.RoleRepository;
import com.engagetech.expenses.dao.UserRepository;
import com.engagetech.expenses.entities.Expense;
import com.engagetech.expenses.entities.Role;
import com.engagetech.expenses.entities.RoleType;
import com.engagetech.expenses.entities.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Haytham DAHRI
 */
@Configuration
@Log4j2
public class ApplicationRunnerConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ExpenseRepository expenseRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ApplicationRunnerConfig(UserRepository userRepository, RoleRepository roleRepository, ExpenseRepository expenseRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.expenseRepository = expenseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if( this.userRepository.count() == 0 ) {
            // Create Initial Roles
            Role userRole = this.roleRepository.save(new Role(RoleType.USER, null));
            Role adminRole = this.roleRepository.save(new Role(RoleType.ADMIN, null));
            // Create Initial User
            User user = this.userRepository.save(new User("haytham", this.passwordEncoder.encode("toortoor"), null,
                    Set.of(userRole, adminRole)));
            // Create Initial Expense
            Expense expense = this.expenseRepository.save(new Expense(new Date(), BigDecimal.valueOf(2500L), "Buy new car",
                    user));
            log.info("System Initialization Done.");
        }
    }
}
