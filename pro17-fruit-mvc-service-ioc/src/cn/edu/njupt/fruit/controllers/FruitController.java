package cn.edu.njupt.fruit.controllers;

import cn.edu.njupt.fruit.service.FruitService;
import cn.edu.njupt.fruit.pojo.Fruit;
import cn.edu.njupt.myssm.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public class FruitController {
    private FruitService fruitService = null;

    //1.首页操作
    private String index(String oper, String keyword, Integer pageNo, HttpServletRequest request) {

        //默认第一页
        HttpSession session = request.getSession();

        if (pageNo == null) {
            pageNo = 1;
        }
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {

            pageNo = 1;
            //如果keyword为null，需要设置空字符串""，否则查询时会拼接成 %null%,我们期望的是 %%
            if (StringUtil.isEmpty(keyword)) {//表示没有输入内容点了提交按钮
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        }else{
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            }else{
                keyword = "";
            }
        }

        //重新更新当前页面的值
        session.setAttribute("pageNo", pageNo);

        List<Fruit> fruitList = fruitService.getFruitList(keyword,pageNo);
        session.setAttribute("fruitList", fruitList);

        int pageCount = fruitService.getPageCount(keyword);
        session.setAttribute("pageCount", pageCount);

        //此处的视图名称是index
        //那么thymeleaf会将这个逻辑视图名称对应到物理视图名称上去
        //逻辑视图名称： index
        //物理视图名称：view-prefix+逻辑视图名称+view-suffix（web.xml中view-prefix=/; view-suffix=.html）
        //所以真实的视图名称：/index.html
//        super.processTemplate("index", request, response);

        return "index";
    }

    //2.添加页面操作
    private String add(String fname, Integer price, Integer fcount, String remark) {
        fruitService.addFruit(new Fruit(0, fname, price, fcount, remark));
//        response.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    //3.删除页面操作
    private String del(Integer fid) {

        if (fid != null) {
            fruitService.delFruit(fid);
//            response.sendRedirect("fruit.do");
            return "redirect:fruit.do";
        }
        return "error";
    }

    //4.编辑页面操作
    private String edit(Integer fid, HttpServletRequest request){
        if (fid != null) {
            Fruit fruit = fruitService.getFruitByFid(fid);
            request.setAttribute("fruit", fruit);
//            super.processTemplate("edit", request, response);
            return "edit";
        }
        return "error";
    }

    //5.更新操作
    private String update(Integer fid, String fname, Integer price, Integer fcount, String remark){
        //3.执行更新
        fruitService.updateFruit(new Fruit(fid,fname,price,fcount,remark));//执行更新
        //4.资源跳转
        //super.processTemplate("index", request, response);//执行完成，回到index
        //request.getRequestDispathcer("index.html").forward(request,response);//上面相当于这句话,此时就是简单跳转，数据还是修改之前的session中的值
        //故应该让客户端重定向，重新给IndexServlet发请求，重新获取fruitList，然后覆盖到session中，这样库存修改才能更新在index.html中
//        response.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
}
