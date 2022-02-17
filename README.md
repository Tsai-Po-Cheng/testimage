# Fido Lab - Middleware

將目前開出來給 UAF 的 API 統整於下方

## GET /uaf/facets

### URL

https://fidolab.apps.oc.webcomm.com.tw/middleware/uaf/facets

### Request:

無

### Response:

*注意: 若 return code 為 `1200`，中台需做調整，只要回傳 body 內的內容，而非直接回傳 Fido Server 回覆結果*

回傳值為 `1200` 時 (happy path)： 

```json
{
  "trustedFacets": TrustedFacets
}
```

其他狀況：

```json
{
  "header": RestResponseHeader,
  "body": {
    "trustedFacets": TrustedFacets
  }
}
```

#### 參考 WebComm FIDO Server API Document 1.2.6 release 物件

- RestResponseHeader: 通用回覆之錯誤代碼與時間
- TrustedFacets: 受信任的 Facet ID

---

## POST /uaf/requestReg

### URL

https://fidolab.apps.oc.webcomm.com.tw/middleware/uaf/requestReg

### Request:

```json
{
  "header": RestRequestHeader,
  "body": {
    "username": "Jason",
  }
}
```

### Response:

```json
{
  "header": RestResponseHeader,
  "body": {
    "regRequests": UAFRegistrationRequest
}
```

#### 欄位說明

- username: String，要註冊之使用者名稱

#### 參考 WebComm FIDO Server API Document 1.2.6 release 物件

- RestRequestHeader: 通用請求的裝置資料
- RestResponseHeader: 通用回覆之錯誤代碼與時間
- UAFRegistrationRequest: 要回傳給 Client 的註冊資料

---

## POST /uaf/doReg

### URL

https://fidolab.apps.oc.webcomm.com.tw/middleware/uaf/doReg

### Request:

```json
{
  "header": RestRequestHeader,
  "body": {
    "regResponses": UAFRegistrationResponse
  }
}
```

### Response:

```json
{
  "header": RestResponseHeader,
  "body": {
    "regData1": RegData1
  }
}
```

#### 參考 WebComm FIDO Server API Document 1.2.6 release 物件

- RestRequestHeader: 通用請求的裝置資料
- RestResponseHeader: 通用回覆之錯誤代碼與時間
- UAFRegistrationResponse: AuthSDK 驗證後傳回的註冊資料
- RegData1: 註冊完成後，傳回給 SDK 的資料

---

## POST /uaf/requestAuth

### URL

https://fidolab.apps.oc.webcomm.com.tw/middleware/uaf/requestAuth

### Request:

```json
{
  "header": RestRequestHeader,
  "body": {
    "username": "Jason",
    "transaction": {
      "content": "the message you want to show."
    }
  }
}
```

### Response:

```json
{
  "header": RestResponseHeader,
  "body": {
    "authRequests": UAFAuthenticationRequest
}
```

#### 欄位說明

- username: String，要登入之使用者名稱
- content: String，要顯示給使用者確認時顯示的資訊，內容必須透過 base64-url encode (optional)
  - 更新：Base 64 已透過後端處理，可直接傳送字串
- contentType: String，顯示資訊的內容類型，目前只能填「"text/plain"」，支援 MIME Content-Type (optional)
  - 更新：若填入 content，已由後端自動填入，可不填寫

#### 參考 WebComm FIDO Server API Document 1.2.6 release 物件

- RestRequestHeader: 通用請求的裝置資料
- RestResponseHeader: 通用回覆之錯誤代碼與時間
- UAFRegistrationRequest: 要回傳給 Client 的驗證資料
- Transaction: 交易資料，optional，可參考文件，若有傳遞此物件時，UAFRegistrationRequest 也會夾帶相同資料給 SDK 使用，SDK 將會跳出確認視窗。

---

## POST /uaf/doAuth

### URL

https://fidolab.apps.oc.webcomm.com.tw/middleware/uaf/doAuth

### Request:

```json
{
  "header":  RestRequestHeader,
  "body": {
    "authResponses": UAFAuthenticationResponse
  }
}
```

### Response:

```json
{
  "header": RestResponseHeader,
}
```

#### 參考 WebComm FIDO Server API Document 1.2.6 release 物件

- RestRequestHeader: 通用請求的裝置資料
- RestResponseHeader: 通用回覆之錯誤代碼與時間
- UAFAuthenticationResponse: AuthSDK 驗證後傳回的驗證資料

---

## POST /uaf/requestPairAuth

### URL

https://fidolab.apps.oc.webcomm.com.tw/middleware/uaf/requestPairAuth

### Request:

```json
{
  "header": RestRequestHeader,
  "body": {
    "paircode": "9eIaWRd0OkKniBzRvfPQ1LGakeiALYVrOekE-sFdhyM",
    "username": "tester@webcomm.com.tw",
    "extensionIdList": ["registered_uuid"]
  }
}
```

### Response:

```json
{
  "header": RestResponseHeader,
  "body": {
    "authRequests": UAFAuthenticationRequest
  }
}
```

#### 欄位說明
- paircode: String，推播或掃碼功能驗證碼
- username: String，要登入之使用者名稱
- extensionIdList: 要使用的 extension id 列表，先照上述範例填


#### 參考 WebComm FIDO Server API Document 1.2.6 release 物件

- RestRequestHeader: 通用請求的裝置資料
- RestResponseHeader: 通用回覆之錯誤代碼與時間
- UAFRegistrationRequest: 要回傳給 Client 的驗證資料

---

## POST /uaf/doDereg

### URL

https://fidolab.apps.oc.webcomm.com.tw/middleware/uaf/doDereg

