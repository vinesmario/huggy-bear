package com.vinesmario.microservice.server.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * ResourceServerConfigurerAdapter用于保护oauth要开放的资源，
 * 同时主要作用于client端以及token的认证(Bearer auth)
 * <p>
 * 对于自动配置文件 ResourceServerTokenServicesConfiguration
 *
 * @see org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerTokenServicesConfiguration
 * 1，AuthorizationServerEndpointsConfiguration不存在时，TokenStore对象会自动创建。
 * 2，AuthorizationServerEndpointsConfiguration存在时，TokenStore对象需要手动处理。
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    private CorsFilter corsFilter;

    /**
     * Apply the token converter (and enhancer) for token store.
     *
     * @return the JwtTokenStore managing the tokens.
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * This bean generates an token enhancer, which manages the exchange between JWT acces tokens and Authentication
     * in both directions.
     *
     * @return an access token converter configured with the authorization server's public/private keys
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        // 1.对称加解密方式
        // 授权服务器加密和资源服务器解密，使用相同的密钥
        // 考虑到性能和更容易理解，使用对称加解密方式。
        accessTokenConverter.setSigningKey(resourceServerProperties.getJwt().getKeyValue());
        return accessTokenConverter;

        // 2.非对称加解密方式
        // ①授权服务器加密，读取本地存放的 jks 证书文件
        // 使用jdk自带的工具生成签名证书，生成公钥以及私钥。
        // keytool -genkeypair -alias alias_name -keyalg RSA -keypass key_pass_name -keystore D:/key_store_name.jks -storepass store_pass_name
        // keytool -list --keystore key_store_name.jks
        // 说明：
        // keytool工具是基于“证书库”来对密钥进行管理的。“证书库”是一个Java KeyStore（文件后缀 .keystore 或 .jks）证书文件。
        // 1). -genkeypair: 生成一对非对称密钥（公钥和私钥）记录（证书）。
        // 2). -alias: 本条密钥记录（证书）的别名。因一个证书库中可以存放多个证书, 所以可以通过别名标识密钥记录（证书）。
        // 3). -keyalg: 本条密钥记录（证书）的加密算法。可以选择的加密算法有: RSA、DSA、EC等
        // 4). -keypass: 本条密钥记录（证书）的私钥明文，可以与storepass相同。
        // 5). -keystore: 指定存储密钥记录（证书）的“证书库”的名称。
        // 6). -storepass: 指定存储密钥记录（证书）的“证书库”的密码, 可以与keypass相同。
//        KeyPair keyPair = new KeyStoreKeyFactory(
//                new ClassPathResource(resourceServerProperties.getJwt().getKeyStore()),
//                resourceServerProperties.getJwt().getKeyStorePassword().toCharArray())
//                // 如果key_pass_name和store_pass_name相同
//                .getKeyPair(resourceServerProperties.getJwt().getKeyAlias());
//                // 如果key_pass_name和store_pass_name不相同
//                .getKeyPair(resourceServerProperties.getJwt().getKeyAlias(), resourceServerProperties.getJwt().getKeyPassword().toCharArray());
//        accessTokenConverter.setKeyPair(keyPair);
//        return accessTokenConverter;
        // ②资源服务器解密，读取本地存放公钥文件，或者获取从鉴权服务器获取公钥
//        String verifierKey = null;
//        Resource resource = new ClassPathResource("public_key.txt");
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
//            verifierKey = br.lines().collect(Collectors.joining("\n"));
//        } catch (IOException ioe) {
//            verifierKey = oAuth2TokenKeyEndpointClient.getPublicKey();
//        }
//        accessTokenConverter.setVerifierKey(verifierKey);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 如果是在authorization server，
        // 配置文件中的security.oauth2.resource.id参数不起作用
        // 源码中默认resourceId为oauth2-resource，见ResourceServerSecurityConfigurer.class
        // 需重写原方法
        // 其他资源服务器配置可生效，不需重写。
        resources.resourceId(resourceServerProperties.getResourceId());

        // ①默认使用org.springframework.security.oauth2.provider.token.DefaultTokenServices.class处理token，
        // 只做格式和简单校验，如有效期，不验证数据一致性
        // ②如果想要调用token-info-uri的地址对token进一步校验，可通过tokenServices(ResourceServerTokenServices tokenServices)方法，
        // 传入org.springframework.security.oauth2.provider.token.RemoteTokenServices.class对象处理token；
        // ③如果想要调用user-info-uri的地址对token进一步校验，可通过tokenServices(ResourceServerTokenServices tokenServices)方法，
        // 传入org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices.class对象处理token；
        // ④如果想要调用Github等第三方对token进一步校验，可通过tokenServices(ResourceServerTokenServices tokenServices)方法，
        // 传入org.springframework.boot.autoconfigure.security.oauth2.resource.SpringSocialTokenServices.class对象处理token。
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/api/register").permitAll()
//                .antMatchers("/api/activate").permitAll()
//                .antMatchers("/api/authenticate").permitAll()
//                .antMatchers("/api/account/reset-password/init").permitAll()
//                .antMatchers("/api/account/reset-password/finish").permitAll()
//                .antMatchers("/api/profile-info").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/management/health").permitAll()
                .antMatchers("/management/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/configuration/ui").permitAll()
                .antMatchers("/swagger-ui/index.html").permitAll();
    }

}
