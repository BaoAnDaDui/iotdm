package com.aibaixun.iotdm.business;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class MetaData {

    private String deviceId;

    private String productId;

    private volatile String tenantId;

    public MetaData(String deviceId, String productId) {
        this.deviceId = deviceId;
        this.productId = productId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}