package cn.edu.njupt.qqzone.service.impl;

import cn.edu.njupt.qqzone.dao.UserBasicDAO;
import cn.edu.njupt.qqzone.pojo.UserBasic;
import cn.edu.njupt.qqzone.service.UserBasicService;

import java.util.ArrayList;
import java.util.List;

public class UserBasicServiceImpl implements UserBasicService {
    private UserBasicDAO userBasicDAO = null;

    @Override
    public UserBasic login(String loginId, String pwd) {
        UserBasic userBasic = userBasicDAO.getUserBasic(loginId, pwd);
        return userBasic;
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        //查到的是fid列表，下面还需要根据fid找到具体的人
        List<UserBasic> userBasicList = userBasicDAO.getUserBasicList(userBasic);
        List<UserBasic> friendList = new ArrayList<>(userBasicList.size());
        for (int i = 0; i < userBasicList.size(); i++) {
            UserBasic friend = userBasicList.get(i);
            friendList.add(friend);
        }
        return friendList;
    }
}
