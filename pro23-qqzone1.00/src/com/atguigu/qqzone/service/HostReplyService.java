package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.HostReply;

public interface HostReplyService {
    HostReply getHostReplyByReplyID(Integer replyID);

    void delHostReply(Integer id);
}
