package com.example.board.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.board.exception.jwt.JwtTokenNotFoundExcpetion;
import com.example.board.service.JwtService;
import com.example.board.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // JWT 검증
        String bearer_prefix = "Bearer ";
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        var securitycontext = SecurityContextHolder.getContext();

        // if(ObjectUtils.isEmpty(authorization)|| !authorization.startsWith(bearer_prefix)){
        //     throw new JwtTokenNotFoundExcpetion();
        // }

        if(!ObjectUtils.isEmpty(authorization) && authorization.startsWith(bearer_prefix) && securitycontext.getAuthentication() == null){
            var accessToken = authorization.substring(bearer_prefix.length());
            var username = jwtService.getUsername(accessToken);
            var userDetails = userService.loadUserByUsername(username);

            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securitycontext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securitycontext);
        }
        
        filterChain.doFilter(request, response);
    }

}
