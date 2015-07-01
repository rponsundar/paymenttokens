package demo;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.provider.paymenttoken.beans.ErrorInfo;
import com.provider.paymenttoken.beans.PaymentTokenRequestBean;
import com.provider.paymenttoken.beans.PaymentTokenResponseBean;
import com.provider.paymenttoken.exception.PTResponseErrorHandler;

public class RestClientTest {

	public void testRest() {
		RestTemplate templ = new RestTemplate();
		templ.setErrorHandler(new PTResponseErrorHandler());
		templ.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		templ.getMessageConverters().add(new StringHttpMessageConverter());
		PaymentTokenRequestBean req = new PaymentTokenRequestBean();
		//req.setTokenRequestorId(123);
		req.setPanNo("1234567891123456");
		// req.setPanExpiryDate("2010");

		try {
			ResponseEntity<PaymentTokenResponseBean> resp = templ
					.postForEntity(
							"http://localhost:8083/tokenprovider/payment/token",
							req, PaymentTokenResponseBean.class);

			// System.out.println(resp.getBody().getPaymentToken());

		} catch (Exception err) {
			System.out.println("error occured");
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new RestClientTest().testRest();

	}

}
