package com.ebiggerr.sims.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class accountAuthentication_UserDetails implements UserDetails {

    //Entity
    private final accountauthentication accountauthentication;

    private accountAuthenticationDTO accountAuthenticationDTO;

    public accountAuthentication_UserDetails(accountauthentication accountauthentication){
        this.accountauthentication=accountauthentication;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //TODO may have bug here in this method
        accountAuthenticationDTO=this.accountauthentication.getAccountAuthenticationDTOFromEntity();
        List<SimpleGrantedAuthority> authorities=new LinkedList<>();

        Collection<String> list=accountAuthenticationDTO.getAccRoles();
        int size=list.size();
        Iterator<String> iterator=list.iterator();

        for(int index=0; index< size; index++){
            authorities.add(new SimpleGrantedAuthority(iterator.next()));
        }

        return authorities;
    }

    /**
     *
     * @return accountAuthenticationDTO
     */
    public accountAuthenticationDTO getAccountAuthenticationDTOFromUserDetails(){
        return this.accountauthentication.getAccountAuthenticationDTOFromEntity();
    }

    /**
     *
     * @return
     */
    @Override
    public String getPassword() {
        return accountauthentication.getAccountPassword();
    }

    /**
     *
     * @return
     */
    @Override
    public String getUsername() {
        return accountauthentication.getAccountUsername();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }
}
