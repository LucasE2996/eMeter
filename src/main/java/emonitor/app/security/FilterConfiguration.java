package emonitor.app.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filterProxy());
        registration.addUrlPatterns("/*");
        registration.setName("springSecurityFilterChain");
        return registration;
    }

    public DelegatingFilterProxy filterProxy() {
        return new DelegatingFilterProxy();
    }
}
