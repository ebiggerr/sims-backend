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

package com.ebiggerr.sims.controller.account;

import com.ebiggerr.sims.domain.response.API_Response;
import com.ebiggerr.sims.domain.response.JWTToken;
import com.ebiggerr.sims.config.jwt.Token_Provider;
import com.ebiggerr.sims.domain.accountAuthenticationDTO;
import com.ebiggerr.sims.domain.accountAuthentication_UserDetails;
import com.ebiggerr.sims.domain.request.authenticationRequest;
import com.ebiggerr.sims.service.account.accountAuthenticationService;
import com.ebiggerr.sims.service.account.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class accountMainController {

    private final AuthenticationManager authenticationManager;

    //service Token Provider
    private final Token_Provider tokenProvider;

    // service for register and logout
    private final accountService accountService;

    //service for authentication and authorisation
    private final accountAuthenticationService accountAuthenticationService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    private boolean passwordMatch;

    //constructor dependency injection
    //@Autowired
    public accountMainController(AuthenticationManager authenticationManager, Token_Provider tokenProvider, accountService accountService, accountAuthenticationService accountAuthenticationService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.accountService = accountService;
        this.accountAuthenticationService = accountAuthenticationService;
    }

    //endpoint for authentication
    @PostMapping(path = "/authenticate" )
    public API_Response returnJWTAfterAuthentication(@RequestBody authenticationRequest request){

        //TODO SIMS-1 trying to improve this section so no need to create a new object of 'accountAuthenticationDTO'
        //using accountAuthenticaion_UserDetails would be sufficient
        //get username, password and authorities[]

        accountAuthentication_UserDetails acc= (accountAuthentication_UserDetails) accountAuthenticationService.loadUserByUsername( request.getUsername() );
        accountAuthenticationDTO accDTO=acc.getAccountAuthenticationDTOFromUserDetails();

        final Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //TODO SIMS-2 IF SIMS-1 success, then does not need this step
        //can use the Authentication object
        //compare the password from request and the password from matching record in database
        passwordMatch=bCryptPasswordEncoder.matches(request.getPassword(),
                                                    accDTO.getAccountPassword() );
        if( !passwordMatch ) {
            return new API_Response().Unauthorized();
        }
        if( acc == null ) {
            return new API_Response().UserNotFound();
        }

        //TODO change the dummy response to meaningful response
        //return new API_Response().Success( new JWTToken( tokenProvider.generateToken(authentication) ) );
        return new API_Response().Unauthorized(); // dummy response
    }

    @PostMapping(path = "/register")
    public API_Response registerAccount(@RequestBody authenticationRequest credentials){

        //registration

        return new API_Response().Success();
    }

    // endpoint that update account lastActive time
    @PostMapping(path= "/logout" )
    public API_Response logout(@RequestBody authenticationRequest request){

        accountService.logout(request.getUsername());

        return new API_Response().Success();
    }

    //@PostMapping(path = "register" )

}
