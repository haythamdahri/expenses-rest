package com.engagetech.expenses.config;

import com.engagetech.expenses.filters.RequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Haytham DAHRI
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final RequestFilter requestFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurityConfig(BCryptPasswordEncoder passwordEncoder, UserDetailsService userDetailsService, RequestFilter requestFilter, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.requestFilter = requestFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * <p>configure AuthenticationManager so that it knows from where to load user for matching credentials Use BCryptPasswordEncoder</p>
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception Thrown Exception When Having Unexpected Error
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * <p>Configure Spring Security To Ignore Static Content Server Side</p>
     * <p>Mainly used when rendering HTML from server side or while invoking some static content</p>
     *
     * @param web Web Security Object
     * @throws Exception Thrown Exception When Having Unexpected Error
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/static/**",
                "/uploads/**",
                "/images/**",
                "/webjars/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs"
        );
    }

    /**
     * <p>Configure Endpoints security, especially to ignore such as those of authentication</p>
     *
     * @param http Http Security OBject
     * @throws Exception Thrown Exception When Having Unexpected Error
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // No need CSRF for stateless authentication
        http.csrf().disable()
                .authorizeRequests()
                // Allow POST for authentication
                .antMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                // Allows OPTIONS because it's needed for our project only
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // All remaining requests must be secured with authentication
                .anyRequest().authenticated().and()
                // make sure we use stateless session; session won't be used to store user's state.
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add a filter to validate the tokens with every request
        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
