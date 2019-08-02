package com.vinesmario.microservice.server.security.model;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.uaa.dto.UserAccountDTO;
import com.vinesmario.microservice.server.security.exception.UserNotActivatedException;
import com.vinesmario.microservice.server.security.exception.UserNotAssignedRolesException;
import com.vinesmario.microservice.server.uaa.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.info("Authenticating {}", login);
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("username")
                .password("password")// 明文
                .roles("ADMIN")
                .build();
        return userDetails;
//        if (new EmailValidator().isValid(login, null)) {
//            Optional<UserAccountDTO> userByEmailFromDatabase = userAccountService.getWithAuthoritiesByEmail(login);
//            return userByEmailFromDatabase.map(userAccountDTO -> createSpringSecurityUser(login, userAccountDTO))
//                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
//        }
//
//        if (new MobileValidator().isValid(login, null)) {
//            Optional<UserAccountDTO> userByEmailFromDatabase = userAccountService.getWithAuthoritiesByMobile(login);
//            return userByEmailFromDatabase.map(userAccountDTO -> createSpringSecurityUser(login, userAccountDTO))
//                    .orElseThrow(() -> new UsernameNotFoundException("User with mobile " + login + " was not found in the database"));
//        }
//
//        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
//        Optional<UserAccountDTO> userByLoginFromDatabase = userAccountService.getWithAuthoritiesByUsername(lowercaseLogin);
//        return userByLoginFromDatabase.map(user -> createSpringSecurityUser(lowercaseLogin, user))
//                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    /**
     * 生成当前登录用户UserDetails对象，放入token
     * 可根据需要 implements UserDetails，定制需要的属性
     *
     * @param lowercaseLogin
     * @param userAccountDTO
     * @return
     */
    private SecurityUser createSpringSecurityUser(String lowercaseLogin, UserAccountDTO userAccountDTO) {
        if (DictConstant.BYTE_YES_NO_N == userAccountDTO.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        if (CollectionUtils.isEmpty(userAccountDTO.getAuthorityList())) {
            throw new UserNotAssignedRolesException("User " + lowercaseLogin + " was not assigned roles");
        }
        List<GrantedAuthority> grantedAuthorities = userAccountDTO.getAuthorityList().stream()
                .map(authorityDTO -> new SecurityAuthority(authorityDTO.getRole()))
                .collect(Collectors.toList());
        SecurityUser securityUser = new SecurityUser(userAccountDTO.getUsername(),
                "{bcrypt}" + userAccountDTO.getPassword(),// bcrypt加密后的密文
                grantedAuthorities);
        return securityUser;
    }
}
