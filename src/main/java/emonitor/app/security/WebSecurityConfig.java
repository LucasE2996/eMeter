package emonitor.app.security;

import emonitor.app.services.ClientService;
import emonitor.app.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String TOKEN_STRING = "my_token";
    private final static String COOKIE_STRING = "my_cookie";

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final ClientService userSvc;
    private final MyTokenBasedRememberMeService tokenSvc;
    private final RememberMeAuthenticationProvider rememberMeProvider;

    public WebSecurityConfig(
            RestAuthenticationEntryPoint restAuthenticationEntryPoint,
            ClientService userSvc,
            MyTokenBasedRememberMeService tokenSvc,
            RememberMeAuthenticationProvider rememberMeProvider) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.userSvc = userSvc;
        this.tokenSvc = tokenSvc;
        this.rememberMeProvider = rememberMeProvider;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSvc)
            .passwordEncoder(passwordEncoder());
        auth.authenticationProvider(rememberMeProvider);
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
//                    .antMatchers("/user/**").hasAnyAuthority("USER")
//                    .antMatchers("/meter/**").hasAnyAuthority("USER")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/loginprocess")
                    .successHandler(mySuccessHandler())
                    .failureHandler(myFailureHandler())
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(myLogoutSuccessHandler())
                    .deleteCookies(COOKIE_STRING).and()
                .rememberMe()
                    .rememberMeServices(tokenSvc).and()
                .addFilterBefore(rememberMeAuthenticationFilter(), BasicAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider
//                = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userSvc);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public SimpleUrlLogoutSuccessHandler myLogoutSuccessHandler() {
        return new SimpleUrlLogoutSuccessHandler();
    }

    @Bean
    public MyTokenBasedRememberMeService tokenBasedRememberMeService() {
        MyTokenBasedRememberMeService service = new MyTokenBasedRememberMeService(TOKEN_STRING,
                userSvc);
        service.setAlwaysRemember(true);
        service.setCookieName(COOKIE_STRING);
        return service;
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(TOKEN_STRING);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
        return new RememberMeAuthenticationFilter(authenticationManager(), tokenBasedRememberMeService());
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler mySuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler();
    }
}
