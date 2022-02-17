function getFidoHeader() {
    return {
        "appVersion": "v8",
        "channelCode": "1234",
        "deviceBrand": "google",
        "deviceType": "chrome",
        "deviceUuid": "xja78sdiw98s76",
        "deviceVersion": "92.0.4515.107",
        "userIp": "127.0.0.1"
    };
}

function requestReg(username, displayName) {
    console.log("=== Start request register ===")
    //var username = document.getElementById("registerUsername").value;
    console.log("Register User username: " + username);
    console.log("Register User displayName: " + displayName);

    if (username == undefined || username == "") {
        console.log("註冊失敗: 請輸入 username")
        return;
    }

    if (displayName == undefined || displayName == "") {
        console.log("註冊失敗: 請輸入 displayName")
        return;
    }

    var fido2RequestRegReq = {
        "header": getFidoHeader(),
        "body": {
            "attestation": "none",
            "authenticatorSelection": {
                "authenticatorAttachment": "platform",
                "requireResidentKey": false,
                "userVerification": "preferred"
            },
            "displayName": displayName,
            // "extensions": {}, //加了就會死 1498
            "friendlyName": "",
            // "origin": "https://fidolab.apps.oc.webcomm.com.tw/", //加了斜線就會錯 /
            "origin": "https://fidolab.apps.oc.webcomm.com.tw",
            "rpId": "fidolab.apps.oc.webcomm.com.tw",
            // "origin": "http://localhost:8080",
            // "rpId": "localhost",
            "rpName": "Fido Lab Relying Party",
            "username": username
        }
    }

    $.ajax({
        type: "POST",
        data: JSON.stringify(fido2RequestRegReq),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: "/webauthn/requestReg",
        success: function (fido2RequestRegResp) {
            console.log("Fido 2 Request Req Resp:");
            console.log(fido2RequestRegResp);
            if (fido2RequestRegResp.header.code == 1200) {
                doReg(fido2RequestRegResp);
            } else {
                console.log("註冊失敗");
            }
        },
        error: function () {
            console.log("註冊失敗");
        }
    })

}

function doReg(fido2RequestRegResp) {

    console.log("=== Start do register ===")

    console.log(fido2RequestRegResp.body);

    //fido2RequestRegResp.body.challenge = base64url.decode(fido2RequestRegResp.body.challenge);
    //fido2RequestRegResp.body.user.id = base64url.decode(fido2RequestRegResp.body.user.id);
    // console.log("CHALANGE: " + base64url.encode(fido2RequestRegResp.body.challenge));

    // console.log(fido2RequestRegResp.body);

    var publicKeyCredentialCreationOptions = preformatMakeCredReq(fido2RequestRegResp.body);

    navigator.credentials.create({
        "publicKey": publicKeyCredentialCreationOptions
    }).then(function (credential) {

        console.log(credential);

        var newCredentialInfo = publicKeyCredentialToJSON(credential);

        var fido2DoRegReq = {
            "header": getFidoHeader(),
            "body": {
                "regPublicKeyCredential": newCredentialInfo
            }
        };

        console.log(JSON.stringify(fido2DoRegReq));

        $.ajax({
            type: "POST",
            data: JSON.stringify(fido2DoRegReq),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/webauthn/doReg",
            success: function (fido2DoRegResp) {
                console.log(fido2DoRegResp);

                if (fido2DoRegResp.header.code == 1200) {
                    console.log("註冊成功");
                } else {
                    console.log("註冊失敗");
                }
            },
            error: function () {
                console.log("註冊失敗");
            }
        });

    });
}

function requestAuth(username) {

    if (username == undefined || username == "") {
        console.log("註冊失敗: 請輸入 username")
        return;
    }

    var fido2RequestAuthReq = {
        "header": getFidoHeader(),
        "body": {
            //"extensions": {},
            // "origin": "http://localhost:8080",
            // "rpId": "localhost",
            "origin": "https://fidolab.apps.oc.webcomm.com.tw",
            "rpId": "fidolab.apps.oc.webcomm.com.tw",
            //"userVerification": "preferred", // 這個是幹嘛的?
            "username": username
        }
    };

    $.ajax({
        type: "POST",
        data: JSON.stringify(fido2RequestAuthReq),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: "/webauthn/requestAuth",
        success: function (fido2RequestAuthResp) {
            console.log(fido2RequestAuthResp);
            if (fido2RequestAuthResp.header.code == 1200) {
                doAuth(fido2RequestAuthResp);
            } else {
                console.log("登入失敗");
            }
        },
        error: function () {
            console.log("登入失敗");
        }
    });
}

function doAuth(fido2RequestAuthResp) {

    var publicKeyCredentialRequestOptions = preformatGetAssertReq(fido2RequestAuthResp.body);

    navigator.credentials.get({
        publicKey: publicKeyCredentialRequestOptions
    }).then(function (credential) {

        console.log(credential);

        var publicKeyCredential = publicKeyCredentialToJSON(credential);

        var fido2DoAuthReq = {
            "header": getFidoHeader(),
            "body": {
                "publicKeyCredential": publicKeyCredential
                // "tokenBindingId": ""
            }
        }

        $.ajax({
            type: "POST",
            data: JSON.stringify(fido2DoAuthReq),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/webauthn/doAuth",
            success: function (fido2RequestAuthResp) {
                console.log(fido2RequestAuthResp);

                if (fido2RequestAuthResp.header.code == 1200) {
                    console.log("登入成功");
                } else {
                    console.log("登入失敗");
                }
            },
            error: function () {
                console.log("登入失敗");
            }
        });

    });
}