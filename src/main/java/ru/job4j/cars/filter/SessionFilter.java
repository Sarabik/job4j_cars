package ru.job4j.cars.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.job4j.cars.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Order(2)
public class SessionFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        addUserToSession(session, request);
        chain.doFilter(request, response);
    }

    private void addUserToSession(HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("guest");
        }
        request.setAttribute("user", user);
    }

}
