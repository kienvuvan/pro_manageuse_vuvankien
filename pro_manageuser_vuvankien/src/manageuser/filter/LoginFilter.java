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
			// Nếu đường dẫn đến trang đăng nhập hoặc người dùng đã đăng nhập
			// hoặc nếu đường dẫn không tồn tại thì cho qua
			if (loginRequest.equals(contextPath + Constant.LOGIN_URL) || Common.checkLogin(session)
					|| response.getStatus() == Constant.NOT_FOUND_URL) {
				filterChain.doFilter(servletRequest, servletResponse);
				// Ngược lại nếu là đường dẫn tồn tại nhưng admin chưa đăng nhập
			} else {
				// Chuyển về màn hình đăng nhập
				response.sendRedirect(Constant.LOGIN_URL);
			}
			// Nếu có lỗi xảy ra
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
