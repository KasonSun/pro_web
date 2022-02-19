package cn.edu.njupt.qqzone.dao.impl;

import cn.edu.njupt.myssm.basedao.BaseDAO;
import cn.edu.njupt.qqzone.dao.TopicDAO;
import cn.edu.njupt.qqzone.pojo.Topic;
import cn.edu.njupt.qqzone.pojo.UserBasic;

import java.util.List;

public class TopicDAOImpl extends BaseDAO<Topic> implements TopicDAO {
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        return super.executeQuery("select * from t_topic where author=?", userBasic.getId());
    }

    @Override
    public void addTopic(Topic topic) {

    }

    @Override
    public void delTopic(Topic topic) {

    }

    @Override
    public Topic getTopic(Integer id) {
        return null;
    }
}
