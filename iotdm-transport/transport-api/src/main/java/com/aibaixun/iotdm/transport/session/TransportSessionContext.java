package com.aibaixun.iotdm.transport.session;

import com.aibaixun.iotdm.msg.DeviceInfo;
import com.aibaixun.iotdm.msg.TransportSessionInfo;

import java.util.UUID;

/**
 * 连接session 上下文
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public interface TransportSessionContext {

    /**
     * 获取连接session id
     * @return uuid
     */
    UUID getSessionId ();


    /**
     * 下一条消息id
     * @return int
     */
    int nextMsgId();


    /**
     * 设备更新
     * @param sessionInfo session 信息
     * @param deviceInfo 设备信息
     */
    void onDeviceUpdate(TransportSessionInfo sessionInfo, DeviceInfo deviceInfo);
}