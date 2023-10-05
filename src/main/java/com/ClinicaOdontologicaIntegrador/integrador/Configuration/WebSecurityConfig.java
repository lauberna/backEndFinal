package com.ClinicaOdontologicaIntegrador.integrador.Configuration;

import com.ClinicaOdontologicaIntegrador.integrador.security.CustomAuthenticationSuccessHandler;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll() // Permitir acceso no autenticado a la consola de H2
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .antMatchers(HttpMethod.GET,"/pacientes").hasAnyRole("USER", "ADMIN")
                .antMatchers("/pacientes/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET,"/odontologos").hasAnyRole("USER", "ADMIN")
                .antMatchers("/odontologos/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET,"/turnos").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/turnos").hasAnyRole("USER", "ADMIN")
                .antMatchers("/turnos/**").hasRole("ADMIN")

                .anyRequest().authenticated().and()
                .formLogin()
                .defaultSuccessUrl("/")
                .successHandler(customAuthenticationSuccessHandler1())
                .and()
                .rememberMe().key("odsajndoasdoasjd")
                .and()
                .exceptionHandling().accessDeniedPage("/403");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.csrf().disable();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler1() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public DaoAuthenticationProvider authProvicer(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
