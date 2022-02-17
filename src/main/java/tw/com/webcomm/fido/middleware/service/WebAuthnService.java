package tw.com.webcomm.fido.middleware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tw.com.webcomm.fido.model.webauthn.request.Fido2DoAuthReq;
import tw.com.webcomm.fido.model.webauthn.request.Fido2DoRegReq;
import tw.com.webcomm.fido.model.webauthn.request.Fido2RequestAuthReq;
import tw.com.webcomm.fido.model.webauthn.request.Fido2RequestRegReq;
import tw.com.webcomm.fido.model.webauthn.response.Fido2DoAuthResp;
import tw.com.webcomm.fido.model.webauthn.response.Fido2DoRegResp;
import tw.com.webcomm.fido.model.webauthn.response.Fido2RequestAuthResp;
import tw.com.webcomm.fido.model.webauthn.response.Fido2RequestRegResp;

@Service
public class WebAuthnService {

	private static final String REQUEST_REG = "/rest/fido2/requestReg";
	private static final String DO_REG = "/rest/fido2/doReg";
	private static final String REQUEST_AUTH = "/rest/fido2/requestAuth";
	private static final String DO_AUTH = "/rest/fido2/doAuth";

	@Value("${fido.server.url}")
	private String fidoServerUrl;

	@Autowired
	private RestTemplate restTemplate;

	public Fido2RequestRegResp requestReg(Fido2RequestRegReq req) {

		String url = new StringBuilder(fidoServerUrl).append(REQUEST_REG).toString();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Fido2RequestRegReq> httpEntity = new HttpEntity<>(req, requestHeaders);

		ResponseEntity<Fido2RequestRegResp> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
				Fido2RequestRegResp.class);

		return res.getBody();
	}

	public Fido2DoRegResp doReg(Fido2DoRegReq req) {

		String url = new StringBuilder(fidoServerUrl).append(DO_REG).toString();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Fido2DoRegReq> httpEntity = new HttpEntity<>(req, requestHeaders);

		ResponseEntity<Fido2DoRegResp> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
				Fido2DoRegResp.class);

		return res.getBody();
	}

	public Fido2RequestAuthResp requestAuth(Fido2RequestAuthReq req) {
		String url = new StringBuilder(fidoServerUrl).append(REQUEST_AUTH).toString();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Fido2RequestAuthReq> httpEntity = new HttpEntity<>(req, requestHeaders);

		ResponseEntity<Fido2RequestAuthResp> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
				Fido2RequestAuthResp.class);

		return res.getBody();
	}

	public Fido2DoAuthResp doAuth(Fido2DoAuthReq req) {
		String url = new StringBuilder(fidoServerUrl).append(DO_AUTH).toString();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Fido2DoAuthReq> httpEntity = new HttpEntity<>(req, requestHeaders);

		ResponseEntity<Fido2DoAuthResp> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
				Fido2DoAuthResp.class);

		return res.getBody();
	}

}
