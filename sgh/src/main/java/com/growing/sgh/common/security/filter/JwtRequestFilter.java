package com.growing.sgh.common.security.filter;

import com.growing.sgh.common.security.constant.SecurityConstants;
import com.growing.sgh.common.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.growing.sgh.common.security.constant.SecurityConstants.*;
import static com.growing.sgh.common.security.constant.SecurityConstants.TOKEN_HEADER;

@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TOKEN_HEADER);

        if(validateToken(token)){
            setAuthentication(token);
        }

        filterChain.doFilter(request,response);
    }

    private void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean validateToken(String token){
        return jwtTokenProvider.validateToken(token);
    }
}
