package com.ebiggerr.sims.mapper;

import com.ebiggerr.sims.domain.accountAuthenticationDTO;
import com.ebiggerr.sims.domain.accountauthentication;
import com.ebiggerr.sims.domain.accountrole;
import com.ebiggerr.sims.domain.roledetails;

import java.util.*;
import java.util.stream.Collectors;

public class accountauthenticationConverter {

    /**
     * <h1> Convert an Entity to DTO </h1>
     *
     *
     * accountauthentication : Set&lt;accountrole&gt;
     * <br></br>
     * accountAuthenticationDTO : Collection&lt;String&gt;
     *
     * @param acc Entity accountauthentication.class
     * @return accountAuthenticationDTO
     */
    public accountAuthenticationDTO entityToDTO(accountauthentication acc){

        //convert the Set<accountrole> to Collection<String>
        Set<accountrole> accountRoleSet=acc.getAccRoles();
        Iterator<accountrole> accountRoleIterator = accountRoleSet.iterator();

        accountrole temp = new accountrole();
        int size = accountRoleSet.size();

        Collection<String> roleList= new LinkedList<>();

        for( int index=0; index < size ; index ++){

            temp=accountRoleIterator.next();

            roledetails roleDetails = temp.getRoleDetailsSet();

            roleList.add( roleDetails.getRoleName() );
        }

        return new accountAuthenticationDTO( acc.getAccountID(),acc.getAccountUsername(), roleList, acc.getLastLogin(),acc.getLastActive() );

    }

    /**
     * <h1> Convert list of Entity to list of DTO </h1>
     *
     * Using the base method of entityToDTO
     * <br></br>
     * Stream
     *
     * @param accList List of Entity
     * @return List of DTO
     */
    public List<accountAuthenticationDTO> entitiesToDTO( List<accountauthentication> accList ){

        List<accountAuthenticationDTO> list=new LinkedList<>();

        for( int index=0; index< accList.size(); index++){
            list.add( this.entityToDTO( accList.get(index) ) );
        }

        return accList.stream().map(this::entityToDTO).collect(Collectors.toList());

        //return list;
    }
}
