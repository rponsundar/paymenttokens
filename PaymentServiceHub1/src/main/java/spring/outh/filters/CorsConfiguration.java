package spring.outh.filters;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(-1)
@EnableWebSecurity
public class CorsConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public AuthenticationSecurity authenticationSecurity() {
		return new AuthenticationSecurity();
	}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

		@Override
		public void configure(WebSecurity webSecurity) throws Exception {
			webSecurity.ignoring().antMatchers("/pages/**");
			webSecurity.ignoring().antMatchers("/psh/**");
			webSecurity.ignoring().antMatchers("/index**");
			webSecurity.ignoring().antMatchers("/index/**");
			webSecurity.ignoring().antMatchers("/views/**");
			//webSecurity.ignoring().antMatchers("/oauth/**");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//http.authorizeRequests().anyRequest().authenticated();

//			http.anonymous().and().authorizeRequests()
//					.antMatchers("/oauth/**").permitAll();

//			http
//			// Since we want the protected resources to be accessible in the UI as well we need 
//			// session creation to be allowed (it's disabled by default in 2.0.6)
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//				.and()
//			.requestMatchers().antMatchers("/oauth/**").and().authorizeRequests().antMatchers("/oauth/**").access("none");

			//			http.formLogin().loginPage("/index.html").permitAll().and()
//					.logout().permitAll().logoutSuccessUrl("/index.html");

            http
       .authorizeRequests()
           .antMatchers("/BankLogin.jsp").permitAll()
           .anyRequest().hasRole("USER")
           .and()
       .exceptionHandling()
           .accessDeniedPage("/BankLogin.jsp?authorization_error=true")
           .and()
       // TODO: put CSRF protection back into this endpoint
       .csrf()
           .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
           .disable()
       .logout()
       	.logoutUrl("/logout")
           .logoutSuccessUrl("/BankLogin.jsp")
           .and()
       .formLogin()
       .loginPage("/BankLogin.jsp")
       	.loginProcessingUrl("/BankLogin.jsp")
           .failureUrl("/BankLogin.jsp?authentication_error=true")
           .loginPage("/BankLogin.jsp");

			
		}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration
	protected static class AuthenticationSecurity extends
			GlobalAuthenticationConfigurerAdapter {

		private DataSource dataSource;

		@Autowired
		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off

			auth.jdbcAuthentication()
					.dataSource(dataSource)
					.usersByUsernameQuery(
							"select username,password, enabled from users where username=?")
					.authoritiesByUsernameQuery(
							"select username, role from user_roles where username=?");

			// auth.inMemoryAuthentication().withUser("user").password("password")
			// .roles("ADMIN", "USER");
			// @formatter:on
		}

	}

}