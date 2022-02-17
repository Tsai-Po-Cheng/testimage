package tw.com.webcomm.fido.middleware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tw.com.webcomm.fido.middleware.service.QrcodeService;
import tw.com.webcomm.fido.model.oob.RequestQRCodeReq;
import tw.com.webcomm.fido.model.oob.RequestQRCodeRequestBody;
import tw.com.webcomm.fido.model.oob.RequestQRCodeResp;
import tw.com.webcomm.fido.model.oob.ValidateQRCodeReq;
import tw.com.webcomm.fido.model.oob.ValidateQRCodeResp;

@CrossOrigin(origins = "*")
@RestController
public class QrcodeController {

	@Value("${fido.app-id}")
	private String appId;

	@Autowired
	private QrcodeService svc;

	@PostMapping(value = "/oob/requestQrcode")
	// public RequestQRCodeResp requestQrcode(@RequestBody RequestQRCodeReq req) {
	public RequestQRCodeResp requestQrcode() {
		RequestQRCodeReq req = new RequestQRCodeReq();
		req.setBody(new RequestQRCodeRequestBody());
		req.getBody().setAppId(appId);
		return svc.requestQrcode(req);
	}

	@PostMapping(value = "/oob/validateQrcode")
	public ValidateQRCodeResp validateQrcode(@RequestBody ValidateQRCodeReq req) {
		req.getBody().setAppId(appId);
		return svc.validateQrcode(req);
	}

}
