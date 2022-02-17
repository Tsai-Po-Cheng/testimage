/**
 * 
 */
$(function() {
	document.getElementById("register").addEventListener("submit", function(event) {
		event.preventDefault();

		var username = this.username.value;
		var displayName = this.displayName.value;

		var fido2RequestRegStr =
		{
			"header": {
				"appVersion": "v1",
				"channelCode": "hi.fido2.poc",
				"deviceBrand": "google",
				"deviceType": "chrome",
				"deviceUuid": "xxx",
				"deviceVersion": "92.0.4515.107",
				"userIp": "localhost"
			},
			"body": {
				"attestation": "none",
				"authenticatorSelection": [{
					"authenticatorAttachment": "platform"
				}],
				"displayName": "",
				"friendlyName": "",
				"origin": "https://hi-client-poc-hi-poc.apps.oc.webcomm.com.tw",
				"rpId": "apps.oc.webcomm.com.tw",
				"rpName": "Hi Fido2",
				"username": ""
			}
		};
		fido2RequestRegStr.body.username = username;
		fido2RequestRegStr.body.displayName = displayName;
		fido2RequestRegStr.body.friendlyName = "";
		console.log("fido2RequestRegStr: ", fido2RequestRegStr);

		$.ajax({
			type: "POST",
			data: JSON.stringify(fido2RequestRegStr),
			dataType: "json",
			contentType: "application/json",
			url: "/requestReg",
			success: function(jsondata) {
				if (jsondata.body) {
					console.log("fido2RequestRegResp" + JSON.stringify(jsondata.body));

					var makeCredReq = preformatMakeCredReq(jsondata.body);
					console.log(makeCredReq);

					navigator.credentials.create({ "publicKey": makeCredReq }).then((makeCredChallenge) => {
						console.log(makeCredChallenge);

						var newCredentialInfo = publicKeyCredentialToJSON(makeCredChallenge);
						var fido2DoRegReq = {
							"header": {
								"appVersion": "v1",
								"channelCode": "hi.fido2.poc",
								"deviceBrand": "google",
								"deviceType": "chrome",
								"deviceUuid": "xxx",
								"deviceVersion": "92.0.4515.107",
								"userIp": "localhost"
							},
							"body": {}
						};
						fido2DoRegReq.body = { "regPublicKeyCredential": newCredentialInfo };
						console.log("fido2DoRegReq: ", fido2DoRegReq);

						$.ajax({
							type: "POST",
							data: JSON.stringify(fido2DoRegReq),
							dataType: "json",
							contentType: "application/json",
							url: "/doReg",
							success: function(jsondata) {
								if (jsondata) {
									console.log("fido2DoRegReqResp" + JSON.stringify(jsondata));

									var userObj = { username: self.username, displayName: self.displayName, hasRegistered: true };
									localStorage.setItem("userInfo", JSON.stringify(userObj));

									alert("註冊成功");
								}
								else {
									alert("註冊錯誤");
								}
							},
							error: function() {
								alert("註冊錯誤");
							}
						});
					});
				}
			},
			error: function() {
				alert("註冊錯誤");
			}
		});
	})

	document.getElementById("login").addEventListener("submit", function(event) {
		event.preventDefault();

		// do fido2 auth
		var fido2RequestAuthStr = {
			"header": {
				"appVersion": "v1",
				"channelCode": "hi.fido2.poc",
				"deviceBrand": "google",
				"deviceType": "chrome",
				"deviceUuid": "xxx",
				"deviceVersion": "92.0.4515.107",
				"userIp": "localhost"
			},
			"body": {
				"username": "",
				"rpId": "apps.oc.webcomm.com.tw",
				"origin": "https://hi-client-poc-hi-poc.apps.oc.webcomm.com.tw"
			}
		}

		var username = this.username.value;
		fido2RequestAuthStr.body.username = username;
		console.log("fido2RequestAuthStr: ", fido2RequestAuthStr);

		$.ajax({
			type: "POST",
			data: JSON.stringify(fido2RequestAuthStr),
			dataType: "json",
			contentType: "application/json",
			url: "/requestAuth",
			success: function(jsondata) {
				if (jsondata.body) {
					console.log("fido2RequestAuthResp" + JSON.stringify(jsondata.body));

					var getAssertReq = preformatGetAssertReq(jsondata.body);

					navigator.credentials.get({ "publicKey": getAssertReq }).then((newCredentialInfo) => {
						console.log(newCredentialInfo);

						newCredentialInfo = publicKeyCredentialToJSON(newCredentialInfo);
						var fido2DoAuthReq = {
							"header": {
								"appVersion": "v1",
								"channelCode": "hi.fido2.poc",
								"deviceBrand": "google",
								"deviceType": "chrome",
								"deviceUuid": "xxx",
								"deviceVersion": "92.0.4515.107",
								"userIp": "localhost"
							},
							"body": {}
						};
						fido2DoAuthReq.body = { "publicKeyCredential": newCredentialInfo };
						console.log("fido2DoAuthReq: ", fido2DoAuthReq);

						$.ajax({
							type: "POST",
							data: JSON.stringify(fido2DoAuthReq),
							dataType: "json",
							contentType: "application/json",
							url: "/doAuth",
							success: function(jsondata) {
								if (jsondata) {
									console.log("fido2DoAuthResp" + JSON.stringify(jsondata));
									
									alert("登入成功");
								}
								else {
									alert("登入錯誤");
								}
							},
							error: function() {
								alert("登入錯誤");
							}
						});
					});
				}
				else {
					alert("登入錯誤");
				}
			},
			error: function() {
				alert("登入錯誤");
			}
		});
	})
});