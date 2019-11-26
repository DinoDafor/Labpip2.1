import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = "controller")
public class ControllerServlet extends HttpServlet {//todo не-не, крч сюда надо фильтр, только он сможет не дать перейти по URI к result.jsp...


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {





        //todo проверка содержимого, если всё окей, то передаёт на новый result jsp, если нет, то редирект на index jsp
        //todo переделать! под фильтр!
        try {
            double x = Double.parseDouble(request.getParameter("x").replace(',', '.'));//Достаём x
            double y = Double.parseDouble(request.getParameter("y").replace(',', '.'));//Достаём y
            double r = Double.parseDouble(request.getParameter("r").replace(',', '.'));//Достаём r
        } catch (NumberFormatException ex) {
            response.sendRedirect("https://www.enable-javascript.com/ru/");//делаем редирект на jsp страницу с результатами
        }
//        //todo может оставим редирект
//        response.sendRedirect(request.getContextPath() + "/index.jsp");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/validate");
        dispatcher.forward(request,response);
        //todo на всякий оставлю тут форвард
        //RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        // dispatcher.forward(request,response);





        //тут принимаем данные, если не нулл и числа, то переходим на AreaCheckServlet, если не валидные, то редирект отбратно, всё
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
