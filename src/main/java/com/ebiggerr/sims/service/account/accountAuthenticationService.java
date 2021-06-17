package com.ebiggerr.sims.service.account;

import com.ebiggerr.sims.domain.accountAuthenticationDTO;
import com.ebiggerr.sims.domain.accountAuthentication_UserDetails;
import com.ebiggerr.sims.domain.accountauthentication;
import com.ebiggerr.sims.mapper.accountauthenticationConverter;
import com.ebiggerr.sims.repository.accountAuthenticationRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //(value = "accountAuthenticationService")
public class accountAuthenticationService implements UserDetailsService /*,UserService*/ {

    private final accountAuthenticationRepo accountAuthenticationRepo;

    private accountauthenticationConverter accMapper = new accountauthenticationConverter();

    //constructor-based dependency injection
   //@Autowired
    public accountAuthenticationService( accountAuthenticationRepo accountAuthenticationRepo){
        this.accountAuthenticationRepo = accountAuthenticationRepo;
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
    public accountAuthentication_UserDetails loadUserByUsername(String accountUsername) {

        //returns an object with record from database using the data access methods in accountauthenticationRepo
        accountauthentication accountauthentication = accountAuthenticationRepo.findaccountAutheticationWithAccountRolesAndRoleDetailsByAccountUsername(accountUsername);

        if( accountauthentication == null ){
            throw new UsernameNotFoundException("User Not Found.");
        }

        //wrapper
        return new accountAuthentication_UserDetails(accountauthentication);

    }

    /**
     * <h1>Retrieve information of all active AND approved accounts</h1>
     *
     * Information: UserID, Username, Authorities, Last Login and Last Active
     *
     * @return [List  &lt;accountAuthenticationDTO&gt; ] : List of all the active AND approved accounts
     */
    public List<accountAuthenticationDTO> getAllAccountInfoWithRoles(){

        // mapping to DTO from entity to eliminate Sensitive credentials such as password
        return accMapper.entitiesToDTO( accountAuthenticationRepo.getAll() );
    }


    //@Deprecated
    /*
    @Override
    public accountauthentication findOne(String username) {
        return accountAuthenticationRepo.findaccountAutheticationWithAccountRolesAndRoleDetailsByAccountUsername(username);
    }*/
}
