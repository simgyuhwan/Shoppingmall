package com.growing.sgh.common.security.provider;

import com.growing.sgh.common.security.CustomUserDetailService;
import com.growing.sgh.common.security.constant.SecurityConstants;
import com.growing.sgh.common.security.prop.JwtProperties;
import com.growing.sgh.domain.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final CustomUserDetailService userDetailService;

    public String createToken(String username, long memberId, List<String> roles){
        byte[] signKey = getSignKey();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.Token_TYPE)
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("unm", username)
                .claim("mid", "" + memberId)
                .claim("rol", roles)
                .compact();

        return token;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader){
        if(isNotEmpty(tokenHeader)){
            try{
                Jws<Claims> parsedToken = Jwts.parserBuilder()
                        .setSigningKey(getSignKey())
                        .build().parseClaimsJws(tokenHeader.replace("Bearer ", ""));

                Claims claims = parsedToken.getBody();

                String username = (String) claims.get("unm");

                List<SimpleGrantedAuthority> authorities = ((List<?>) claims.get("rol")).stream()
                        .map(auth -> new SimpleGrantedAuthority((String) auth))
                        .collect(Collectors.toList());

                if(isNotEmpty(username)){
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);

                    return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                }
            }catch (ExpiredJwtException exception){
                log.warn("Request to parse expired JWT : {} failed : {} ", tokenHeader, exception.getMessage());
            }catch (UnsupportedJwtException exception){
                log.warn("Request to parse unsupported JWT : {} failed {} ", tokenHeader, exception.getMessage());
            }catch (MalformedJwtException exception){
                log.warn("Request to parse invalid JWT : {} failed : {} ", tokenHeader, exception.getMessage());
            }catch (IllegalStateException exception){
                log.warn("Request to parse empty or null JWT : {} failed : {}", tokenHeader, exception.getMessage());
            }
        }
        return null;
    }

    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build().parseClaimsJws(jwtToken);
            return claims.getBody().getExpiration().after(new Date());
        }catch (ExpiredJwtException exception){
            log.error("Token Expired");
            return false;
        }catch (JwtException exception){
            log.error("Token Tampered");
            return false;
        }catch (NullPointerException exception){
            log.error("Token is null");
            return false;
        }catch(Exception e){
            return false;
        }
    }

    private boolean isNotEmpty(final CharSequence cs){
        return !isEmpty(cs);
    }

    private boolean isEmpty(final CharSequence cs){
        return cs == null || cs.length() == 0;
    }
    private byte[] getSignKey(){
        return jwtProperties.getSecretKey().getBytes();
    }
}
