package tw.com.webcomm.fido.middleware.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tw.com.webcomm.fido.middleware.service.UafService;

@CrossOrigin(origins = "*")
@RestController
public class UafController {

	@Value("${fido.app-id}")
	private String appId;

	@Autowired
	private UafService svc;

	@GetMapping(value = "/uaf/facets", produces = MediaType.APPLICATION_JSON_VALUE)
	public String facets() throws JsonMappingException, JsonProcessingException {
		String getFacetsResponse = svc.facets(appId);

		// 注意: 中台這邊需要額外做加工，若 return code 成功，則 「只要傳出 body 內的內容，不傳 header」

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode respNode = mapper.readValue(getFacetsResponse, ObjectNode.class);

		String respCode = respNode.get("header").get("code").asText();
		if ("1200".equals(respCode)) {
			JsonNode responseBody = respNode.get("body");

			getFacetsResponse = mapper.writeValueAsString(responseBody);
		}

		return getFacetsResponse;
	}

	@PostMapping(value = "/uaf/requestReg", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String requestReg(@RequestBody String req) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reqNode = mapper.readValue(req, ObjectNode.class);
		reqNode.with("body").put("appID", appId);
		reqNode.with("body").put("rpServerData", "testdata");
		req = mapper.writeValueAsString(reqNode);

		String res = svc.requestReg(req);
		return res;
	}

	@PostMapping(value = "/uaf/doReg", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String doReg(@RequestBody String req) {
		String res = svc.doReg(req);
		return res;
	}

	@PostMapping(value = "/uaf/requestAuth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String requestAuth(@RequestBody String req) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reqNode = mapper.readValue(req, ObjectNode.class);
		reqNode.with("body").put("appID", appId);

		String transactionContent = reqNode.path("body").path("transaction").path("content").asText();
		if (!transactionContent.isBlank()) {
			String base64Content = Base64.getUrlEncoder().withoutPadding()
					.encodeToString(transactionContent.getBytes());
			reqNode.with("body").with("transaction").put("content", base64Content);
			reqNode.with("body").with("transaction").put("contentType", "text/plain");
		}

		req = mapper.writeValueAsString(reqNode);

		String res = svc.requestAuth(req);
		return res;
	}

	@PostMapping(value = "/uaf/doAuth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String doAuth(@RequestBody String req) {
		String res = svc.doAuth(req);
		return res;
	}

	@PostMapping(value = "/uaf/requestPairAuth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String requestPairAuth(@RequestBody String req) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reqNode = mapper.readValue(req, ObjectNode.class);
		reqNode.with("body").put("appID", appId);
		req = mapper.writeValueAsString(reqNode);

		String res = svc.requestPairAuth(req);
		return res;
	}

	@PostMapping(value = "/uaf/doDereg", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String doDereg(@RequestBody String req) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reqNode = mapper.readValue(req, ObjectNode.class);
		reqNode.with("body").put("appID", appId);
		req = mapper.writeValueAsString(reqNode);

		String res = svc.doDereg(req);
		return res;
	}

}