### Request:

```json
{
  "header": RestRequestHeader,
  "body": {
    "username": "jason.chiu",
    "authenticators": DeregisterAuthenticator
  }
}
```

### Response:

```json
{
  "header": RestResponseHeader,
  "body": {
    "deRegRequests": UAFDeregistrationRequest
  }
}
```

#### 欄位說明

- username: String，要登入之使用者名稱
- authenticators: 要註銷的驗證器 (內容參考 Fido 文件)

#### 參考 WebComm FIDO Server API Document 1.2.6 release 物件

- RestRequestHeader: 通用請求的裝置資料
- RestResponseHeader: 通用回覆之錯誤代碼與時間
- DeregisterAuthenticator: List of authenticators to be deregistered.
- UAFDeregistrationRequest: 要回傳給Client的註銷資料

---

# 虛擬網銀範例 - 行動端 API

## POST /mobile/auth
驗證帳號密碼，回傳 Boolean 值

### URL

https://fidolab.apps.oc.webcomm.com.tw/middleware/mobile/auth

### Request:

```json
{
    "username": "A123456789",
    "password": "myWeakPassword"
}
```

### Response:

驗證成功

```json
{
    "authenticationResult": "true",
    "username": "A123456789"
}
```

驗證失敗

```json
{
    "authenticationResult": "false",
    "username": "A123456789"
}
```

或者，未提供帳號密碼欄位時

```json
{
    "authenticationResult": "false",
}
```
---

## POST /mobile/account
取得帳號資訊，包含顯示名稱、帳號、餘額

### URL

https://fidolab.apps.oc.webcomm.com.tw/middleware/mobile/account

### Request:

```json
{
    "username": "A123456789",
}
```

### Response:

```json
{
    "name": "Alex Yang",
    "email": "A123456789",
    "balance": 999
}
```

---

## POST /ibank/mobile/transfer

進行轉帳

### URL

https://fidolab.apps.oc.webcomm.com.tw/ibank/mobile/transfer

須注意此 API 位於 ibank 應用程式上，而非 middleware，其前綴 content path 不同

### Request:

範例：使用者「jason.chiu@webcomm.com.tw」轉帳 1 塊錢給使用者「A123456789」

```json
{
    "userEmail": "jason.chiu@webcomm.com.tw",
    "remoteEmail": "A123456789",
    "amount": 1
}
```

### Response:

```json
{
    "transferResult": "success"
}
```

或

```json
{
    "exception": "此帳戶不存在",
    "transferResult": "fail"
}
```

---

## POST /ibank/mobile/transactionsPage

取得使用者交易紀錄

### URL

https://fidolab.apps.oc.webcomm.com.tw/ibank/mobile/transactionsPage

須注意此 API 位於 ibank 應用程式上，而非 middleware，其前綴 content path 不同

### Request:

```json
{
    "email": "jason.chiu@webcomm.com.tw",
    "page": "0",
    "size": 5
}
```

### Response:

```json
{
    "content": [
        {
            "accountEmail": "jason.chiu@webcomm.com.tw",
            "type": "deposit",
            "amount": 999,
            "date": "2022-01-17T09:29:10.086+0000",
            "remoteEmail": null,
            "active": true,
            "balance": 3017487
        },
        {
            "accountEmail": "jason.chiu@webcomm.com.tw",
            "type": "withdraw",
            "amount": 500,
            "date": "2022-01-17T09:29:04.386+0000",
            "remoteEmail": null,
            "active": true,
            "balance": 3016488
        },
        {
            "accountEmail": "jason.chiu@webcomm.com.tw",
            "type": "transfer",
            "amount": 1,
            "date": "2022-01-17T09:25:27.660+0000",
            "remoteEmail": "jason.chiu2@webcomm.com.tw",
            "active": true,
            "balance": 3016988
        },
        {
            "accountEmail": "jason.chiu@webcomm.com.tw",
            "type": "transfer",
            "amount": 1,
            "date": "2022-01-17T09:25:17.333+0000",
            "remoteEmail": "jason.chiu2@webcomm.com.tw",
            "active": true,
            "balance": 3016989
        },
        {
            "accountEmail": "jason.chiu@webcomm.com.tw",
            "type": "transfer",
            "amount": 2,
            "date": "2022-01-17T09:24:40.589+0000",
            "remoteEmail": "jason.chiu2@webcomm.com.tw",
            "active": true,
            "balance": 3016990
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "offset": 0,
        "pageSize": 5,
        "pageNumber": 0,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 5,
    "totalElements": 25,
    "last": false,
    "number": 0,
    "size": 5,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "numberOfElements": 5,
    "first": true,
    "empty": false
}
```

此 API 會自動以時間進行降幕排序，以上述範例，將於網頁端查詢使用者「jason.chiu@webcomm.com.tw」的交易紀錄，將顯示前五筆顯示


|\# | 類型 | 金額 |	 餘額  |       時間       |           交易對象           |
|:-:|:---:|:----:|:------:|:----------------:|:---------------------------:|
| 1 |	存款 | 999 | 3017487 | 2022-01-17 17:29 |                            |
| 2 |	提款 | 500 | 3016488 | 2022-01-17 17:29 |                            |
| 3 |	匯款 |   1 | 3016988 | 2022-01-17 17:25 | jason.chiu2@webcomm.com.tw |
| 4 |	匯款 |   1 | 3016989 | 2022-01-17 17:25 | jason.chiu2@webcomm.com.tw |
| 5 |	匯款 |   2 | 3016990 | 2022-01-17 17:24 | jason.chiu2@webcomm.com.tw |

---