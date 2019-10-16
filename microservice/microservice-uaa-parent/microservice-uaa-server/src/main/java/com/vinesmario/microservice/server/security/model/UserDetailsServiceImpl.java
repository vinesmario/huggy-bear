package com.vinesmario.microservice.server.security.model;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.security.model.SecurityGrantedAuthority;
import com.vinesmario.microservice.client.uaa.dto.OauthUserDTO;
import com.vinesmario.microservice.server.security.exception.UserNotActivatedException;
import com.vinesmario.microservice.server.security.exception.UserNotAssignedRolesException;
import com.vinesmario.microservice.server.uaa.service.OauthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private OauthUserService oauthUserService;

    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
        return loadUserByUsername(authentication.getName());
    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException{
        log.info("Authenticating {}", login);
        log.info("ClientId {}", SecurityHelper.getLocalClientId());
        SecurityHelper.clearClientId();
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("username")
                .password("password")// 明文
                .roles("ADMIN")
                .build();
        return userDetails;
        // TODO 根据clientId查询OauthClientDetail表获取authorities，同时判断是B端用户前端还是C端用户前端
        // TODO 如果是B端用户，根据login查询OauthUser、Appointment、PositionAuthority获取authorities，取两者的交集。
        // TODO 如果是C端用户，根据login查询OauthUser，Authorities采用OauthClientDetail的数据。
//        if (new EmailValidator().isValid(login, null)) {
//            Optional<OauthUserDTO> userByEmailFromDatabase = oauthUserService.getWithAuthoritiesByEmail(login);
//            return userByEmailFromDatabase.map(oauthUserDTO -> createUserDetails(login, oauthUserDTO))
//                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
//        }
//
//        if (new MobileValidator().isValid(login, null)) {
//            Optional<OauthUserDTO> userByEmailFromDatabase = oauthUserService.getWithAuthoritiesByMobile(login);
//            return userByEmailFromDatabase.map(oauthUserDTO -> createUserDetails(login, oauthUserDTO))
//                    .orElseThrow(() -> new UsernameNotFoundException("User with mobile " + login + " was not found in the database"));
//        }
//
//        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
//        Optional<OauthUserDTO> userByLoginFromDatabase = oauthUserService.getWithAuthoritiesByUsername(lowercaseLogin);
//        return userByLoginFromDatabase.map(user -> createUserDetails(lowercaseLogin, user))
//                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    /**
     * 生成当前登录用户UserDetails对象，放入token
     * 可根据需要 implements UserDetails，定制需要的属性
     *
     * @param lowercaseLogin
     * @param oauthUserDTO
     * @return
     */
    private UserDetails createUserDetails(String lowercaseLogin, OauthUserDTO oauthUserDTO) {
        if (DictConstant.BYTE_YES_NO_N == oauthUserDTO.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        if (CollectionUtils.isEmpty(oauthUserDTO.getAuthorityList())) {
            throw new UserNotAssignedRolesException("User " + lowercaseLogin + " was not assigned roles");
        }
        List<GrantedAuthority> grantedAuthorities = oauthUserDTO.getAuthorityList().stream()
                .map(authorityDTO -> new SecurityGrantedAuthority(authorityDTO.getName()))
                .collect(Collectors.toList());
        User userDetails = new User(oauthUserDTO.getUsername(),
                "{bcrypt}" + oauthUserDTO.getPassword(),// bcrypt加密后的密文
                grantedAuthorities);
        return userDetails;
    }
}
