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

@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {

    FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.设置编码（post来自edit页面，post提交方式,在获取参数前进行设置）
        request.setCharacterEncoding("utf-8");

        //2.获取参数
        String fidStr = request.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountstr = request.getParameter("fcount");
        int fcount = Integer.parseInt(fcountstr);
        String remark = request.getParameter("remark");
        //3.执行更新
        fruitDAO.updateFruit(new Fruit(fid,fname,price,fcount,remark));//执行更新
        //4.资源跳转
        //super.processTemplate("index", request, response);//执行完成，回到index
        //request.getRrequestDispathcer("index.html").forward(request,response);//上面相当于这句话,此时就是简单跳转，数据还是修改之前的session中的值
        //故应该让客户端重定向，重新给IndexServlet发请求，重新获取fruitList，然后覆盖到session中，这样库存修改才能更新在index.html中
        response.sendRedirect("index");

    }
}
