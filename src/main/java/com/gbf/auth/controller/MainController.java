package com.gbf.auth.controller;

import com.gbf.auth.dto.*;
import com.gbf.auth.model.User;
import com.gbf.auth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
public class MainController {

    @Autowired
    private TokenService service;

    @PostMapping("/login")
    public RefreshToken login(HttpServletResponse response, @RequestBody AuthCredentionals authCredentionals) {
        User user = service.login(authCredentionals);
        System.out.println("user = " + user);
        service.addAuthTokenToResponse(response, user);
        return service.getRefreshToken(user);
    }

    @PostMapping("/refresh")
    public RefreshToken refresh(HttpServletResponse response, @RequestBody RefreshToken refreshToken){
        return service.refresh( response, refreshToken);
    }

    @PostMapping("/validate")
    public AuthorisationData validate(@RequestBody AuthToken authToken){
        return service.validate(authToken);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = WebUtils.getCookie(request, TokenService.COOKIE_BEARER);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @GetMapping("/getkey")
    public PublicKeyDto giveKey() throws NoSuchAlgorithmException, IOException {
        return service.getPublicKey();
    }

}
