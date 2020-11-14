package pl.sda.spring.customers.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String CUSTOMERS_READ = "CUSTOMERS_READ";
    public static final String CUSTOMERS_WRITE = "CUSTOMERS_WRITE";
    public static final String ADMIN_ROLE = "ADMIN";

    private final DataSource dataSource;

    SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/admin").hasRole(ADMIN_ROLE)
            .antMatchers("/api/v1/customers/**").authenticated()
        .and()
            .csrf().disable()
            .httpBasic()
        .and()
            .headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource);
//            .withUser("user1")
//            .password("{bcrypt}$2a$10$2lMRkC9fIbWfUuMFBhumIuyc1D1N89dqZ3T3GbA6/GA3DEZHcNsfq")
//            .roles(CUSTOMERS_READ)
//        .and()
//            .withUser("user2")
//            .password("{bcrypt}$2a$10$2lMRkC9fIbWfUuMFBhumIuyc1D1N89dqZ3T3GbA6/GA3DEZHcNsfq")
//            .roles(CUSTOMERS_READ, CUSTOMERS_WRITE);
    }
}
