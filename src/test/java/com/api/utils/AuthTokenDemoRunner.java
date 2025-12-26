package com.api.utils;

import java.io.IOException;
import java.time.Duration;

import com.api.constant.Role;

public class AuthTokenDemoRunner {

	public static void main(String[] args) throws IOException, InterruptedException {

		
		for(int i=0;i<5;i++)
		{
		
		String token = AuthTokenProvider.getToken(Role.FD);
	
		System.out.println(token);
	}

}
}
