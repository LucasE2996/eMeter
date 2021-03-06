package emonitor.app.security;

import emonitor.app.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public WebSecurityConfig(
            RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    /**
     * configure how requests are secured by interceptors
     * @param http the security configure builder
     */

    // configure how requests are secured by interceptors
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // prevent CSRF attack
            .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
            .authorizeRequests()
                .antMatchers("/user/register", "/user/all").permitAll()
                .antMatchers("/user/detail", "/meter/**").hasAuthority("USER")
                .anyRequest().fullyAuthenticated()
                .and()
            .formLogin()
                .loginProcessingUrl("/loginprocess")
                .loginProcessingUrl("/loginprocess")
                .successHandler(mySuccessHandler())
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
            .logout().and()
            .httpBasic();
    }

    /**
     * Get authentication success handler
     * @return MySavedRequestAwareAuthenticationSuccessHandler
     */
    @Bean
    public SimpleUrlAuthenticationSuccessHandler mySuccessHandler() {
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }
}
