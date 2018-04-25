package emonitor.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint
            restAuthenticationEntryPoint;
    private final MySavedRequestAwareAuthenticationSuccessHandler
            authenticationSuccessHandler;

    public WebSecurityConfig(RestAuthenticationEntryPoint
                                     restAuthenticationEntryPoint,
                             MySavedRequestAwareAuthenticationSuccessHandler
                                     authenticationSuccessHandler) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/user/**").hasAnyAuthority("USER")
                .antMatchers("/meter/**").hasAnyAuthority("USER")
                .anyRequest().fullyAuthenticated()
                .and().httpBasic()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(myFailureHandler())
                .and()
                .logout();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }
}
