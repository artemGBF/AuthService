package com.gbf.auth.service;

import com.gbf.auth.dto.*;
import com.gbf.auth.model.User;
import com.gbf.auth.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    public static final String COOKIE_BEARER = "COOKIE-BEARER";
    private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    private Map<String, Long> repo = new ConcurrentHashMap<>();//многопоточный доступ к мапе

    /**
     * 30 min
     */
    @Value("${auth.token.expiration}")
    private Integer authTokenExp;
    /**
     * 240 min
     */
    @Value("${refresh.token.expiration}")
    private Integer refreshTokenExp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserValidationService userValidationService;

    //TODO проверки сделать
    public User login(AuthCredentionals credentionals) {
        User user = userRepository.findByLogin(credentionals.getLogin())
                .orElseThrow(() -> new RuntimeException("пользователь с " + credentionals.getLogin() + " не найден"));
        if (!passwordEncoder.matches(credentionals.getPassword(), user.getPassword())) {
            throw new RuntimeException("invalid password");
        }
        userValidationService.validateUser(user);
        return user;
    }

    public void addAuthTokenToResponse(HttpServletResponse response, User user) {
        String token = Jwts.builder()
                .setSubject(user.getLogin())
                .claim("authorities", List.of(user.getRole().name()))
                .signWith(keyPair.getPrivate())
                .setExpiration(new Date(System.currentTimeMillis() + authTokenExp))
                .compact();
        Cookie cookie = new Cookie(COOKIE_BEARER, token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public RefreshToken getRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken(Jwts.builder()
                .setSubject(user.getLogin())
                .claim("authorities", List.of(user.getRole().name()))
                .signWith(keyPair.getPrivate())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExp))
                .compact());
        this.repo.put(refreshToken.getRefreshToken(), user.getId());
        return refreshToken;
    }

    public AuthorisationData validate(AuthToken authToken) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(authToken.getAuthToken());
        String subject = claimsJws.getBody().getSubject();
        ArrayList<String> authorities = claimsJws.getBody().get("authorities", ArrayList.class);
        return new AuthorisationData(subject, authorities);
    }

    //TODO проверки статусов
    public RefreshToken refresh(HttpServletResponse response, @RequestBody RefreshToken refreshToken) {

        Long id = this.repo.remove(refreshToken.getRefreshToken());
        if (id == null) {
            throw new RuntimeException("Not valid refresh token");
        }

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(refreshToken.getRefreshToken());
        String login = claimsJws.getBody().getSubject();
        User user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("not found"));
        if (!id.equals(user.getId())) {
            throw new RuntimeException("Not valid refresh token");
        }
        userValidationService.validateUser(user);
        addAuthTokenToResponse(response, user);
        return getRefreshToken(user);
    }

    public PublicKeyDto getPublicKey() throws NoSuchAlgorithmException, IOException {
        return new PublicKeyDto(keyPair.getPublic());
    }
}
