package spring.oauth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.psh.beans.CardInfoRequestBean;
import com.bank.psh.beans.CardURNInfoResponseBean;
import com.bank.psh.beans.TokenInfoResponseBean;
import com.bank.psh.beans.URNRequestBean;
import com.bank.psh.beans.URNResponseBean;
import com.bank.psh.data.model.PaymentToken;
import com.bank.psh.service.PaymentHubService;
import com.bank.psh.service.utils.PshUtil;

@EnableAutoConfiguration
@RestController
@Configuration
@ComponentScan(basePackages = { "spring", "com.bank", "com.provider" })
@ImportResource({ "classpath:root-context.xml" })
@SpringBootApplication
public class OauthConfiguration extends SpringBootServletInitializer {

	private static Logger logger = Logger.getLogger(OauthConfiguration.class);

	/*
	 * public static void main(String[] args) {
	 * SpringApplication.run(OauthConfiguration.class, args); }
	 */public OauthConfiguration() {

	}

	private PaymentHubService paymentHubService;

	/**
	 * @param paymentHubService
	 *            the paymentHubService to set
	 */
	@Autowired
	public void setPaymentHubService(PaymentHubService paymentHubService) {
		this.paymentHubService = paymentHubService;
	}

	@RequestMapping(value = "/paymenthub/tokenlist", method = RequestMethod.GET)
	public @ResponseBody List<CardURNInfoResponseBean> getTokenList(
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		SecurityContext securityContext = (SecurityContext) session
				.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authentication = (Authentication) securityContext
				.getAuthentication();

		logger.info("Card & URN requested for the user "
				+ authentication.getName());

		List<CardURNInfoResponseBean> beanList = paymentHubService.getCardURNInfo(authentication.getName());
		return beanList;
	}

	@RequestMapping(value = "/paymenthub/tokens", method = RequestMethod.POST)
	public @ResponseBody TokenInfoResponseBean getToken(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody URNRequestBean urnRequest) {

		logger.info("Token requested for URN " + urnRequest.getUrn());
		PaymentToken pmTkn = getToken(urnRequest.getUrn());
		String crypto = PshUtil.generateCryptogram(
				urnRequest.getTransactionDetails(), pmTkn);
		TokenInfoResponseBean token = new TokenInfoResponseBean();
		token.setToken(pmTkn.getToken());
		token.setCryto(crypto);
		return token;
	}

	@RequestMapping(value = "/paymenthub/getpaytoken", method = RequestMethod.POST)
	public @ResponseBody TokenInfoResponseBean generatePaymentToken(
			HttpServletRequest request, HttpServletResponse response) {

		logger.info("Payment Token requested");
		HttpSession session = request.getSession();
		String urn = (String)session.getAttribute("USER_URN");
		logger.info("URN from Session: " + urn);
		PaymentToken pmTkn = getToken(urn);
		String crypto = PshUtil.generateCryptogram(
				null, pmTkn);
		TokenInfoResponseBean token = new TokenInfoResponseBean();
		token.setToken(pmTkn.getToken());
		token.setCryto(crypto);
		return token;
	}

	@RequestMapping(value = "/paymenthub/invalidate", method = RequestMethod.POST)
	public @ResponseBody void invalidate(HttpServletRequest request,
			HttpServletResponse response) {

		request.getSession().invalidate();
		logger.info("Session invalidated...");
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {

		return application.sources(OauthConfiguration.class);
	}

	@Configuration
	@EnableResourceServer
	protected static class ResourceServer extends
			ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.requestMatchers()
					.antMatchers("/paymenthub/**", "/paymenthub**").and()
					.authorizeRequests().anyRequest()
					.access("#oauth2.hasScope('read')");
			// @formatter:on
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources)
				throws Exception {
			resources.resourceId("PaymentServiceHub");
		}

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends
			AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private DataSource dataSource;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {
			// @formatter:off

			clients.jdbc(dataSource);
//			JdbcClientDetailsServiceBuilder db = clients.init(builder);;
//			clientDetailsService = db.dataSource(dataSource).build();
			
		}

	}
	
	protected static class Stuff {

		@Autowired
		private ClientDetailsService clientDetailsService;

		@Autowired
		private TokenStore tokenStore;

		@Bean
		public ApprovalStore approvalStore() throws Exception {
			TokenApprovalStore store = new TokenApprovalStore();
			store.setTokenStore(tokenStore);
			return store;
		}

		@Bean
		@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
		public SparklrUserApprovalHandler userApprovalHandler() throws Exception {
			SparklrUserApprovalHandler handler = new SparklrUserApprovalHandler();
			handler.setApprovalStore(approvalStore());
			handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
			handler.setClientDetailsService(clientDetailsService);
			handler.setUseApprovalStore(true);
			return handler;
		}
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

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public PaymentToken getToken(String urn) {
		String query = "SELECT TOKEN.TOKEN TOKEN, TOKEN.EXP_DATE EXP_DATE FROM CUST_URN_TOKEN URN, CUST_PYMT_TOKEN TOKEN "
				+ "WHERE URN.URN = ? AND URN.TOKEN_ID = TOKEN.ID";
		PaymentToken paymentToken = null;

		Object[] args = new Object[] { urn };

		SqlRowSet srs = jdbcTemplate.queryForRowSet(query, args);

		if (srs != null) {
			paymentToken = new PaymentToken();
			while (srs.next()) {
				paymentToken.setToken(srs.getString("TOKEN"));
				// paymentToken.setExpDate(srs.getString("EXP_DATE"));
			}
		}

		return paymentToken;
	}
}