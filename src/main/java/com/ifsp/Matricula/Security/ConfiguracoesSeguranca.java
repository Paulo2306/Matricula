package com.ifsp.Matricula.Security;

import com.ifsp.Matricula.Service.CookieService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesSeguranca {

        @Bean
        public SecurityFilterChain correnteFiltroSeguranca(
                        HttpSecurity httpSecurity,
                        CookieService cookieService) throws Exception {

                return httpSecurity

                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers(
                                                                "/formlogin",
                                                                "/login",
                                                                "/css/**",
                                                                "/error")
                                                .permitAll()

                                                .requestMatchers(HttpMethod.GET, "/cadastraUsuario").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/Usuarios").permitAll()

                                                .anyRequest().authenticated())

                                .formLogin(form -> form
                                                .loginPage("/formlogin")
                                                .loginProcessingUrl("/login")
                                                .usernameParameter("username")
                                                .passwordParameter("password")

                                                .successHandler((request, response, authentication) -> {

                                                        String username = authentication.getName();

                                                        ResponseCookie cookie = cookieService.criarCookie(username);

                                                        response.addHeader(
                                                                        HttpHeaders.SET_COOKIE,
                                                                        cookie.toString());

                                                        response.sendRedirect("/dashboard");
                                                })

                                                .failureUrl("/formlogin?erro=true")
                                                .permitAll())

                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/formlogin?sair")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())

                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .sessionFixation(fixation -> fixation.migrateSession())
                                                .invalidSessionUrl("/formlogin?expirou")
                                                .maximumSessions(1))

                                .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}