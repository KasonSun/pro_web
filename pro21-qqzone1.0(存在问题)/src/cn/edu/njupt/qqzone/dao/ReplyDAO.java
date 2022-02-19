package cn.edu.njupt.qqzone.dao;

import cn.edu.njupt.qqzone.pojo.Reply;
import cn.edu.njupt.qqzone.pojo.Topic;

import java.util.List;

public interface ReplyDAO {
    //获取指定的日志回复列表
    List<Reply> getReplyList(Topic topic);

    //添加回复
    void addReply(Reply reply);

    //删除回复
    void delReply(Integer id);
}
