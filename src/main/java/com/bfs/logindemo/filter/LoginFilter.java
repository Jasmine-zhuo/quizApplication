//package com.bfs.logindemo.filter;
//
//import com.bfs.logindemo.domain.User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter("/*")
//@Component
//public class LoginFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        System.out.println("In LoginFilter");
//        HttpSession session = request.getSession(false);
//        String path = request.getRequestURI();
//        System.out.println("path = " + path);
//
//        // Allow access to these URLs without authentication
//        if ("/login".equals(path) || path.startsWith("/contact") || "/register".equals(path) || path.startsWith("/admin/login") || path.startsWith("/resources/")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if (session != null && session.getAttribute("user") != null) {
//            User user = (User) session.getAttribute("user");
//            // If user is an admin, allow access to admin URLs
//            Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
//            if (user.isAdmin() && path.startsWith("/admin")) {
//                filterChain.doFilter(request, response);
//            } else if (!user.isAdmin() && !path.startsWith("/admin")) {
//                // If user is not an admin, allow access to non-admin URLs
//                filterChain.doFilter(request, response);
//            } else {
//                // Redirect to login if URL is not allowed
//                response.sendRedirect("/login");
//            }
//        } else {
//            // Redirect back to the login page if user is not logged in
//            response.sendRedirect("/login");
//        }
//    }
//
////    @Override
////    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
////        String path = request.getRequestURI();
////        System.out.println("path = " + path);
////        return "/login".equals(path) || path.startsWith("/contact") || path.startsWith("/admin/login")|| path.startsWith("/resources/");
////    }
//}
