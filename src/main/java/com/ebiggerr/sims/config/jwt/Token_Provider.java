
/*
 * Copyright (c) 2021 EbiggerR
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ebiggerr.sims.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ebiggerr.sims.domain.accountAuthentication_UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    private Logger logger = LoggerFactory.getLogger(Token_Provider.class);

    //@Value("${secret.key}")
    private final String privateKey = "jXn2r5u8x!A%D*G-KaPdSgVkYp3s6v9y";
    private final Algorithm algorithm = Algorithm.HMAC256(privateKey);

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
        String username;
        if (principal instanceof accountAuthentication_UserDetails) {
             username = ((accountAuthentication_UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        try{
            if( username != null ) {

                return JWT.create()
                        .withClaim("username", username)
                        .withClaim("roles", authorities)
                        .withIssuedAt(now)
                        .withExpiresAt(exp)
                        .withIssuer("auth0")
                        .sign(algorithm);
            }
            else{
                throw new JWTCreationException("Null Username", new Throwable("Null Username") );
                //return "Valid Authentication. Something went wrong during the creation of JWT. Please try again.";
            }

        }catch (JWTCreationException exception){
            logger.error( "Something Wrong During the Creation of JWT: " + exception.getMessage() );
            return "Valid Authentication. Something went wrong during the creation of JWT. Please try again.";
        }

    }

    /**
     *
     * @param token JSON Web Token
     * @return DecodedJWT from the JSON Web Token
     */
    public DecodedJWT verifyAndDecodeToken(String token){

        JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();

        return verifier.verify(token);
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

        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

        token = decodedJWT.getClaim("username").asString();

        return token; //username from JWT
    }

}
