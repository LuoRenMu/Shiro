package com.mikuac.shiro.dto.action.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * Created on 2021/9/6.
 *
 * @author Zero
 * @version $Id: $Id
 */
@Data
public class GetMsgResp {

    /**
     * 消息id
     */
    @JSONField(name = "message_id")
    private Integer messageId;

    /**
     * 消息真实id
     */
    @JSONField(name = "real_id")
    private Integer realId;

    /**
     * 发送者
     */
    @JSONField(name = "sender")
    private Sender sender;

    /**
     * 发送时间
     */
    @JSONField(name = "time")
    private Integer time;

    /**
     * 消息内容
     */
    @JSONField(name = "message")
    private String message;

    /**
     * 原始消息内容
     */
    @JSONField(name = "raw_message")
    private String rawMessage;

    /**
     * 消息类型
     */
    @JSONField(name = "message_type")
    private String messageType;

    /**
     * sender信息
     */
    @Data
    public static class Sender {

        @JSONField(name = "user_id")
        private String userId;

        @JSONField(name = "nickname")
        private String nickname;

        @JSONField(name = "card")
        private String card;

        @JSONField(name = "sex")
        private String sex;

        @JSONField(name = "age")
        private Integer age;

        @JSONField(name = "area")
        private String area;

        @JSONField(name = "level")
        private String level;

        @JSONField(name = "role")
        private String role;

        @JSONField(name = "title")
        private String title;

    }

}
