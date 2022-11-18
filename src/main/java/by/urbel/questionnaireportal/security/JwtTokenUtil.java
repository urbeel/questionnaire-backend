package by.urbel.questionnaireportal.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expired}")
    private long validityInMilliseconds;

    public String generateAccessToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String validateTokenAndGetEmail(String token) {
        return JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token).getSubject();
    }
}
