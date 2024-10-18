//package com.stpp.movies.configurations;
//
//import com.stpp.movies.entities.User;
//import com.stpp.movies.enumerators.Role;
//import com.stpp.movies.enumerators.Status;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomSecurityExpression {
//
//  public boolean isAdminAndActive() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    if (authentication != null && authentication.getPrincipal() instanceof User) {
//      User user = (User) authentication.getPrincipal();
//      return user.getRole().equals(Role.ADMIN) && user.getStatus().equals(Status.ACTIVE);
//    }
//    return false;
//  }
//}
