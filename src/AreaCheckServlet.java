import servlets.Coordinate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@WebServlet(name = "AreaCheckServlet", urlPatterns = "validate")
public class AreaCheckServlet extends HttpServlet {
    ArrayList<Coordinate> coordinatesCollection = new ArrayList<>();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss"); //Для обработки времени
        System.out.println("Принимаем данные в AreaCheckServlet..."); //СТ, которая говорит о том, что мы попали в AreaCheck
        System.out.println("Входные данные: ");





        double startTime = System.nanoTime(); //Начало выполнения скрипта
        double x = Double.parseDouble(request.getParameter("x").replace(',', '.'));//Достаём x, убираем "," из чисел
        System.out.println("X: " + x);
        double y = Double.parseDouble(request.getParameter("y").replace(',', '.'));//Достаём y
        System.out.println("Y: " + y);
        double r = Double.parseDouble(request.getParameter("r").replace(',', '.'));//Достаём r
        System.out.println("R: " + r);
        //ОКРУГЛЕНИЕ ВЕРНОЕ
        x = Math.round(x * 100) / 100.0;
        System.out.println("X после округления: " + x);
        y = Math.round(y * 100) / 100.0;
        System.out.println("X после округления: " + y);



        double pixelX = x * (100 / 3.0);
        System.out.println("Х пиксилей от клиента: " + pixelX);
        double pixelY = (y * (100 / 3.0));
        System.out.println("Y пиксилей от клиента: " + pixelY);

        //проверим входные данные


        boolean correctCoordinate = false; //Проверка на попадание
        //ВАЛИДАЦИЯ НА ОБЛАСТЬ ВЕРНАЯ!
        if (x >= 0 && y >= 0 && (x * x + y * y <= r * r)) { //первая четверть, КРУГ
            correctCoordinate = true;
        } else if (x >= 0 && y <= 0 && x <= r && y >= -r / 2) { //четвёртая, ПРЯМОУГОЛЬНИК
            correctCoordinate = true;
        } else if (x <= 0 && y <= 0 && (x >= -r / 2) && (y >= -r) && (y >= -2 * x - r)) { //третья, ТРЕУГОЛЬНИК
            correctCoordinate = true;
        }

        double endTime = System.nanoTime(); //Время окончания скрипта
        double execute = (endTime - startTime) / Math.pow(10, 9); //Продолжительность выполнения скрипта


        Coordinate coordinate = new Coordinate(x, y, r, pixelX, pixelY, simpleDateFormat.format(new Date()), execute, correctCoordinate); //Создание объекта-координат для передачи
        ServletContext context = getServletContext(); // Достаём контекст

        Object attribute = context.getAttribute("userData"); // достаём новые/старые значения точек


        if (!(attribute == null || ((ArrayList<Coordinate>) attribute).size() == 0)) { //Если коллекции нет, то создаём её
            coordinatesCollection = (ArrayList<Coordinate>) attribute;
        }

        coordinatesCollection.add(coordinate); //Кладём в коллекцию наш объект с координатами
        context.setAttribute("userData", coordinatesCollection);//сохраняем в контексте коллекцию с точками



        getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
