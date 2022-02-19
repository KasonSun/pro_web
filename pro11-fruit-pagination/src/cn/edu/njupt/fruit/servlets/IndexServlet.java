package cn.edu.njupt.fruit.servlets;

import cn.edu.njupt.fruit.dao.FruitDAO;
import cn.edu.njupt.fruit.dao.impl.FruitDAOImpl;
import cn.edu.njupt.fruit.pojo.Fruit;
import cn.edu.njupt.myssm.myspringmvc.ViewBaseServlet;
import cn.edu.njupt.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//Servlet从3.0版本开始支持注解方式的注册，添加注解不需要再进行web.xml的配置
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //默认第一页
        Integer pageNo = 1;
        String pageNoStr=request.getParameter("pageNo");
        if (StringUtil.isNotEmpty(pageNoStr)) {
             pageNo = Integer.parseInt(pageNoStr);
        }

        HttpSession session = request.getSession();
        session.setAttribute("pageNo", pageNo);

        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(pageNo);
        session.setAttribute("fruitList", fruitList);

        int fruitCount = fruitDAO.getFruitCount();
        int pageCount = (fruitCount + 5 - 1) / 5;
        session.setAttribute("pageCount", pageCount);

        //此处的视图名称是index
        //那么thymeleaf会将这个逻辑视图名称对应到物理视图名称上去
        //逻辑视图名称： index
        //物理视图名称：view-prefix+逻辑视图名称+view-suffix（web.xml中view-prefix=/; view-suffix=.html）
        //所以真实的视图名称：/index.html
        super.processTemplate("index", request, response);
    }
}
