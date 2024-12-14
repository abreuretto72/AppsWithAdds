package com.abreuretto.AppsWithAds.Model;

/**
 * Created by Abreu on 13/10/2017.
 */

public class TabPermissao
{
    String Permissao;
    String Portugues;
    String Ingles;
    String IconeName;

    public String getGrupo() {
        return Grupo;
    }

    public void setGrupo(String grupo) {
        Grupo = grupo;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    String Grupo;
    int indice;

    public String getPermissao() {
        return Permissao;
    }

    public void setPermissao(String permissao) {
        Permissao = permissao;
    }

    public String getPortugues() {
        return Portugues;
    }

    public void setPortugues(String portugues) {
        Portugues = portugues;
    }

    public String getIngles() {
        return Ingles;
    }

    public void setIngles(String ingles) {
        Ingles = ingles;
    }

    public String getIconeName() {
        return IconeName;
    }

    public void setIconeName(String iconeName) {
        IconeName = iconeName;
    }




}
