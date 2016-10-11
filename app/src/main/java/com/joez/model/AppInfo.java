package com.joez.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
@Data
@Accessors(prefix = "m")
public class AppInfo implements Comparable<Object> {
    long mLastUpdateTime;
    String mName;
    String mIcon;

    public AppInfo(String nName, long lastUpdateTime, String icon) {
        mName = nName;
        mIcon = icon;
        mLastUpdateTime = lastUpdateTime;
    }

    @Override
    public int compareTo(Object another) {
        AppInfo f = (AppInfo)another;
        return getName().compareTo(f.getName());
    }

}
