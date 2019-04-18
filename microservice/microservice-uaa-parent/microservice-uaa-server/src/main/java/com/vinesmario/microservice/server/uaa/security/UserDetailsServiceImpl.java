package com.vinesmario.microservice.server.uaa.security;

import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.server.common.constant.DictConstant;
import com.vinesmario.microservice.server.uaa.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
        log.debug("Authenticating {}", login);

        if (new EmailValidator().isValid(login, null)) {
            Optional<UserAccountDto> userByEmailFromDatabase = userAccountService.getWithAuthoritiesByEmail(login);
            return userByEmailFromDatabase.map(userAccountDto -> createSpringSecurityUser(login, userAccountDto))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }

//        if (new MobileValidator().isValid(login, null)) {
//            Optional<UserAccountDto> userByEmailFromDatabase = userAccountService.getWithAuthoritiesByMobile(login);
//            return userByEmailFromDatabase.map(userAccountDto -> createSpringSecurityUser(login, userAccountDto))
//                    .orElseThrow(() -> new UsernameNotFoundException("User with mobile " + login + " was not found in the database"));
//        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<UserAccountDto> userByLoginFromDatabase = userAccountService.getWithAuthoritiesByUsername(lowercaseLogin);
        return userByLoginFromDatabase.map(user -> createSpringSecurityUser(lowercaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    /**
     * 生成当前登录用户UserDetails对象，放入token
     * 可根据需要 implements UserDetails，定制需要的属性
     *
     * @param lowercaseLogin
     * @param userAccountDto
     * @return
     */
    private User createSpringSecurityUser(String lowercaseLogin, UserAccountDto userAccountDto) {
        if (DictConstant.BYTE_YES_NO_N == userAccountDto.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = userAccountDto.getRoleDtoList().stream()
                .map(roleDto -> new SimpleGrantedAuthority(roleDto.getEnName()))
                .collect(Collectors.toList());
        return new User(userAccountDto.getUsername(),
                userAccountDto.getPassword(),
                grantedAuthorities);
    }
}
