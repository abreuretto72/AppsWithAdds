package com.abreuretto.AppsWithAds.Model;

/**
 * Created by Abreu on 05/10/2017.
 */

public class Permissao {


    private String NomePermissao;

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

    private String Portugues;
    private String Ingles;

    public String getNomePermissao() {
        return NomePermissao;
    }

    public void setNomePermissao(String nomePermissao) {
        NomePermissao = nomePermissao;
    }


}
