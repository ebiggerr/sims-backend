package com.ebiggerr.sims.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class accountAuthentication_UserDetails implements UserDetails {

    //Entity
    private accountauthentication accountauthentication;

    // private accountAuthenticationDTO accountAuthenticationDTO;

    public accountAuthentication_UserDetails(accountauthentication accountauthentication){
        this.accountauthentication=accountauthentication;
    }

    public accountauthentication getAccountAuthentication(){
        return this.accountauthentication;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        accountauthentication acc=new accountauthentication();
        accountAuthenticationDTO accountAuthenticationDTO = acc.getAccountAuthenticationDTOFromEntity(this.getAccountAuthentication());
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();

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
        return accountauthentication.getAccountAuthenticationDTOFromEntity(this.accountauthentication);
    }

    /**
     *
     * @return
     */
    @Override
    public String getPassword() {
        return this.accountauthentication.getAccountPassword();
    }

    /**
     *
     * @return
     */
    @Override
    public String getUsername() {
        return this.accountauthentication.getAccountUsername();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEnabled() {

        return true;
    }
}
