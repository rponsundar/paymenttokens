package spring.oauth.web.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bank.psh.controller.impl.AccessConfirmationController;

@Configuration
public class OauthWebMVCConfig  extends WebMvcConfigurerAdapter {

	@Bean
	public AccessConfirmationController accessConfirmationController(ClientDetailsService clientDetailsService,
			ApprovalStore approvalStore) {
		AccessConfirmationController accessConfirmationController = new AccessConfirmationController();
		return accessConfirmationController;
	}

}
