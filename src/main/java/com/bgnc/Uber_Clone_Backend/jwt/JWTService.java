package com.bgnc.Uber_Clone_Backend.jwt;


import com.bgnc.Uber_Clone_Backend.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTService {

    public static final String SECRET_KEY = "JfVoLB61pmyKbWswGsuxD6TMnPFZzsoOACmSd1mA5hM=";

    public String generateToken(UserDetails userDetails, Set<Role> roles) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles.stream()
                        .map(Enum::name) // Role enum'larını String'e çeviriyoruz
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // 2 saatlik geçerlilik
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Token'dan kullanıcı adını çıkarır.
     */
    public String getUsernameByToken(String token) {
        return exportToken(token, Claims::getSubject);
    }

    /**
     * Token'ın geçerliliğini kontrol eder (expiration tarihi kontrol edilir).
     */
    public boolean isTokenValid(String token) {
        Date expireDate = exportToken(token, Claims::getExpiration);
        return new Date().before(expireDate);
    }

    public Set<Role> getRolesByToken(String token) {
        return exportToken(token, claims -> {
            // "roles" claim'ini al ve bir Collection olarak işle
            var roles = claims.get("roles", Collection.class);

            if (roles == null) {
                throw new IllegalArgumentException("Token does not contain roles");
            }

            // Collection içindeki her bir rolü Role enum'una çevir ve Set'e dönüştür
            return ((Collection<?>) roles).stream()
                    .map(Object::toString) // Her bir elemanı String'e dönüştür
                    .map(Role::valueOf)   // String'i Role enum'una dönüştür
                    .collect(Collectors.toSet());
        });
    }

    /**
     * Genel amaçlı bir token verisi okuma fonksiyonu.
     */
    public <T> T exportToken(String token, Function<Claims, T> claimsFunc) {
        Claims claims = getClaims(token);
        return claimsFunc.apply(claims);
    }

    /**
     * Token'daki Claims (payload) bilgilerini döner.
     */
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Secret Key ile Key nesnesi oluşturur.
     */
    public Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}