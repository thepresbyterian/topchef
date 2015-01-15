package topchef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

import topchef.filters.CsrfTokenHeaderFilter;
import topchef.security.AuthenticationFailure;
import topchef.security.AuthenticationSuccess;
import topchef.security.EntryPointUnauthorizedHandler;
import topchef.services.UsersService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UsersService detailsService;

	@Autowired
	private AuthenticationFailure authFailure;
	
	@Autowired
	private AuthenticationSuccess authSuccess;
	
	@Autowired
	private EntryPointUnauthorizedHandler unauthorizedHandler;
	
	@Override
	public void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder
			.userDetailsService(detailsService)
			.passwordEncoder(bCryptPasswordEncoder());
	}
	
    @Bean 
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.addFilterAfter(new CsrfTokenHeaderFilter(), CsrfFilter.class)		
			.exceptionHandling()
			.authenticationEntryPoint(unauthorizedHandler)		
		.and()			
			.authorizeRequests()
				.antMatchers("/**").permitAll()
				.antMatchers("/images/**", "/fonts/**", "/css/**", "/js/**", "/jsp/**", "/views/**", "/login**").permitAll()
				.antMatchers("/mock/**").permitAll()
				.antMatchers("/api/**").authenticated()
				.antMatchers("/admin/**").hasRole("ADMIN")
		.and()
			.formLogin()
				.successHandler(authSuccess)
				.failureHandler(authFailure)
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/api/profile")			
				.usernameParameter("username")
				.passwordParameter("password")				
				.permitAll()
		.and()
			.logout()
				.deleteCookies()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll();
	}
}