/**
 * Copyright (C) 2018 Luvina Academy
 * CharacterEnCodingFilter.java Dec 21, 2018, Vu Van Kien
 */
package manageuser.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.utils.Constant;

/**
 * Class thực hiện chức năng set kiểu UTF-8 cho request và response
 * 
 * @author kien vu
 *
 */
public class CharacterEnCodingFilter implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		try {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			// Set định dạng UTF-8 cho request
			request.setCharacterEncoding("UTF-8");
			// Set định dạng UTF-8 cho response
			response.setCharacterEncoding("UTF-8");
			filterChain.doFilter(servletRequest, servletResponse);
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println("CharacterEnCodingFilter : doFilter - " + e.getMessage());
			// Chuyển về màn hình lỗi
			response.sendRedirect(Constant.ERROR_URL);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
