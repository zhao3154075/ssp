package com.cloudplatform;

public class Constants {
    public static final String appId="1_IuIHG5cZh1oSsOl3";
    public static final String key="cd3404ae275f7c66b4f57eb513a1e973";
    public static final String domain="http://open.8531.cn/home/";
    public static final String encKey = CloudPlatformApiUtil.MD5(CloudPlatformApiUtil.AUTHOR_KEY);
    public static final String encIv = CloudPlatformApiUtil.MD5(encKey);
}
