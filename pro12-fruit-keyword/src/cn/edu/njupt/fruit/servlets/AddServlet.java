package cn.edu.njupt.fruit.servlets;

import cn.edu.njupt.fruit.dao.FruitDAO;
import cn.edu.njupt.fruit.dao.impl.FruitDAOImpl;
import cn.edu.njupt.fruit.pojo.Fruit;
import cn.edu.njupt.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {
    //add.html中表单method=post，故重写post方法
    FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.
        request.setCharacterEncoding("utf-8");

        //2.
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = request.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = request.getParameter("remark");

        //3.
        fruitDAO.addFruit(new Fruit(0,fname, price, fcount, remark));

        //4.
        response.sendRedirect("index");
    }
}
