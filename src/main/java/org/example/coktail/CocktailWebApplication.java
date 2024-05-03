package org.example.coktail;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.web.*;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class CocktailWebApplication {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((requests) -> requests
                        .mvcMatchers("/user/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin().and()
                .csrf().disable()
                .build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},cn=users,cn=accounts,dc=demo1,dc=freeipa,dc=org")
                .contextSource()
                .url("ldap://ipa.demo1.freeipa.org:389")
                .managerDn("uid=admin,cn=users,cn=accounts,dc=demo1,dc=freeipa,dc=org")
                .managerPassword("Secret123");
    }

    public static void main(String[] args) {
        SpringApplication.run(CocktailWebApplication.class, args);
    }

}