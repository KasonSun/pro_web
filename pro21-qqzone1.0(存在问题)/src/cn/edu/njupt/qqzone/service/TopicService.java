package cn.edu.njupt.qqzone.service;

import cn.edu.njupt.qqzone.pojo.Topic;
import cn.edu.njupt.qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicService {
    //查询特定的用户日志列表
    List<Topic> getTopicList(UserBasic userBasic);
}
