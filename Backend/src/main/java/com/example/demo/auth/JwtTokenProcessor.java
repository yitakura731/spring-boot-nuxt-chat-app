package com.example.demo.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.example.demo.api.data.AppUser;
import com.example.demo.repository.data.BaseUser;
import com.example.demo.service.AuthService;
import com.example.demo.util.Constraints;
import com.example.demo.util.Tools;

@Component
public class JwtTokenProcessor {

    private static final String TOKEN_SECRET_KEY = "Ndsc6w4gnThisismyapplicationsecret!asf8ybaisf8ya";

    private static final long TOKEN_VAILD_DURATIONR_HOUR = 1L;

    private static final String TOKEN_ISS = "MySipAppOwner";

    private static final String TOKEN_CLAIM_KEY_ID = "id";

    private static final String TOKEN_CLAIM_KEY_USERID = "userId";

    private static final String TOKEN_CLAIM_KEY_NAME = "name";

    private static final String TOKEN_CLAIM_KEY_EMAIL = "email";

    private static Algorithm RSA256_ALGORITHM = Algorithm.HMAC256(TOKEN_SECRET_KEY);

    @Value("${jwt.leeway:0}")
    private int leeway;

    private final AuthService service;

    @Autowired
    public JwtTokenProcessor(AuthService service) {
        this.service = service;
    }

    public Authentication authenticate(HttpServletRequest request) throws Exception {
        String idToken = fetchIdToken(request);
        if (idToken != null) {
            return getAuthentication(idToken);
        } else {
            return null;
        }
    }

    public Authentication authenticate(String idToken) throws Exception {
        return getAuthentication(idToken);
    }

    public String createToken(AppUser user) {
        String sub = UUID.randomUUID().toString();
        LocalDateTime now = Tools.nowDateTime();
        Date iat = toDate(now, "Asia/Tokyo");
        LocalDateTime expire = now.plusHours(TOKEN_VAILD_DURATIONR_HOUR);
        Date exp = toDate(expire, "Asia/Tokyo");
        return JWT.create().
                withSubject(sub).
                withIssuer(TOKEN_ISS).
                withClaim(TOKEN_CLAIM_KEY_ID, user.getId()).
                withClaim(TOKEN_CLAIM_KEY_USERID, user.getUserId()).
                withClaim(TOKEN_CLAIM_KEY_NAME, user.getName()).
                withClaim(TOKEN_CLAIM_KEY_EMAIL, user.getEmail()).
                withIssuedAt(iat).
                withExpiresAt(exp).
                sign(RSA256_ALGORITHM);
    }

    public JwtAuthentication getAuthentication(final String token) throws Exception {
        DecodedJWT jwt = validateToken(token);
        Map<String, Claim> claims = jwt.getClaims();
        String userId = claims.get(TOKEN_CLAIM_KEY_USERID).asString();
        BaseUser baseUser = (BaseUser) this.service.loadUserByUsername(userId);
        return new JwtAuthentication(
                baseUser.getId(),
                baseUser.getUserId(),
                baseUser.getName(),
                baseUser.getEmail(),
                baseUser.getAuthorities());
    }

    public DecodedJWT validateToken(final String token) throws Exception {
        Verification verification = JWT.require(RSA256_ALGORITHM);
        if (leeway > 0) {
            verification.acceptLeeway(leeway);
        }
        JWTVerifier verifier = verification.build();
        return verifier.verify(token);
    }

    private static Date toDate(LocalDateTime time, String zone) {
        ZonedDateTime zdt = time.atZone(ZoneId.of(zone));
        return Date.from(zdt.toInstant());
    }

    private String fetchIdToken(HttpServletRequest request) {
        String retVal = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return retVal;
        }
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals(Constraints.COOKIE_ID_TOKEN_KEY)) {
                retVal = cookie.getValue();
                break;
            }
        }
        return retVal;
    }
}
