package cn.edu.njupt.myssm.listeners;

import cn.edu.njupt.myssm.ioc.BeanFactory;
import cn.edu.njupt.myssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//监听上下文启动，在上下文启动的时候去创建IOC容器，然后将其保存到application作用域
//后面中央控制器DispatcherServlet再从application作用域中获取IOC容器
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();//1.得的application作用域

        String path = application.getInitParameter("contextConfigLocation");//2.获取上下文的初始化参数就是XML文件路径

        BeanFactory beanFactory = new ClassPathXmlApplicationContext(path);//3. 创建bean集合（创建IOC容器）

        application.setAttribute("beanFactory", beanFactory);//4.将bean集合（IOC容器）放入application作用域
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
