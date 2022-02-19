package cn.edu.njupt.qqzone.dao.impl;

import cn.edu.njupt.myssm.basedao.BaseDAO;
import cn.edu.njupt.qqzone.dao.UserBasicDAO;
import cn.edu.njupt.qqzone.pojo.UserBasic;

import java.util.List;


public class UserBasicDAOImpl extends BaseDAO<UserBasic> implements UserBasicDAO {
    @Override
    public UserBasic getUserBasic(String loginId, String pwd) {
        return super.load("select * from t_user_basic where loginId=? and pwd=? ", loginId, pwd);
    }

    @Override
    public List<UserBasic> getUserBasicList(UserBasic userBasic) {
        String sql = "select fid as id from t_friend where uid=?";
        return super.executeQuery(sql, userBasic.getId());
    }

    @Override
    public UserBasic getUserBasicById(Integer id) {
        return load("select * from t_user_basic where id=?", id);
    }

}
