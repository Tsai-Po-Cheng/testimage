package tw.com.webcomm.fido.middleware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tw.com.webcomm.fido.middleware.service.WebAuthnService;
import tw.com.webcomm.fido.model.webauthn.request.Fido2DoAuthReq;
import tw.com.webcomm.fido.model.webauthn.request.Fido2DoRegReq;
import tw.com.webcomm.fido.model.webauthn.request.Fido2RequestAuthReq;
import tw.com.webcomm.fido.model.webauthn.request.Fido2RequestRegReq;
import tw.com.webcomm.fido.model.webauthn.response.Fido2DoAuthResp;
import tw.com.webcomm.fido.model.webauthn.response.Fido2DoRegResp;
import tw.com.webcomm.fido.model.webauthn.response.Fido2RequestAuthResp;
import tw.com.webcomm.fido.model.webauthn.response.Fido2RequestRegResp;

@RestController
public class WebAuthnController {

	@Autowired
	private WebAuthnService svc;

	@PostMapping(value = "/webauthn/requestReg")
	public Fido2RequestRegResp requestReg(@RequestBody Fido2RequestRegReq req) {
		return svc.requestReg(req);
	}

	@PostMapping(value = "/webauthn/doReg")
	public Fido2DoRegResp doReg(@RequestBody Fido2DoRegReq req) {
		return svc.doReg(req);
	}

	@PostMapping(value = "/webauthn/requestAuth")
	public Fido2RequestAuthResp requestAuth(@RequestBody Fido2RequestAuthReq req) {
		return svc.requestAuth(req);
	}

	@PostMapping(value = "/webauthn/doAuth")
	public Fido2DoAuthResp doAuth(@RequestBody Fido2DoAuthReq req) {
		return svc.doAuth(req);
	}

}
