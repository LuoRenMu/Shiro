package com.mikuac.shiro.handler.event;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.common.utils.EventUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.notice.GroupHonorChangeNoticeEvent;
import com.mikuac.shiro.dto.event.notice.GroupLuckyKingNoticeEvent;
import com.mikuac.shiro.dto.event.notice.PokeNoticeEvent;
import com.mikuac.shiro.enums.NotifyEventEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author zero
 */
@Component
public class NotifyEvent {

    private EventUtils utils;

    @Autowired
    public void setUtils(EventUtils utils) {
        this.utils = utils;
    }

    /**
     * 存储通知事件处理器
     */
    public final Map<String, BiConsumer<Bot, JSONObject>> handlers = new HashMap<>();

    /**
     * 通知事件分发
     *
     * @param bot  {@link Bot}
     * @param resp {@link JSONObject}
     */
    public void handler(Bot bot, JSONObject resp) {
        String type = resp.getString("sub_type");
        handlers.getOrDefault(type, (b, e) -> {
        }).accept(bot, resp);
    }

    /**
     * 事件处理
     *
     * @param bot  {@link Bot}
     * @param resp {@link JSONObject}
     * @param type {@link NotifyEventEnum}
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void process(Bot bot, JSONObject resp, NotifyEventEnum type) {
        if (type == NotifyEventEnum.POKE) {
            PokeNoticeEvent event = resp.to(PokeNoticeEvent.class);
            // 如果群号不为空则作为群内戳一戳处理
            if (event.getGroupId() > 0L) {
                bot.getPluginList().stream().anyMatch(o -> utils.getPlugin(o).onGroupPokeNotice(bot, event) == BotPlugin.MESSAGE_BLOCK);
            } else {
                bot.getPluginList().stream().anyMatch(o -> utils.getPlugin(o).onPrivatePokeNotice(bot, event) == BotPlugin.MESSAGE_BLOCK);
            }
        }

        if (type == NotifyEventEnum.HONOR) {
            GroupHonorChangeNoticeEvent event = resp.to(GroupHonorChangeNoticeEvent.class);
            bot.getPluginList().stream().anyMatch(o -> utils.getPlugin(o).onGroupHonorChangeNotice(bot, event) == BotPlugin.MESSAGE_BLOCK);
        }

        if (type == NotifyEventEnum.LUCKY_KING) {
            GroupLuckyKingNoticeEvent event = resp.to(GroupLuckyKingNoticeEvent.class);
            bot.getPluginList().stream().anyMatch(o -> utils.getPlugin(o).onGroupLuckyKingNotice(bot, event) == BotPlugin.MESSAGE_BLOCK);
        }
    }

    /**
     * 戳一戳事件
     *
     * @param bot  {@link Bot}
     * @param resp {@link JSONObject}
     */
    public void poke(Bot bot, JSONObject resp) {
        process(bot, resp, NotifyEventEnum.POKE);
    }

    /**
     * 抢红包运气王事件
     *
     * @param bot  {@link Bot}
     * @param resp {@link JSONObject}
     */
    public void luckyKing(Bot bot, JSONObject resp) {
        process(bot, resp, NotifyEventEnum.LUCKY_KING);
    }

    /**
     * 群荣誉变更事件
     *
     * @param bot  {@link Bot}
     * @param resp {@link JSONObject}
     */
    public void honor(Bot bot, JSONObject resp) {
        process(bot, resp, NotifyEventEnum.HONOR);
    }

}
