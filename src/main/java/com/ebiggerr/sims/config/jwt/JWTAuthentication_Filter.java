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

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
// @PropertySource(value = {"classpath:application-dev.properties"})
public class JWTAuthentication_Filter extends OncePerRequestFilter {

    @Autowired
    private final Token_Provider tokenProvider=new Token_Provider();

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = null;
        String authToken = null;
        DecodedJWT decodedJWT = null;

        //extract request header
        String header = request.getHeader("Authorization");

        if ( header != null && header.startsWith("Bearer")){
            authToken = header.replace("Bearer","").trim(); //extract the token String by eliminate the "Bearer"

            try{
                Token_Provider provider = new Token_Provider();
                decodedJWT = provider.verifyAndDecodeToken(authToken);
                username = decodedJWT.getClaim("username").asString();

            }catch ( JWTVerificationException jwtVerificationException){
                logger.error("Invalid JWT: " + jwtVerificationException.getMessage() );
            }
        }

        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {

            if (SecurityContextHolder.getContext().getAuthentication() == null && decodedJWT != null) {

                //extract username and authorities from the decoded JWT
                UsernamePasswordAuthenticationToken authenticationToken =
                        tokenProvider.getAuthenticationToken(decodedJWT, SecurityContextHolder.getContext().getAuthentication(), username );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //update
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request,response);

    }
}
