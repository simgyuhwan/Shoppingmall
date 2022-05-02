package com.growing.sgh.config;

import com.growing.sgh.config.security.CustomUserDetailService;
import com.growing.sgh.config.security.filter.JwtRequestFilter;
import com.growing.sgh.config.security.handler.JwtAccessDeniedHandler;
import com.growing.sgh.config.security.handler.JwtAuthenticationEntryPoint;
import com.growing.sgh.config.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                formLogin().disable()
                .httpBasic().disable();
        http.cors().configurationSource(corsConfigurationSource());
        http.csrf().disable();

        http
                //.addFilterAt(new JwtAuthenticationFilter(authenticationManager(),jwtTokenProvider),
                //UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/member/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/items/**").access("request.method == GET ? permitALL : hasRole('ADMIN')")
                .antMatchers("/orders/**").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/category/**").access("request.method == GET ? permitALL : hasRole('ADMIN')")
                .anyRequest().authenticated();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        // URL
        configuration.addAllowedOrigin("*");
        // Header
        configuration.addAllowedHeader("*");
        // Http Method
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization","Content-Disposition"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-ui/**","/v3/api-docs/**"
                        ,"/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService())
                .passwordEncoder(passwordEncoder());

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}password")
                .authorities("ADMIN","MEMBER");
    }

    @Bean
    public UserDetailsService userDetailService(){
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
