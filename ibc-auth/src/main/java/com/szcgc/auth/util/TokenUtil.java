package com.szcgc.auth.util;

import com.szcgc.comm.util.StringUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author liaohong
 * @create 2022/9/29 20:15
 * 需要和网关应用配套
 */
public class TokenUtil {

    private static final String LOGIN_USER_KEY = "login_user_key";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String SECRET = "ibc_CGC_20!2";

    public static String makeToken(String uuid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(LOGIN_USER_KEY, uuid);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public static String readToken(String token) {
        if (token == null)
            return null;
        if (!StringUtils.isEmpty(token) && token.length() > TOKEN_PREFIX.length() && token.startsWith(TOKEN_PREFIX)) {
            token = token.substring(TOKEN_PREFIX.length());
        }
        Map<String, Object> claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        if (claims == null || claims.isEmpty())
            return null;
        return (String) claims.get(LOGIN_USER_KEY);
    }
}
