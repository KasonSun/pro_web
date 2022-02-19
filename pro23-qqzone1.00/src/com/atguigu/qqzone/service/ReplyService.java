package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;

import java.util.List;

public interface ReplyService {
    //根据topic的id获取关联的所有回复
    List<Reply> getReplyListByTopicId(Integer topicId);

    //添加回复
    void addReply(Reply reply);

    //删除回复
    void delReply(Integer replyId);

    //删除日志关联的所有的回复
    void delReplyList(Topic topic);
}
