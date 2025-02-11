package com.weshallsah.transcoder.services;

import com.weshallsah.transcoder.model.UsersDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTservices {

    private String secretKey = "";

    public JWTservices()  {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 60))
                .and()
                .signWith(getKey())
                .compact();
    }

    public SecretKey getKey(){
        byte[] keybyte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keybyte);
    }

    public String getusername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extreactAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extreactAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build().
                parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UsersDetails usersDetails) {
        final String username = getusername(token);
        return (username.equals(usersDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

}
