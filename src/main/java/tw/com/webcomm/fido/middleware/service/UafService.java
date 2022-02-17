package tw.com.webcomm.fido.middleware.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UafService {

	private static final String REQUEST_REG = "/rest/requestReg";
	private static final String DO_REG = "/rest/doReg";
	private static final String REQUEST_AUTH = "/rest/requestAuth";
	private static final String DO_AUTH = "/rest/doAuth";
	private static final String FACETS = "/rest/facets";
	private static final String REQUEST_PAIR_AUTH = "/rest/requestPairAuth";
	private static final String DO_DEREG = "/rest/doDereg";

	@Value("${fido.server.url}")
	private String fidoServerUrl;

	@Autowired
	private RestTemplate restTemplate;

	public String facets(String appId) {
		String url = new StringBuilder(fidoServerUrl).append(FACETS).append("?appID=").append(appId).toString();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(null, requestHeaders);

		ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

		return res.getBody();
	}

	public String requestReg(String req) {
		String url = new StringBuilder(fidoServerUrl).append(REQUEST_REG).toString();
		return commonRedirectPost(url, req);
	}

	public String doReg(String req) {
		String url = new StringBuilder(fidoServerUrl).append(DO_REG).toString();
		return commonRedirectPost(url, req);
	}

	public String requestAuth(String req) {
		String url = new StringBuilder(fidoServerUrl).append(REQUEST_AUTH).toString();
		return commonRedirectPost(url, req);
	}

	public String doAuth(String req) {
		String url = new StringBuilder(fidoServerUrl).append(DO_AUTH).toString();
		return commonRedirectPost(url, req);
	}
	
	public String requestPairAuth(String req) {
		String url = new StringBuilder(fidoServerUrl).append(REQUEST_PAIR_AUTH).toString();
		return commonRedirectPost(url, req);
	}
	
	public String doDereg(String req) {
		String url = new StringBuilder(fidoServerUrl).append(DO_DEREG).toString();
		return commonRedirectPost(url, req);
	}

	public String commonRedirectPost(String url, String req) {
		// String url = new StringBuilder(FIDO_SERVER_URL).append(XXXXX).toString();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>(req, requestHeaders);

		ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

		return res.getBody();
	}

}
