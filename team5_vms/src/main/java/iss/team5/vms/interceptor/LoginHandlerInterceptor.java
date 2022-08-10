package iss.team5.vms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("loginUser") == null){
        	System.out.println(request.getRequestURL());
//            request.getRequestDispatcher("/login").forward(request, response);
        	response.sendRedirect("http://localhost:8080/login");
            return false;
        }else {
            return true;
        }
    }
}