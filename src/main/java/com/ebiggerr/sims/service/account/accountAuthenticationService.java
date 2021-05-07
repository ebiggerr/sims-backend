package com.ebiggerr.sims.service.account;

import com.ebiggerr.sims.domain.accountauthentication;
import com.ebiggerr.sims.domain.accountAuthentication_UserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ebiggerr.sims.repository.accountAuthenticationRepo;

@Service
public class accountAuthenticationService implements UserDetailsService {

    //immutable
    private final accountAuthenticationRepo accountAuthenticationRepo;

    //constructor-based dependency injection
   //@Autowired
    public accountAuthenticationService( accountAuthenticationRepo accountAuthenticationRepo){
        this.accountAuthenticationRepo=accountAuthenticationRepo;
    }

    /**
     * <h1>Using the method of DAO(accountauthentication) to search for a record in database with matching username</h1>
     *
     * <p>wrapped the accountauthentication object in the accountAuthentication_UserDetails class</p>
     *
     * <p>therefore the getters in the accountauthentication can be used to retrieve information needed.<p>
     *
     * @param accountUsername username of an account
     * @return accountauthentication wrapped in class(accountAuthentication_UserDetails) that implements the UserDetails interface
     *
     *
     */
    @Override
    public UserDetails loadUserByUsername(String accountUsername) {

        //returns an object with record from database using the data access methods in accountauthenticationRepo
        accountauthentication accountauthentication= accountAuthenticationRepo.findaccountAutheticationWithAccountRolesAndRoleDetailsByAccountUsername(accountUsername);

        if( accountauthentication == null ){
            throw new UsernameNotFoundException("User Not Found.");
        }

        //wrapper
        return new accountAuthentication_UserDetails(accountauthentication);

    }
}
