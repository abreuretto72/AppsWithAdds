package com.abreuretto.AppsWithAds.Model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Abreu on 25/09/2017.
 */

public class DadosApp  {

    private String ID;
    private String NomeApp;
    private String PackageName;
    private String SourceDir;
    private String TargetOsVersion;
    private String IconUri;
    private String Sistema;
    private String DataInstala;
    private String TamanhoFile;
    private String VersionName;
    private String VersionCode;
    private String ProcessName;
    private String PublicSourceDir;
    private String NativeLibDir;
    private String DTInclusao;
    private String DataUpdate;
    private ArrayList<Activities> dadosActivities;
    private ArrayList<Libs> dadosLib;
    private ArrayList<Permissao> dadosPermissao;

    public ArrayList<Activities> getDadosActivities() {
        return dadosActivities;
    }

    public void setDadosActivities(ArrayList<Activities> dadosActivities) {
        this.dadosActivities = dadosActivities;
    }

    public ArrayList<Libs> getDadosLib() {
        return dadosLib;
    }

    public void setDadosLib(ArrayList<Libs> dadosLib) {
        this.dadosLib = dadosLib;
    }

    public ArrayList<Permissao> getDadosPermissao() {
        return dadosPermissao;
    }

    public void setDadosPermissao(ArrayList<Permissao> dadosPermissao) {
        this.dadosPermissao = dadosPermissao;
    }








    public String getTargetOsVersion() {
        return TargetOsVersion;
    }

    public void setTargetOsVersion(String targetOsVersion) {
        TargetOsVersion = targetOsVersion;
    }

    public String getProcessName() {
        return ProcessName;
    }

    public void setProcessName(String processName) {
        ProcessName = processName;
    }

    public String getPublicSourceDir() {
        return PublicSourceDir;
    }

    public void setPublicSourceDir(String publicSourceDir) {
        PublicSourceDir = publicSourceDir;
    }

    public String getNativeLibDir() {
        return NativeLibDir;
    }

    public void setNativeLibDir(String nativeLibDir) {
        NativeLibDir = nativeLibDir;
    }

    public String getDataUpdate() {
        return DataUpdate;
    }

    public void setDataUpdate(String dataUpdate) {
        DataUpdate = dataUpdate;
    }


    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
    }



    public String getDTInclusao() {
        return DTInclusao;
    }

    public void setDTInclusao(String DTInclusao) {
        this.DTInclusao = DTInclusao;
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

    public String getSourceDir() {
        return SourceDir;
    }

    public void setSourceDir(String sourceDir) {
        SourceDir = sourceDir;
    }

    public String getTargetOSVersion() {
        return TargetOsVersion;
    }

    public void setTargetOSVersion(String targetOSVersion) {
        this.TargetOsVersion = targetOSVersion;
    }

    public String getIconUri() {
        return IconUri;
    }

    public void setIconUri(String iconUri) {
        IconUri = iconUri;
    }

    public String getSistema() {
        return Sistema;
    }

    public void setSistema(String sistema) {
        Sistema = sistema;
    }

    public String getDataInstala() {
        return DataInstala;
    }

    public void setDataInstala(String dataInstala) {
        DataInstala = dataInstala;
    }

    public String getTamanhoFile() {
        return TamanhoFile;
    }

    public void setTamanhoFile(String tamanhoFile) {
        TamanhoFile = tamanhoFile;
    }






}
