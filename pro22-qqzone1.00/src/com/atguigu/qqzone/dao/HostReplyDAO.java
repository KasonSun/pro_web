package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.HostReply;

public interface HostReplyDAO {
    //根据replyId查询关联的HostReply实体
    HostReply getHostReplyByReplyId(Integer replyId);

    //根据id删除HostReply
    void delHostReply(Integer id);
}
