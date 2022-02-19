package cn.edu.njupt.fruit.servlets;

import cn.edu.njupt.fruit.dao.FruitDAO;
import cn.edu.njupt.fruit.dao.impl.FruitDAOImpl;
import cn.edu.njupt.fruit.pojo.Fruit;
import cn.edu.njupt.myssm.myspringmvc.ViewBaseServlet;
import cn.edu.njupt.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");

        String opreate = request.getParameter("operate");

        //若获取的operate为空，则默认index方法
        if (StringUtil.isEmpty(opreate)) {
            opreate = "index";
        }

        switch (opreate) {
            case "index":
                index(request, response);
                break;
            case "add":
                add(request,response);
                break;
            case "del":
                del(request, response);
                break;
            case "edit":
                edit(request, response);
                break;
            case "update":
                update(request, response);
                break;
            default:
                throw new RuntimeException("operate值非法！");
        }
    }

    //1.首页操作
    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //默认第一页
        HttpSession session = request.getSession();
        Integer pageNo = 1;

        String oper = request.getParameter("oper");
        //如果oper！=null，说明是通过表单查询按钮点击过来的
        //如果oper==null， 说明不是通过表单的查询按钮点击过来的

        String keyword = null;
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            //说明是点击表单查询发送的请求
            //此时 pageNo应该还原为 1，keyword应该从请求参数中获取
            pageNo = 1;
            keyword = request.getParameter("keyword");
            //如果keyword为null，需要设置空字符串""，否则查询时会拼接成 %null%,我们期望的是 %%
            if (StringUtil.isEmpty(keyword)) {//表示没有输入内容点了提交按钮
                keyword = "";
            }
            //将keyword保存（覆盖）到session中（表示当前是基于keyword显示的页面）
            session.setAttribute("keyword", keyword);
        }else{
            //说明不是点击表单查询发送的请求（比如点击下面的上一页下一页或者直接在地址栏输入网址）
            //此时keyword应该从session作用域中获取
            String pageNoStr=request.getParameter("pageNo");
            if (StringUtil.isNotEmpty(pageNoStr)) {
                pageNo = Integer.parseInt(pageNoStr);//如果从请求中读取到pageNo，则类型转换，否则，pageNo默认就是1
            }

            //如果不是点击的查询按钮，那么查询时基于session中保存的现有的keyword进行查询
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            }else{
                keyword = "";
            }
        }




        //重新更新当前页面的值
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

    //2.添加页面操作
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.
        //request.setCharacterEncoding("utf-8");

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
        response.sendRedirect("fruit.do");
    }

    //3.删除页面操作
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);

            response.sendRedirect("fruit.do");
        }
    }

    //4.编辑页面操作
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            request.setAttribute("fruit", fruit);
            super.processTemplate("edit", request, response);
        }
    }

    //5.更新操作
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        response.sendRedirect("fruit.do");

    }
}
