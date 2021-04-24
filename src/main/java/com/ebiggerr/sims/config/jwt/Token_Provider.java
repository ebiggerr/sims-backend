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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@PropertySource(value = {"classpath:application-dev.properties"})
public class Token_Provider extends JWT {

    @Value("${secretsigningKey}")
    private String privateKey;

    Algorithm algorithm = Algorithm.HMAC256(privateKey);

    public String generateToken(Authentication authentication){

        final String authorities= authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        //TODO
        Instant nowEp = Instant.now();

        Date now = Date.from(nowEp);

        // 30 minutes Expiration Duration
        Date exp = Date.from(nowEp.plus(30, ChronoUnit.MINUTES));


        try{
            String token = JWT.create().withIssuedAt(now)
                    .withSubject(authentication.getName())
                    .withClaim("AUTHORITIES_KEY",authorities)
                    .withExpiresAt(exp)
                    .sign(algorithm);

            return token;

        }catch (JWTCreationException exception){

        }

        return null;
    }

    public UsernamePasswordAuthenticationToken verifyAndDecodeToken(String token){



        try {

            JWTVerifier verifier = JWT.require(algorithm).build();

            //verify and decode the token
            DecodedJWT decodedJWT = verifier.verify(token);



        }catch(JWTVerificationException jwtVerificationException){

        }

        //TODO
        return null;
    }


}
