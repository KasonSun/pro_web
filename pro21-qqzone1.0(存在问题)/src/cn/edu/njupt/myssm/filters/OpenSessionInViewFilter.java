package cn.edu.njupt.myssm.filters;

import cn.edu.njupt.myssm.trans.TransactionManager;

import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            //1.开启事务
            TransactionManager.beginTrans();
            //放行（放行后会到后面服务层进行DAO调用，完成会返回回来）
            filterChain.doFilter(servletRequest, servletResponse);
            //2.提交事务
            TransactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //3.回滚事务（捕获的异常）
                TransactionManager.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
