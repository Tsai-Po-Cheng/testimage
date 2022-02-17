package tw.com.webcomm.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tw.com.webcomm.example.entity.Account;
import tw.com.webcomm.example.repository.AccountRepository;

@CrossOrigin(origins = "*")
@RestController
public class MobileController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AccountRepository accountRepository;

	@PostMapping(value = "/mobile/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> authenticate(@RequestBody Map<String, String> user) {
		
		String username = user.get("username");
		String password = user.get("password");
		
		Map<String, String> resp = new HashMap<String, String>();
		
		if (username == null || password == null) {		
			resp.put("authenticationResult", String.valueOf(false));
			return resp;
		}
		
		resp.put("username", username);

		Account account = accountRepository.findByEmail(username);
		if (account != null) {
			Boolean isPasswordMatch = bCryptPasswordEncoder.matches(password, account.getPassword());
			if (isPasswordMatch) {
				resp.put("authenticationResult", String.valueOf(true));
				return resp;
			}
		}

		resp.put("authenticationResult", String.valueOf(false));
		return resp;
	}

	@PostMapping(value = "/mobile/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Account getAccountInfo(@RequestBody Map<String, String> user) {

		String username = user.get("username");
		Account account = accountRepository.findByEmailAndActive(username, true);

		Account res = new Account();
		res.setName(account.getName());
		res.setEmail(account.getEmail());
		res.setBalance(account.getBalance());

		return res;
	}

}
