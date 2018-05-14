package meng.xing.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import meng.xing.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Java Web Token（JWT）的处理类
 * token 信息：
 * 1.用户名
 * 2.创建时间
 * 3.过期时间
 * token 非法：
 * 1.无法解析
 * 2.过期时间 小于 当前系统时间
 * 3.创建时间 小于 密码修改时间
 */
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "username"; //用户名
    private static final String CLAIM_KEY_CREATED = "created";//创建时间
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.secret}")
    private String secret;

    private Date getCreatedDateFromToken(String token) {
        token = subTokenHead(token);
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    private Date getExpirationDateFromToken(String token) {
        token = subTokenHead(token);
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getUsernameFromToken(String token) {
        token = subTokenHead(token);
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = String.valueOf(claims.get(CLAIM_KEY_USERNAME));
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
//
//    public String refreshToken(String token, User user) {
//        if (!canTokenBeRefreshed(token, user.getLastPasswordResetDate())) return null;
//        String refreshedToken;
//        try {
//            Map<String, Object> claims = new HashMap<>();
//            claims.put(CLAIM_KEY_USERNAME, getUsernameFromToken(token));
//            claims.put(CLAIM_KEY_CREATED, new Date());
//            refreshedToken = generateToken(claims);
//        } catch (Exception e) {
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }

    public Boolean validateToken(String token, User user) {

        final String _username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);

        return (_username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    private String generateToken(Map<String, Object> claims) {
        return tokenHead + Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
//
//    private boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//        final Date created = getCreatedDateFromToken(token);
//        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
//                && !isTokenExpired(token);
//    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private String subTokenHead(String token) {
        return token.substring(tokenHead.length());
    }
}
