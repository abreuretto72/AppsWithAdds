package com.abreuretto.AppsWithAds.Model;

import android.content.pm.ApplicationInfo;

/**
 * Created by Abreu on 01/10/2017.
 */

public class ApplicationInfoAll
{

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    public Boolean getTem() {
        return tem;
    }

    public void setTem(Boolean tem) {
        this.tem = tem;
    }

    private ApplicationInfo  applicationInfo;
    private Boolean tem;

}
