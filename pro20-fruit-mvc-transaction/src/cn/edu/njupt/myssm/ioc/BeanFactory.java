package cn.edu.njupt.myssm.ioc;

public interface BeanFactory {
    //根据applicationContext.xml文件中bean标签中的id获得具体的类
    Object getBean(String id);
}
