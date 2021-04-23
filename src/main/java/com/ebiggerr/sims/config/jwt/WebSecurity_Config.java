/*
 *  Copyright (C) 2020  Alexious Yong (Ebiggerr)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ebiggerr.sims.config.jwt;

import com.ebiggerr.sims.service.account.accountAuthenticationService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurity_Config extends WebSecurityConfigurerAdapter {

    private final UnauthorizedEntryPoint unauthorizedEntryPoint;

    private final accountAuthenticationService accountAuthenticationService;

    /**
     * Constructor-based dependency injection
     * @param accountAuthenticationService
     * @param unauthorizedEntryPoint
     */
    //@Autowired
    public WebSecurity_Config(accountAuthenticationService accountAuthenticationService, UnauthorizedEntryPoint unauthorizedEntryPoint) {
        this.accountAuthenticationService = accountAuthenticationService;
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
    }

    /*@Autowired
    protected void setUnauthorizedEntryPoint(UnauthorizedEntryPoint unauthorizedEntryPoint){
        this.unauthorizedEntryPoint=unauthorizedEntryPoint;
    }

    @Autowired
    protected void setAccountAuthenticationService(accountAuthenticationService accountAuthenticationService){
        this.accountAuthenticationService=accountAuthenticationService;
    }*/

    /**
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountAuthenticationService).passwordEncoder(encoder());
    }

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore( authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Bean
    public JWTAuthentication_Filter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthentication_Filter();
    }

    /*@Bean
    public CORS_Filter corsFilter() throws Exception{
        return new CORS_Filter();
    }*/

}
