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
