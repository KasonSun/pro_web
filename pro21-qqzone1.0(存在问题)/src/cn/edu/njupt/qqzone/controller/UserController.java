package cn.edu.njupt.qqzone.controller;

import cn.edu.njupt.qqzone.pojo.Topic;
import cn.edu.njupt.qqzone.pojo.UserBasic;
import cn.edu.njupt.qqzone.service.TopicService;
import cn.edu.njupt.qqzone.service.UserBasicService;

import javax.servlet.http.HttpSession;
import java.util.List;

public class UserController {
    private UserBasicService userBasicService;
    private TopicService topicService;

    public String login(String loginId, String pwd, HttpSession session) {
        //1.登录验证
        UserBasic userBasic = userBasicService.login(loginId, pwd);
        if (userBasic != null) {
            //1-1 获取相关好友信息
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
            //1-2 获取相关的日志列表信息（但是，日志只有id 没有其他信息）
            List<Topic> topicList = topicService.getTopicList(userBasic);

            userBasic.setFriendList(friendList);
            userBasic.setTopicList(topicList);

            session.setAttribute("userBasic", userBasic);
            return "index";
        }else{
            return "login";
        }
    }
}
