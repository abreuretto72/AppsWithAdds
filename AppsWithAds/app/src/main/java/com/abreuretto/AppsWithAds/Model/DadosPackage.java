package com.abreuretto.AppsWithAds.Model;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

/**
 * Created by Abreu on 25/09/2017.
 */

public class DadosPackage {

    private String ID;
    private String NomeApp;
    private String PackageName;
    private String TemIntent;

    private String DTinclusao;

    public String getTemIntent() {
        return TemIntent;
    }

    public void setTemIntent(String temIntent) {
        TemIntent = temIntent;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNomeApp() {
        return NomeApp;
    }

    public void setNomeApp(String nomeApp) {
        NomeApp = nomeApp;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getDTinclusao() {
        return DTinclusao;
    }

    public void setDTinclusao(String DTinclusao) {
        this.DTinclusao = DTinclusao;
    }








}

