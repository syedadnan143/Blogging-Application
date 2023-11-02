package com.cwd.blog.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class JwtAuthRequest {
  
	
	private String username;
	
	private String password;

	
}
