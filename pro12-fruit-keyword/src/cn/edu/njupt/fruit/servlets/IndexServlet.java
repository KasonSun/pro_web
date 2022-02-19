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

    //实现查询（表单是post）
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        //默认第一页
        Integer pageNo = 1;
        HttpSession session = request.getSession();

        String oper = request.getParameter("oper");
        //如果oper！=null，说明是通过表单查询按钮点击过来的
        //如果oper==null， 说明不是通过表单的查询按钮点击过来的

        String keyword = null;
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            //说明是点击表单查询发送的请求
            //此时 pageNo应该还原为 1，keyword应该从请求参数中获取
            pageNo = 1;
            keyword = request.getParameter("keyword");
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        }else{
            //说明不是点击表单查询发送的请求（比如点击下面的上一页下一页或者直接在地址栏输入网址）
            //此时keyword应该从session作用域中获取
            String pageNoStr=request.getParameter("pageNo");
            if (StringUtil.isNotEmpty(pageNoStr)) {
                pageNo = Integer.parseInt(pageNoStr);
            }
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            }else{
                keyword = "";
            }
        }





        session.setAttribute("pageNo", pageNo);

        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword,pageNo);
        session.setAttribute("fruitList", fruitList);

        int fruitCount = fruitDAO.getFruitCount(keyword);
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
