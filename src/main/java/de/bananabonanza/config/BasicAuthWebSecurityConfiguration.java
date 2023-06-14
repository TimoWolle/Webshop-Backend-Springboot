package de.bananabonanza.config;

import de.bananabonanza.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class BasicAuthWebSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeHttpRequests((auth)->auth
                    .requestMatchers("/index.html").permitAll()
                    .requestMatchers("/error").permitAll()
                    .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling()
                    .accessDeniedPage("/error");

//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/")
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID");

        http.cors();
        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails member = User
                .withUsername("member")
                .password(passwordEncoder().encode("member"))
                .authorities(Role.MEMBER.getGrantedPermissions())
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities(Role.ADMIN.getGrantedPermissions())
                .build();

        UserDetails analyst = User
                .withUsername("analyst")
                .password(passwordEncoder().encode("analyst"))
                .authorities(Role.ANALYST.getGrantedPermissions())
                .build();

        return new InMemoryUserDetailsManager(member, admin, analyst);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}