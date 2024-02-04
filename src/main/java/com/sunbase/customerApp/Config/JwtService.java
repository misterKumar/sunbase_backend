package com.sunbase.customerApp.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {
    private static final String SECRET_KEY = "47516d694963652b6a6d66456a466372615a66374e473931792f72614e7a426353396d744e516f574847797241414a67476b6d354d70506a572f37584c624835a";
    /**
     * @param token jwt token passed in the arg
     * @return extreact the subject part from the jwt token. i.e email
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * @param token A hexadecimal secret key
     * @param claimsResolver get all the claims in the jwt token
     * @return all the claims
     * @param <T>
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /*
     * Generate a signing key for our JWT signature
     * */
    private Key getSingInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    /**
     * @param userDetails user authentication credentials
     * @return our Jwt Token
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(
            Map<String, Objects> extractClaims,
            UserDetails userDetails
    ){
//        generating header, payload and signature of the Jwt Token
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // token validation
    public boolean isTokenValid(String token, UserDetails userDetails){
//        extract username from user token
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    //    verify that the time duration for token validation
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //    get the expiration date of the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}