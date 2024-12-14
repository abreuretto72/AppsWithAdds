package com.abreuretto.AppsWithAds.Model;

/**
 * Created by Abreu on 29/09/2017.
 */

public class DadosPermissao
{

    private String ID;
    private String PackageName;
    private String Permissao;

    private String DTInclusao;

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

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getPermissao() {
        return Permissao;
    }

    public void setPermissao(String permissao) {
        Permissao = permissao;
    }




}
