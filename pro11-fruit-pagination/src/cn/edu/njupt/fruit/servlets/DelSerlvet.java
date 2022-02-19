package cn.edu.njupt.fruit.servlets;

import cn.edu.njupt.fruit.dao.FruitDAO;
import cn.edu.njupt.fruit.dao.impl.FruitDAOImpl;
import cn.edu.njupt.myssm.myspringmvc.ViewBaseServlet;
import cn.edu.njupt.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/del.do")
public class DelSerlvet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);

            response.sendRedirect("index");
        }
    }
}
