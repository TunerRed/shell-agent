package org.shellagent.services;

import io.jsonwebtoken.*;
import org.shellagent.exception.LoginException;
import org.shellagent.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GatewayAuth {

    @Value("${token.config.secret}")
    String secret;
    @Value("${token.config.iss}")
    String issUser;
    @Value("${token.config.expiresSeconds}")
    long expiresSeconds;
    @Value("${token.config.expiresMinutes}")
    int expiresMinutes;

    @Autowired
    HttpServletRequest request;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    Date expiration;

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String createToken (String username) {
        long curSeconds = System.currentTimeMillis();
        long expiresMillSecond = (expiresMinutes * 60 + expiresSeconds) * 1000;
        Date date = new Date(curSeconds + expiresMillSecond);
        setExpiration(date);
        JwtBuilder jwtBuilder = Jwts.builder().claim("loginTime",new Date(curSeconds)).setSubject(username)
                .setIssuer(issUser).signWith(SignatureAlgorithm.HS512,secret)
                .setExpiration(getExpiration()).setNotBefore(new Date(curSeconds));
        logger.debug("用户[" + username + "]登录，token有效期至：" +new SimpleDateFormat("MM-dd HH:mm:ss").format(date));
        return jwtBuilder.compact();
    }

    /**
     * 验证token是否有效.
     * 检查是否过期以及是否含有有效用户名
     * */
    public String verify (String token) throws LoginException {
        String username = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            username = claims.getSubject();
            Date loginTime = claims.get("loginTime",Date.class);
            if(username.isEmpty() || loginTime == null)
                throw new Exception();
        } catch (ExpiredJwtException e){
            throw new LoginException("登录过期");
        } catch (Exception e) {
            // secret==null || parse Error || username == null
            logger.debug("token认证失败："+e.getMessage());
            throw new LoginException("认证失败，请重新登录");
        }
        return username;
    }

    /**
     * 从token获取用户名.
     * 用于记录犯罪证据
     * @param token 请求头Authorization字段信息
     * @return 用户名
     * */
    public String getUser(String token) {
        String username = "";
        try {
            username = verify(token);
        } catch (Exception e) {
            logger.error("token认证失败："+e.getMessage());
        }
        return username;
    }

    public String getUsername() {
        return getUser(request.getHeader(Constant.RequestArg.Auth));
    }

}
