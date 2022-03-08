package com.aibaixun.iotdm.transport.limits;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 远程 地址 限制 状态
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class RemoteAddressLimitStats {

    private final Lock lock = new ReentrantLock();

    private boolean blocked;
    private long lastActivityTs;
    private int failureCount;
    private int connectionsCount;

    public Lock getLock() {
        return lock;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public long getLastActivityTs() {
        return lastActivityTs;
    }

    public void setLastActivityTs(long lastActivityTs) {
        this.lastActivityTs = lastActivityTs;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public int getConnectionsCount() {
        return connectionsCount;
    }

    public void setConnectionsCount(int connectionsCount) {
        this.connectionsCount = connectionsCount;
    }
}
