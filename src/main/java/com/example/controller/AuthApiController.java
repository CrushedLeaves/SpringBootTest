package com.example.controller;

import com.example.entity.resp.RestBean;
import com.example.service.AccountService;
import com.example.service.VerifyService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
@Api(tags = "用户验证",description = "用户登录成功，失败的返回实体，注册以及验证码发送")
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    @Resource
    VerifyService service;
    @Resource
    AccountService accountService;

    @ApiResponses({
            @ApiResponse(code = 200,message = "邮件发送成功"),
            @ApiResponse(code = 500,message = "邮件发送失败")
    })
    @ApiOperation("请求邮件验证码")
    @GetMapping("/verify-code")
    public RestBean<Void> verifyCode(@ApiParam("邮箱地址") @RequestParam("email") String email){
        System.out.println("发送验证码"+email);
        try{
            service.sendVerifyCode(email);
            return new RestBean<>(200,"邮件发送成功");
        }catch(Exception e){
            return new RestBean<>(500,"邮件发送失败");
        }
    }
    @ApiResponses({
            @ApiResponse(code = 200,message = "登陆成功"),
            @ApiResponse(code = 403,message = "登陆失败")
    })
    @ApiOperation("发起注册请求")
    @PostMapping("/register")
    public RestBean<Void> register(
            @ApiParam("用户名") @RequestParam String username,
            @ApiParam("密码")@RequestParam String password,
            @ApiParam("邮箱地址")@RequestParam String email,
            @ApiParam("验证码")@RequestParam String verify){


        if(service.doVerify(email,verify)){
            accountService.creatAccount(username,password);
            return new RestBean<>(200,"注册成功");
        }else{
            return new RestBean<>(403,"注册失败，短信验证码填写失败");
        }
    }
    @PostMapping("/login_success")
    public RestBean<Void> successLogin(){
        System.out.println("成功登录");
        return new RestBean<>(200,"登陆成功");
    }

    @PostMapping("/login_failure")
    public RestBean<Void> failureLogin(){
        return new RestBean<>(403,"登陆失败或密码错误");
    }

    @ApiIgnore
    @RequestMapping("/access-deny")
    public RestBean<Void> accessDeny(){
        System.out.println("经行验证");
        return new RestBean<>(401,"未验证，请先登录");
    }

    @GetMapping("/logout-success")
    public RestBean<Void> logoutSuccess(){
        return new RestBean<>(200,"退出成功");
    }
}
