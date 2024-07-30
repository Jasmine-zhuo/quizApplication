package com.bfs.logindemo.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
@Component
public class LoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("In LoginFilter");
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            filterChain.doFilter(request, response);
        } else {
            // redirect back to the login page if user is not logged in
            response.sendRedirect("/login");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        System.out.println("path = " + path);
        return "/login".equals(path) || "/contact".equals(path) || "/WEB-INF/jsp/login.jsp".equals(path);
    }
}
