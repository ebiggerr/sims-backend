package com.ebiggerr.sims.service.account;

import com.ebiggerr.sims.domain.account.accountEntity;
import com.ebiggerr.sims.repository.accountRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class accountService {

    private final accountRepo accountRepo;

    //constructor-based dependency injection
    //@Autowired
    public accountService(accountRepo accountRepo){
        this.accountRepo=accountRepo;
    }

    public void registerAccount(accountEntity accountEntity){
        accountRepo.registerAccount(accountEntity);
    }

    public void logout(String accountUsername){
        accountRepo.logoutUpdateLastActive( System.currentTimeMillis(), accountUsername);
    }

}
