package com.eNvestDetails.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import com.eNvestDetails.util.UserAccountServiceUtil;


public class AuthenticationTokenProcessingFilter extends GenericFilterBean
{

	private final UserDetailsService userService;
	
	private static Logger log = Logger.getLogger(AuthenticationTokenProcessingFilter.class.getName());


	public AuthenticationTokenProcessingFilter(UserDetailsService userService)
	{
		this.userService = userService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);

		String authToken = this.extractAuthTokenFromRequest(httpRequest);
		String userName = TokenUtils.getUserNameFromToken(authToken);
		String urlPath = httpRequest.getPathInfo();
		log.info("Inside authentication token processing filter for request method: " +httpRequest.getMethod() +" and urlpath:"+urlPath);
		
		if(true || "/UserService/users/registerUser".equals(urlPath) || "/UserService/users/authenticate".equals(urlPath)){
			chain.doFilter(request, response);
			return;
		}
		
		if (userName != null) {

			UserDetails userDetails = this.userService.loadUserByUsername(userName);

			if (TokenUtils.validateToken(authToken, userDetails)) {

				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				chain.doFilter(request, response);
			}else{
				((HttpServletResponse) response).sendError(
						HttpServletResponse.SC_UNAUTHORIZED,
						"Unauthorized: Authentication token was either missing or invalid.");
			}

		}else{
			((HttpServletResponse) response).sendError(
					HttpServletResponse.SC_UNAUTHORIZED,
					"Unauthorized: Authentication token was either missing or invalid.");
		}

		
	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request)
	{
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest)
	{
		/* Get token from header */
		String authToken = httpRequest.getHeader("X-Auth-Token");
		log.info("getting token from header for request :"+ httpRequest.getPathInfo()+ " :" + authToken);
		/* If token not found get it from request parameter */
		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}
}