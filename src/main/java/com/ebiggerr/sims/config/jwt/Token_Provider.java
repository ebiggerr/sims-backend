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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ebiggerr.sims.domain.accountAuthentication_UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

//@Component
@Service
//@PropertySource(value = {"classpath:application.properties"})
public class Token_Provider extends JWT {

    //@Value("${secret.key}")
    private final String privateKey = "jXn2r5u8x!A%D*G-KaPdSgVkYp3s6v9y";
    private final Algorithm algorithm = Algorithm.HMAC256(privateKey);
    
    /**
     *
     * @param acc User Details object that contains the Username, Password and Roles/Authorities
     * @return [String] JSON Web Token
     *
     * Claims: Issuer, IssuedAt, ExpiredAt, Username, Roles
     *
     */
    public String generateToken(accountAuthentication_UserDetails acc){

        final String authorities = acc.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Instant nowEp = Instant.now();
        Date now = Date.from(nowEp);
        // 30 minutes Expiration Duration
        Date exp = Date.from(nowEp.plus(30, ChronoUnit.MINUTES));

        try{
            String token = JWT.create()
                    .withClaim("username", acc.getUsername())
                    .withClaim("roles",authorities)
                    .withIssuedAt(now)
                    .withExpiresAt(exp)
                    .sign(algorithm);
            return token;

        }catch (JWTCreationException exception){

        }
        return null;
    }

    /**
     *
     * @param authentication Authentication object that contains the Principals, Credentials and Roles/Authorities
     * @return [String] JSON Web Token
     *
     * Claims: Issuer, IssuedAt, ExpiredAt, Username, Roles
     *
     */
    public String generateTokenAuthentication(Authentication authentication){

        //algorithm = Algorithm.HMAC256(privateKey);

        final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Instant nowEp = Instant.now();
        Date now = Date.from(nowEp);
        // 30 minutes Expiration Duration
        Date exp = Date.from(nowEp.plus(30, ChronoUnit.MINUTES));

        // extract username from Authentication object
        Object principal = authentication.getPrincipal();
        String username = null;
        if (principal instanceof accountAuthentication_UserDetails) {
             username = ((accountAuthentication_UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        try{
            String token = JWT.create()
                    .withClaim("username", username)
                    .withClaim("roles",authorities)
                    .withIssuedAt(now)
                    .withExpiresAt(exp)
                    .withIssuer("auth0")
                    .sign(algorithm);
            return token;

        }catch (JWTCreationException exception){

        }
        return null;
    }

    /**
     *
     * @param token JSON Web Token
     * @return DecodedJWT from the JSON Web Token
     */
    public DecodedJWT verifyAndDecodeToken(String token){

        // TODO fix to get from application.properties
        //algorithm = Algorithm.HMAC256(privateKey);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();

        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT;
    }

    /**
     *
     * @param decodedJWT decodedJWT from the Request
     * @param existingAuth always be null
     * @param username username extract from the JWT
     * @return UsernamePasswordAuthenticationToken to be used by SecurityContextHolder.getContext.setAuthentication(), @PreAuthorize on controllers
     */
    UsernamePasswordAuthenticationToken getAuthenticationToken (DecodedJWT decodedJWT, final Authentication existingAuth, String username){

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(decodedJWT.getClaim("roles").asString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username,"",authorities);
    }

    /**
     *
     * @param token JSON Web Token
     * @return Extracted username from the JWT
     */
    public String getUsernameFromToken(String token){

        token = token.replace("Bearer","").trim();

        //algorithm = Algorithm.HMAC256(privateKey);

        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

        token = decodedJWT.getClaim("username").asString();

        return token; //username from JWT
    }

}
