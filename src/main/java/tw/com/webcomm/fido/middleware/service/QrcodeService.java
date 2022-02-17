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

import tw.com.webcomm.fido.model.oob.RequestQRCodeReq;
import tw.com.webcomm.fido.model.oob.RequestQRCodeResp;
import tw.com.webcomm.fido.model.oob.ValidateQRCodeReq;
import tw.com.webcomm.fido.model.oob.ValidateQRCodeResp;

@Service
public class QrcodeService {

	private static final String REQUEST_QRCODE = "/rest/oob/requestQRCode";
	private static final String VALIDATE_QRCODE = "/rest/oob/validateQRCode";

	@Value("${fido.server.url}")
	private String fidoServerUrl;

	@Autowired
	private RestTemplate restTemplate;

	public RequestQRCodeResp requestQrcode(RequestQRCodeReq req) {

		String url = new StringBuilder(fidoServerUrl).append(REQUEST_QRCODE).toString();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RequestQRCodeReq> httpEntity = new HttpEntity<>(req, requestHeaders);

		ResponseEntity<RequestQRCodeResp> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
				RequestQRCodeResp.class);

		return res.getBody();
	}

	public ValidateQRCodeResp validateQrcode(ValidateQRCodeReq req) {

		String url = new StringBuilder(fidoServerUrl).append(VALIDATE_QRCODE).toString();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ValidateQRCodeReq> httpEntity = new HttpEntity<>(req, requestHeaders);

		ResponseEntity<ValidateQRCodeResp> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
				ValidateQRCodeResp.class);

		return res.getBody();
	}
}
