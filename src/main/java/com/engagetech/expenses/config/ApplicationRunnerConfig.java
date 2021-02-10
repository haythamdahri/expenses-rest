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
    private final BCryptPasswordEncoder passwordEncoder;

    public ApplicationRunnerConfig(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if( this.userRepository.count() == 0 ) {
            log.info("INITIALIZING SYSTEM DATA");
            // Create Initial Roles
            Role userRole = this.roleRepository.save(new Role(RoleType.USER, null));
            Role adminRole = this.roleRepository.save(new Role(RoleType.ADMIN, null));
            // Create Initial User
            this.userRepository.save(new User("haytham", this.passwordEncoder.encode("haytham123"), null,
                    Set.of(userRole, adminRole)));
            this.userRepository.save(new User("ramzi", this.passwordEncoder.encode("ramzi123"), null,
                    Set.of(userRole, adminRole)));
            this.userRepository.save(new User("vedrana", this.passwordEncoder.encode("vedrana123"), null,
                    Set.of(userRole, adminRole)));
           this.userRepository.save(new User("jeanmichel", this.passwordEncoder.encode("jeanmichel123"), null,
                    Set.of(userRole, adminRole)));
            log.info("System Initialization Done.");
        }
    }
}
