

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//todo переименовать под фильтр
public class ControllerFilter implements Filter { //Фильтр, который осуществляет проверку на входные данные для AreaCheckServlet


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("Вошли в фильтр...");

        String x = servletRequest.getParameter("x");
        String y = servletRequest.getParameter("y");
        String r = servletRequest.getParameter("r");


        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;




        System.out.println("X в фильтре равен: " + x);
        System.out.println("Y в фильтре равен: " + y);
        System.out.println("R в фильтре равен: " + r);

        System.out.println("Входим в проверку на значения в фильтре ControllerFilter...");
        if (!(x == null || y == null || r == null) && (isNumeric(x) && isNumeric(y) && isNumeric(r))) {
            //todo ВОЗМОЖНО надо было округление засунуть сюда, хотя не факт
            System.out.println("Вошли в проверку на значения в фильтре ControllerFilter...");
                filterChain.doFilter(servletRequest, servletResponse);

        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    //todo прочитать про выражения
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    @Override
    public void destroy() {

    }
}
