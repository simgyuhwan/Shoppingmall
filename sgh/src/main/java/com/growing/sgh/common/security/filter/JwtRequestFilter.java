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
        String header = request.getHeader(TOKEN_HEADER);

        if(isEmpty(header) || !header.startsWith(TOKEN_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);

    }
    private boolean isEmpty(String header){
        return header == null | header.length() == 0;
    }
}
