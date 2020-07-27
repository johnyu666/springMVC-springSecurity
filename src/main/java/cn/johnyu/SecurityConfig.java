package cn.johnyu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder delegatingCreate(){
        PasswordEncoder encoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }

    /**
     *用来修改过滤器链
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/js/**");
    }

    /**
     *配置UserDetailService,完成"鉴权",完成认证，为用户分配角色
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder=delegatingCreate();
       auth.inMemoryAuthentication().passwordEncoder(encoder)
               .withUser("john").password(encoder.encode("123")).roles("ADMIN");

        auth.inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("tom").password(encoder.encode("234")).roles("GUEST");

//        auth.jdbcAuthentication().dataSource(dataSource);
//                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
    }
    /**
     * 修改拦截器，保护请求，完成基于角色的授权（RBAC）
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                .antMatchers("/suc")
                .hasAuthority("ROLE_ADMIN")
                .antMatchers("/other")
                .hasAnyRole("GUEST","READER")
                .anyRequest().permitAll()
                .and()
                .formLogin()

                .and()
                .exceptionHandling()
                .accessDeniedPage("/forbidden")
                .and()
                .logout()
                .logoutUrl("/logout");
    }
}
