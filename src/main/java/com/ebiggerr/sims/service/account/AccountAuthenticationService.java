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

package com.ebiggerr.sims.service.account;

import com.ebiggerr.sims.domain.AccountAuthenticationDTO;
import com.ebiggerr.sims.domain.AccountAuthentication_UserDetails;
import com.ebiggerr.sims.domain.AccountAuthentication;
import com.ebiggerr.sims.mapper.AccountAuthenticationConverter;
import com.ebiggerr.sims.repository.AccountAuthenticationRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //(value = "accountAuthenticationService")
public class AccountAuthenticationService implements UserDetailsService /*,UserService*/ {

    private final AccountAuthenticationRepo accountAuthenticationRepo;

    private AccountAuthenticationConverter accMapper = new AccountAuthenticationConverter();

    //constructor-based dependency injection
   //@Autowired
    public AccountAuthenticationService(AccountAuthenticationRepo accountAuthenticationRepo){
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
    public AccountAuthentication_UserDetails loadUserByUsername(String accountUsername) {

        //returns an object with record from database using the data access methods in accountauthenticationRepo
        AccountAuthentication accountauthentication = accountAuthenticationRepo.getApprovedAccountInTheDatabaseWithJoinedAccountRoleAndRoleDetailsWithGivenAccountUsername(accountUsername);

        if( accountauthentication == null ){
            throw new UsernameNotFoundException("User Not Found.");
        }

        //wrapper
        return new AccountAuthentication_UserDetails(accountauthentication);

    }

    /**
     * <h1>Retrieve information of all active AND approved accounts</h1>
     *
     * Information: UserID, Username, Authorities, Last Login and Last Active
     *
     * @return [List  &lt;accountAuthenticationDTO&gt; ] : List of all the active AND approved accounts
     */
    public List<AccountAuthenticationDTO> getAllAccountInfoWithRoles(){

        // mapping to DTO from entity to eliminate Sensitive credentials such as password
        return accMapper.entitiesToDTO( accountAuthenticationRepo.getAllApprovedAccountInTheDatabaseWithJoinedAccountRoleAndRoleDetails() );
    }

}
