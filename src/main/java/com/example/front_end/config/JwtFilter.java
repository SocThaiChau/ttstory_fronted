package com.example.front_end.config;

import com.example.front_end.model.response.AuthenticaResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
//    @Autowired
    public String accessToken ;
    public AuthenticaResponse authenticaResponse;
    public String getAccessToken(){
        return accessToken;
    }
    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public AuthenticaResponse getAuthenticaResponse(){
        return authenticaResponse;
    }
    public void setAuthenticaResponse(AuthenticaResponse authenticaResponse){
        this.authenticaResponse = authenticaResponse;
    }

    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String authHeader = request.getHeader(AUTHORIZATION);
//        System.out.println(authHeader);
//        if(accessToken == null) {
//            accessToken = authHeader;
//        }
//        System.out.println(accessToken);
        filterChain.doFilter(request, response);
    }
}
