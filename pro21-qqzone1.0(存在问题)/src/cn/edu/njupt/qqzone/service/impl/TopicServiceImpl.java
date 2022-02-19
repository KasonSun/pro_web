package cn.edu.njupt.qqzone.service.impl;

import cn.edu.njupt.qqzone.dao.TopicDAO;
import cn.edu.njupt.qqzone.pojo.Topic;
import cn.edu.njupt.qqzone.pojo.UserBasic;
import cn.edu.njupt.qqzone.service.TopicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    private TopicDAO topicDAO = null;


    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        List<Topic> topicList = topicDAO.getTopicList(userBasic);
        return topicList;
    }
}
