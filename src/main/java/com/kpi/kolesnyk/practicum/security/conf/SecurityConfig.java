package com.kpi.kolesnyk.practicum.security.conf;

import com.kpi.kolesnyk.practicum.model.GroupEntity;
import com.kpi.kolesnyk.practicum.model.RoleEntity;
import com.kpi.kolesnyk.practicum.model.UserEntity;
import com.kpi.kolesnyk.practicum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/registration", "/login").not().fullyAuthenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login");
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return x -> List.of(UserEntity.builder()
                                .username("roma")
                                .password(passwordEncoder().encode("123"))
                                .role(RoleEntity.builder().id(3L).authority("ROLE_ADMIN").build())
                                .build(),
                        UserEntity.builder()
                                .username("bukasov")
                                .password(passwordEncoder().encode("123"))
                                .role(RoleEntity.builder().id(2L).authority("ROLE_TEACHER").build())
                                .build(),
                        UserEntity.builder()
                                .username("dima")
                                .password(passwordEncoder().encode("123"))
                                .role(RoleEntity.builder().id(1L).authority("ROLE_STUDENT").build())
                                .group(GroupEntity.builder().id(1L).description("IT-01").build())
                                .build(),
                        UserEntity.builder()
                                .username("admin")
                                .password(passwordEncoder().encode("123"))
                                .role(RoleEntity.builder().id(3L).authority("ROLE_ADMIN").build())
                                .build())
                .forEach(userService::saveUser);
    }
}
