package com.example.service;

import org.springframework.stereotype.Service;

@Service
public interface VerifyService  {
    public void sendVerifyCode(String mail);
    boolean doVerify(String email,String code);
}
