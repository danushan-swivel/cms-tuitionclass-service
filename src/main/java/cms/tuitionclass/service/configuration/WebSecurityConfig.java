package cms.tuitionclass.service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
                .authorizeRequests(
                        authorize -> {
                            authorize.antMatchers(HttpMethod.GET, "api/v1/tuition").permitAll()
                                    .anyRequest().authenticated();
                        }

                ).formLogin().and()
                .httpBasic();
    }
}
