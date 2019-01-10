/**
 * Copyright (C) 2018 Luvina Academy
 * LoginFilter.java Dec 18, 2018, Vu Van Kien
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
import javax.servlet.http.HttpSession;

import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class thực hiện chức năng kiểm tra tình trạng đăng nhập
 * 
 * @author kien vu
 *
 */
public class LoginFilter implements Filter {

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
			HttpSession session = request.getSession(true);
			String loginRequest = request.getRequestURL().toString();
			// Nếu đường dẫn đến file index.jsp hoặc đường dẫn đến trang đăng
			// nhập hoặc người dùng đã đăng nhập thì cho qua
			if (loginRequest.equals(Constant.LOGIN_URL) || loginRequest.equals(Constant.LOGIN_URL1)
					|| Common.checkLogin(session)) {
				filterChain.doFilter(servletRequest, servletResponse);
				// Ngược lại chuyển về màn hình đăng nhập
			} else {
				response.sendRedirect(Constant.LOGIN_URL1);
			}
		} catch (Exception e) {
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
