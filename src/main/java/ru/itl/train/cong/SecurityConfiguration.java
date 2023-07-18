package ru.itl.train.cong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itl.train.service.UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationContext appContext;

    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        initUserService();
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    private void initUserService() {
        if (userService == null) {
            userService = appContext.getBean(UserService.class);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
            .and()
                //Настройка для входа в систему
                .formLogin()
                .loginProcessingUrl("/auth")
                .loginPage("/login")
                //Перенаправление на главную страницу после успешного входа
                .defaultSuccessUrl("/auth")
                .permitAll()
            .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")
            .and()
                .rememberMe()
                .key("myUniqueKey")
                .rememberMeCookieName("remember-me")
                .tokenValiditySeconds(10000000)
                .userDetailsService(userService);

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout")
                .permitAll()
                .deleteCookies("JSESSIONID");

        //.logoutRequestMatcher(new AntPathRequestMatcher(getLogoutUrl()))  //позволяет делать logout с включенной csrf защитой
    }

}
