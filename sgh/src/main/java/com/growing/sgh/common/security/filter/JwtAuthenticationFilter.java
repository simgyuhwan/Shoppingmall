package com.growing.sgh.common.security.filter;

import com.growing.sgh.common.security.constant.SecurityConstants;
import com.growing.sgh.common.security.entity.CustomUser;
import com.growing.sgh.common.security.provider.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.growing.sgh.common.security.constant.SecurityConstants.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;

        setFilterProcessesUrl(AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(" ------------------------------------------");
        System.out.println(" ------------------------------------------");
        System.out.println(" ------------------------------------------");
        System.out.println(" ------------------------------------------");
        System.out.println("username :  " + username);
        System.out.println("password : " + password);
        System.out.println(" ------------------------------------------");
        System.out.println(" ------------------------------------------");
        System.out.println(" ------------------------------------------");
        System.out.println(" ------------------------------------------");
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUser user = (CustomUser) authResult.getPrincipal();
        String username = user.getUsername();
        long memberId = user.getMemberId();

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtTokenProvider.createToken(username, memberId, roles);

        response.addHeader(TOKEN_HEADER,TOKEN_PREFIX+ token);
    }
}
