package com.atguigu.qqzone.service.impl;

import com.atguigu.qqzone.dao.ReplyDAO;
import com.atguigu.qqzone.pojo.HostReply;
import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.HostReplyService;
import com.atguigu.qqzone.service.ReplyService;
import com.atguigu.qqzone.service.UserBasicService;

import java.util.List;

public class ReplyServiceImpl implements ReplyService {
    private ReplyDAO replyDAO;
    //此处引入是其他POJO对应的service接口，而不是DAO接口
    //其他POJO对应的业务逻辑是封装在service层的，我需要调用别人的业务逻辑方法，但不需要去深入考虑人家内部的细节
    private HostReplyService hostReplyService;
    private UserBasicService userBasicService;

    @Override
    public List<Reply> getReplyListByTopicId(Integer topicId) {
        List<Reply> replyList = replyDAO.getReplyList(new Topic(topicId));
        for (int i = 0; i < replyList.size(); i++) {
            Reply reply = replyList.get(i);
            //1.将关联的作者信息设置进去
            UserBasic author = userBasicService.getUserBasicById(reply.getAuthor().getId());
            reply.setAuthor(author);

            //2.将关联的HostReply设置进去
            HostReply hostReply = hostReplyService.getHostReplyByReplyID(reply.getId());
            reply.setHostReply(hostReply);
        }
        return replyList;
    }

    @Override
    public void addReply(Reply reply) {
        replyDAO.addReply(reply);
    }

    @Override
    public void delReply(Integer id) {
        //解决外键约束导致主表删除不成功，需要先删除字表引用的数据，再删除主表数据

        //1.根据id获取到reply
        Reply reply = replyDAO.getReply(id);
        if (reply != null) {
            //2.如果reply有关联的HostReply，则先删除hostReply
            HostReply hostReply = hostReplyService.getHostReplyByReplyID(reply.getId());
            if (hostReply != null) {
                hostReplyService.delHostReply(hostReply.getId());
            }
            //3.删除reply
            replyDAO.delReply(id);
        }
    }

    @Override
    public void delReplyList(Topic topic) {
        List<Reply> replyList = replyDAO.getReplyList(topic);
        if (replyList != null) {
            for (Reply reply : replyList) {
                delReply(reply.getId());//删除关联的回复（包含主人恢复删除的特殊情况，已经封装在上面delReply方法中）
            }
        }
    }
}
