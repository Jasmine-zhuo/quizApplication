package com.bfs.logindemo.filter;

import com.bfs.logindemo.domain.User;
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


        HttpSession session = request.getSession(false);
        String path = request.getRequestURI();
        System.out.println("In LoginFilter");
        System.out.println("path = " + path);
        System.out.println("session = " + session);
        if (session != null) {
            System.out.println("user = " + session.getAttribute("user"));
            System.out.println("isAdmin = " + session.getAttribute("isAdmin"));
        }


        // Allow access to these URLs without authentication
        if ("/login".equals(path) || path.startsWith("/contact") ||
                "/register".equals(path) || path.startsWith("/admin/login")
                || path.startsWith("/resources/")|| path.equals("/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = (User) (session != null ? session.getAttribute("user") : null);
        Boolean isAdmin = (Boolean) (session != null ? session.getAttribute("isAdmin") : null);

        if (user == null) {
            // Redirect to login page if user is not logged in
            response.sendRedirect("/login");
            return;
        }

        // Redirect based on user roles
        if (isAdmin != null && isAdmin) {
            // If the user is an admin, allow access to admin pages
            if (path.startsWith("/admin") || path.startsWith("/quiz-result")) {
                filterChain.doFilter(request, response);
            } else {
                // Admins should not access non-admin pages, redirect to admin home
                response.sendRedirect("/admin/home");
            }
        } else {
            // If the user is not an admin, allow access to non-admin pages
            if (path.startsWith("/admin")) {
                // Non-admin users should not access admin pages, redirect to home
                response.sendRedirect("/home");
            } else {
                filterChain.doFilter(request, response);
            }
        }
//        if (session != null && session.getAttribute("user") != null) {
//            User user = (User) session.getAttribute("user");
//            // If user is an admin, allow access to admin URLs
//            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
//            if(isAdmin == null){
//                isAdmin = false;
//            }
//            if (isAdmin != null && isAdmin && path.startsWith("/admin")) {
//                filterChain.doFilter(request, response);
//            } else if (!isAdmin && !path.startsWith("/admin")) {
//                // If user is not an admin, allow access to non-admin URLs
//                response.sendRedirect(request.getContextPath() + "/home");
//            } else {
//                // Redirect to login if URL is not allowed
//                filterChain.doFilter(request, response);
//            }
//        } else {
//            // Redirect back to the login page if user is not logged in
////            response.sendRedirect("/login");
//            response.sendRedirect("/login");
//        }
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getRequestURI();
//        System.out.println("path = " + path);
//        return "/login".equals(path) || path.startsWith("/contact") || path.startsWith("/admin/login")|| path.startsWith("/resources/");
//    }
}
