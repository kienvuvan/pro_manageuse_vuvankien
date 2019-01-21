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
		// Lấy ra đối tượng HttpServletResponse
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		try {
			// Lấy ra đối tượng HttpServletRequest
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			// Lấy ra đối tượng Session
			HttpSession session = request.getSession(true);
			// Lấy ra đường dẫn login
			String loginRequest = request.getRequestURI().toString();
			// Lấy ra đường dẫn thư mục gốc
			String contextPath = request.getContextPath() + "/";
			// Nếu đường dẫn là url vào màn hình đăng nhập
			if (loginRequest.equals(contextPath + Constant.LOGIN_URL)) {
				// Nếu người dùng chưa đăng nhập
				if (!Common.checkLogin(session)) {
					// Filter sẽ cho qua để chuyển đến màn hình ADM001
					filterChain.doFilter(servletRequest, servletResponse);
					// Nếu người dùng đãđăng nhập
				} else {
					// Chuyển về màn hình ADM002
					response.sendRedirect(Constant.LIST_USER_URL);
				}
				// Ngược lại, không phải url vào màn hình đăng nhập
			} else {
				// Nếu người dùng chưa đăng nhập
				if (!Common.checkLogin(session)) {
					// Chuyển về màn hình đăng nhập
					response.sendRedirect(Constant.LOGIN_URL);
					// Nếu người dùng đã đăng nhập
				} else {
					// Filter sẽ cho qua
					filterChain.doFilter(servletRequest, servletResponse);
				}
			}
			// Nếu có lỗi xảy ra
		} catch (Exception e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : "
					+ new Object(){}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
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
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
