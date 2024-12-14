package com.abreuretto.AppsWithAds.Model;

/**
 * Created by Abreu on 11/10/2017.
 */

public class Servico {

    String NomeProcesso;
    int flag;
    String NomeServico;
    String NomedaClasse;
    Boolean ServicoStartado;
    String DataInicio;
    String DataFinal;
    String DataUltimaUtilizado;
    String TempoUsado;

    public String getNomeProcesso() {
        return NomeProcesso;
    }

    public void setNomeProcesso(String nomeProcesso) {
        NomeProcesso = nomeProcesso;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getNomeServico() {
        return NomeServico;
    }

    public void setNomeServico(String nomeServico) {
        NomeServico = nomeServico;
    }

    public String getNomedaClasse() {
        return NomedaClasse;
    }

    public void setNomedaClasse(String nomedaClasse) {
        NomedaClasse = nomedaClasse;
    }

    public Boolean getServicoStartado() {
        return ServicoStartado;
    }

    public void setServicoStartado(Boolean servicoStartado) {
        ServicoStartado = servicoStartado;
    }

    public String getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(String dataInicio) {
        DataInicio = dataInicio;
    }

    public String getDataFinal() {
        return DataFinal;
    }

    public void setDataFinal(String dataFinal) {
        DataFinal = dataFinal;
    }

    public String getDataUltimaUtilizado() {
        return DataUltimaUtilizado;
    }

    public void setDataUltimaUtilizado(String dataUltimaUtilizado) {
        DataUltimaUtilizado = dataUltimaUtilizado;
    }

    public String getTempoUsado() {
        return TempoUsado;
    }

    public void setTempoUsado(String tempoUsado) {
        TempoUsado = tempoUsado;
    }




}
