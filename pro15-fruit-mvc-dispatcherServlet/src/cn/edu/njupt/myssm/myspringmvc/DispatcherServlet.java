package cn.edu.njupt.myssm.myspringmvc;

import cn.edu.njupt.myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@WebServlet("*.do") //拦截所有以.do结尾的请求
public class DispatcherServlet extends ViewBaseServlet {

    //存储applicationContext.xml文件中id以及对应的class即为（id，class）
    private Map<String, Object> beanMap = new HashMap<>();

    //  构造方法   中读取applicationContext.xml文件
    public DispatcherServlet(){


    }

    public void init() throws ServletException {
        super.init();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //1.创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //3.创建Document对象
            Document document = documentBuilder.parse(inputStream);

            //4.通过document获取xml中bean标签对应的所有java bean
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    //通过className获取实例
                    Object beanObj = Class.forName(className).newInstance();
                    //将applicationContext.xml文件中id以及对应的class即为（id，class）放入map
                    beanMap.put(beanId, beanObj);
                }
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");

        //假设url：http://localhost:8081/hello.do
        //那么servletPath是：/hello.do
        //我的思路是：
        //第一步： /hello.do -> hello
        //第二步： ->HelloController
        String servletPath = request.getServletPath();
        servletPath = servletPath.substring(1);//从索引1开始截取，得到hello.do
        int lastDotIndex = servletPath.lastIndexOf(".do");//获取.的索引
        servletPath = servletPath.substring(0, lastDotIndex);//得到hello
//        System.out.println(servletPath);//打印hello

        //servletPath实际就是applicationContext.xml文件中id
        Object controllerBeanObj = beanMap.get(servletPath);


        String operate = request.getParameter("operate");

        //若获取的operate为空，则默认index方法
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            Method method = controllerBeanObj.getClass().getDeclaredMethod(operate, HttpServletRequest.class);
            if (method != null) {
                //2.controller组件中的方法调用
                method.setAccessible(true);
                Object returnObj = method.invoke(controllerBeanObj, request);

                //3.视图处理
                String methodReturnStr = (String) returnObj;
                if (methodReturnStr.startsWith("redirect:")) {  //eg: "redirect:fruit.do"
                    String redirectStr = methodReturnStr.substring("redirect:".length());//从:后面开始截取得到fruit.do
                    response.sendRedirect(redirectStr);
                }else{
                    super.processTemplate(methodReturnStr,request,response);    //eg: "edit"
                }


            }else{
                throw new RuntimeException("operate值非法！");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
