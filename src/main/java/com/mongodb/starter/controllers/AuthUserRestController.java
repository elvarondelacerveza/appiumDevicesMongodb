package com.mongodb.starter.controllers;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mongodb.starter.models.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthUserRestController {

	@Autowired
	RestTemplate restTemplate;

	@PostMapping(value = "/user")
	public String login(@RequestParam("user") String is, @RequestParam("password") String pwd) {
		String response;
		Map<String, String> params = new HashMap<String, String>();
		params.put("is", is);
		params.put("password", pwd);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		User user = new User();

		try {
			String loginUrl = "https://ctp-services.herokuapp.com/beta/users/login";
			response = restTemplate.postForEntity(loginUrl, params, String.class).getBody();
			JSONObject jsonObject = new JSONObject(response);
			String ITA_token = (String) jsonObject.get("token");
			headers.setBearerAuth(ITA_token);

			// Set cookie
			headers.add("Cookie", "ITAUSR=" + is);
			headers.add("Cookie", "ITASESSION=" + ITA_token);
			String urlUser = "http://ctp-services.herokuapp.com/beta/users/" + is;

			jsonObject = new JSONObject(restTemplate
					.exchange(RequestEntity.get(new URI(urlUser)).headers(headers).build(), String.class).getBody());
			String projectId = (String) jsonObject.get("projectID");

			String urlProject = "http://ctp-services.herokuapp.com/beta/projects/" + projectId;
			jsonObject = new JSONObject(restTemplate
					.exchange(RequestEntity.get(new URI(urlProject)).headers(headers).build(), String.class).getBody());
			String jr = jsonObject.toString();

			if (jr.contains("Device Manager")) {
				String token = getJWTToken(is);
				user.setUser(is);
				user.setToken(token);
			} else {
				return "This project does not admit Device Manager implementation..";
			}
			return "User: " + is + " API_Token: " + user.getToken();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 86500000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}