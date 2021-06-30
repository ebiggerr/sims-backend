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

package com.ebiggerr.sims.controller.account;

import com.ebiggerr.sims.config.jwt.Token_Provider;
import com.ebiggerr.sims.domain.account.AccountEntity;
import com.ebiggerr.sims.domain.AccountAuthenticationDTO;
import com.ebiggerr.sims.domain.AccountAuthentication_UserDetails;
import com.ebiggerr.sims.domain.AccountAuthentication;
import com.ebiggerr.sims.domain.request.AuthenticationRequest;
import com.ebiggerr.sims.domain.response.API_Response;
import com.ebiggerr.sims.domain.response.JWTToken;
import com.ebiggerr.sims.service.account.AccountAuthenticationService;
import com.ebiggerr.sims.service.account.AccountService;
import com.ebiggerr.sims.service.input.InputCheckValid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AccountMainController {

    private Logger logger = LoggerFactory.getLogger(AccountMainController.class);

    private final AuthenticationManager authenticationManager;

    //service Token Provider
    private final Token_Provider tokenProvider;

    // service for register and logout
    private final AccountService accountService;

    //service for authentication and authorisation
    private final AccountAuthenticationService accountAuthenticationService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    private boolean passwordMatch;

    //constructor dependency injection
    //@Autowired
    public AccountMainController(AuthenticationManager authenticationManager, Token_Provider tokenProvider, AccountService accountService, AccountAuthenticationService accountAuthenticationService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.accountService = accountService;
        this.accountAuthenticationService = accountAuthenticationService;
    }

    /**
     * <h1>Authentication using username and password</h1>
     *
     * @param request Contains Username and Password, for authentication
     * @return {String} JSON Web Token wrapped in API_RESPONSE
     */
    @PostMapping(path = "/authenticate" )
    public API_Response returnJWTAfterAuthentication(@RequestBody AuthenticationRequest request){

        AccountAuthentication_UserDetails acc = new AccountAuthentication_UserDetails(null);

        try {
            //check if there is any record with matching username in the database
            acc = accountAuthenticationService.loadUserByUsername(request.getUsername());

        }catch (UsernameNotFoundException e){ //NOT FOUND
            logger.info( "Failed Authentication: Username - " + request.getUsername() + " | Message: " + e.getMessage() );
            return new API_Response().Unauthorized();
        }

        Authentication authentication = null;

        //check if the password from the request matches with the password retrieved from database
        if( acc == null || !bCryptPasswordEncoder.matches( request.getPassword(), acc.getPassword() )){ //PASSWORD NOT MATCH OR USERNAME NOT FOUND
            logger.info( "Failed Authentication: Username - " + request.getUsername() );
            return new API_Response().Unauthorized();
        }

        //Matched Username and Password from the record in the database
        try {
            //populated an Authentication object with Username and Password
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (UsernameNotFoundException e) {
            logger.info( "Failed Authentication: Username - " + request.getUsername() + " | Message: " + e.getMessage() );
        }

        if( authentication.isAuthenticated() ) {

            //update user's last login time
            accountService.updateLastLoginTime( request.getUsername() );
            logger.info("JWT Token generated for user: " + request.getUsername() );

            //return JWT wrapped in the response
            return new API_Response().Success( new JWTToken( tokenProvider.generateToken(authentication) ) );
        }

        return new API_Response().UserNotFound();
    }

    /**
     * <h1>Registration using username and password</h1>
     *
     * @param credentials Credentials for registration : Username and Password
     * @return response to the registration ? Succeed : Failed
     */
    @PostMapping(path = "/register")
    public API_Response registerAccount(@RequestBody AuthenticationRequest credentials){

        String encodedPasswordFromRegisterRequest = bCryptPasswordEncoder.encode( credentials.getPassword() );
        String message="Something Went Wrong";

        if ( InputCheckValid.checkEmail( credentials.getUsername()) ) {
            if ( accountService.registerAccount( credentials.getUsername(), encodedPasswordFromRegisterRequest ) ) return new API_Response().Success();
            else{
                message = "Duplicates";
            }
        }
        else{ return new API_Response().Failed("Invalid Email Address");
        }

        return new API_Response().Failed(message);
    }

    /**
     * <h1>Update user's last active time</h1>
     *
     * @param request User's username
     * @param token JWT in the header of the request, to extract the username from the token
     * @return Response to the request
     */
    @PreAuthorize("hasAnyAuthority('Admin','Manager','Staff')")
    @PostMapping(path= "/updateLastActive" )
    public API_Response logout(@RequestBody AuthenticationRequest request, @RequestHeader (name="Authorization") String token){

        accountService.updateLastActiveTime( request.getUsername() );
        logger.info("Logout Operation: " + tokenProvider.getUsernameFromToken(token) );

        return new API_Response().Success();
    }

    /**
     * <h1>Provide user the details of requested user by username</h1>
     *
     * @param username Username of the account
     * @return Details of that requested account wrapped in API_RESPONSE
     */
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping(path="/account/details/{username}")
    public API_Response getAccountDetailsWithGivenUsername(@PathVariable String username){

        AccountAuthentication found = accountService.getByUsername(username);

        if( found != null ) return new API_Response().Success( found );
        return new API_Response().Failed("Not Found");
    }

    /**
     * <h1>Provide user the details of all active users</h1>
     *
     * @return Details of all accounts wrapped in API_RESPONSE
     */
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping(path="/account/details/allActive")
    public API_Response getAllActiveAccountDetails(){

        List<AccountAuthenticationDTO> found = accountAuthenticationService.getAllAccountInfoWithRoles();

        if( found != null ) return new API_Response().Success( found );
        return new API_Response().Failed("Not Found");
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping(path="/account/details/allPending")
    public API_Response getAllPendingAccountDetails(){

        List<AccountEntity> found = accountService.getAllPending();

        if( found != null ) return new API_Response().Success( found );
        return new API_Response().Failed("Not Found");
    }

    /**
     * <h1> Update Roles of an account with given username</h1>
     *
     * Update of roles will not happen if there is any conflict, and that account have that roles/authorities
     * For example: If user1 has roles of Admin, roles of "Admin" will not be updated again if the @param roles is Admin,Manager [ Update Manager only, ignoring Admin]
     *
     *
     * @param username Username of that account
     * @param roles [String] A series of roles separated with comma. Example: Admin,Manager,Staff
     * @param token Extract the username from JWT for user operation logging
     * @return Response to the operation ? Succeed : Failed
     */
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping(path = "/account/addroles/{username}")
    public API_Response updateRolesOfAnAccountWithGivenUsername(@PathVariable String username, @RequestParam(name = "roles") String roles, @RequestHeader (name="Authorization") String token){

        //A series of roles separated with comma. Example: Admin,Manager,Staff
        String[] arr= roles.split(",");

        boolean success = accountService.updateRolesForAnAccount(arr, username);

        if( !success ) return new API_Response().Error("Something Went Wrong");

        logger.info( "[Admin]" + tokenProvider.getUsernameFromToken(token) + " updates [Roles] of Account with [Username] of " + username + " with " + Arrays.toString(arr) );
        return new API_Response().Success();
    }

    /**
     * <h1> Revoke Roles of an account with given username</h1>
     *
     *
     *
     * @param username Username of that account
     * @param roles [String] A series of roles separated with comma. Example: Admin,Manager,Staff
     * @param token Extract the username from JWT for user operation logging
     * @return Response to the operation ? Succeed : Failed
     */
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping(path = "/account/revokeroles/{username}")
    public API_Response revokeRolesOfAnAccountWithGivenUsername(@PathVariable String username, @RequestParam(name = "roles") String roles, @RequestHeader (name="Authorization") String token){

        String[] arr = roles.split(",");

        boolean success = accountService.revokeRolesForAnAccount(arr,username);

        if( !success ) return new API_Response().Error("Something Went Wrong");

        logger.info( "[Admin]" + tokenProvider.getUsernameFromToken(token) + " revoked the [Roles] " + Arrays.toString(arr) + " of Account with [Username] of " + username  );
        return new API_Response().Success();
    }

    /**
     * <h1> Revoke an account from accessing the system </h1>
     *
     * Update the accountStatus of an account from "APPROVED" to "PENDING"
     *
     * @param username Target account's username
     * @param token Extract the username rom JWT for user operation logging
     * @return Response to the operation ? Succeed : Failed
     */
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping(path = "/account/revoke/{username}")
    public API_Response removeAnAccountWithGivenUsername(@PathVariable String username,@RequestHeader (name="Authorization") String token){

        try{
            boolean success = accountService.revokeAnAccount(username);
            if ( success ) logger.info( "[Admin]" + tokenProvider.getUsernameFromToken(token) + " revoked Account with [Username] of " + username  );
            else{
                return new API_Response().NoRecordFound();
            }
            return new API_Response().Success();
        }catch (UsernameNotFoundException e){
            return new API_Response().UserNotFound();
        }

    }

    /**
     * <h1> Approve an account to have access to the system </h1>
     *
     * Update the accountStatus of an account from "PENDING" to "APPROVED"
     *
     * @param username Target account's username
     * @param token Extract the username rom JWT for user operation logging
     * @return Response to the operation ? Succeed : Failed
     */
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping(path = "/account/approve/{username}")
    public API_Response approveAnAccountWithGivenUsername( @PathVariable String username,@RequestHeader (name="Authorization") String token ){

        try{
            boolean success = accountService.approveAnAccount(username);
            if( success ) logger.info( "[Admin]" + tokenProvider.getUsernameFromToken(token) + " approved Account with [Username] of " + username  );
            else{
                return new API_Response().NoRecordFound();
            }
        }catch (Exception e){
            return new API_Response().Failed("Something Went Wrong.");
        }

        return new API_Response().Success();
    }


}
