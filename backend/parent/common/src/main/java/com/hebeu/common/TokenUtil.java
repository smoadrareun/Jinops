package com.hebeu.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: TokenUtil
 * @Author: Smoadrareun
 * @Description: TODO Token工具类
 */

public class TokenUtil {

    //设置过期时间
    private static final long EXPIRE_DATE=30*60*100000;
    //token秘钥
    private static final String TOKEN_SECRET = "0b85cc0e3ac111d2a7bc88387efc4c74";

    public static String getToken(String uname,String passwd,String salt){
        String token = null;
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("uname",uname)
                    .withClaim("passwd",passwd)
                    .withClaim("salt",salt)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return token;
    }

    public static List<String> verify(String token){
        List<String> list=new ArrayList<>();
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            list.add(jwt.getClaim("uname").asString());
            list.add(jwt.getClaim("passwd").asString());
            list.add(jwt.getClaim("salt").asString());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
