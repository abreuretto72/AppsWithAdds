package com.abreuretto.AppsWithAds.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abreuretto.AppsWithAds.Banco.DB;
import com.abreuretto.AppsWithAds.Model.DadosAds;
import com.abreuretto.AppsWithAds.Model.DadosApp;
import com.abreuretto.AppsWithAds.Model.DadosLib;
import com.abreuretto.AppsWithAds.R;
import com.abreuretto.AppsWithAds.adapter.ListviewAdapterApp;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.io.IOException;
import java.util.ArrayList;

public class AppsFragment extends BaseFragment {
    private String tipo;
    // Método para instanciar esse fragment pelo tipo.
    public static AppsFragment newInstance(String tipo) {
        Bundle args = new Bundle();
        args.putString("tipo", tipo);
        AppsFragment f = new AppsFragment();
        f.setArguments(args);
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        ListView listView;
        ArrayList<DadosApp> listaApp = new ArrayList<DadosApp>();
        DB db;
        ArrayList<DadosLib> listadadosLibs = new ArrayList<DadosLib>();
        ListviewAdapterApp adapterApp;
        ArrayList<DadosAds> listaFiltro = new ArrayList<DadosAds>();
        PackageManager packageManager = null;
        CharSequence mTitle;
        ProgressBar pb2;
        TextView txtpb;
        LinearLayout rel;
        RoundCornerProgressBar pb;


        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Lê o tipo dos argumentos.
            this.tipo = getArguments().getString("tipo");

            packageManager = getPackageManager();
            pb = (RoundCornerProgressBar) findViewById(R.id.progress2);
            txtpb = (TextView) findViewById(R.id.txtpb);
            rel = (LinearLayout) findViewById(R.id.footer);
            listView = (ListView) findViewById(R.id.listview);
            try {
                db = new DB(AppsFragment.this);
            } catch (IOException e) {
                e.printStackTrace();
            }


            listaFiltro.addAll(db.getAds());
            listaApp.addAll(db.getApp());


        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {






        if (tipo.equals("AppsAds"))
        {
            View view = inflater.inflate(R.layout.fragment_apps, container, false);
            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText("Apps with Ads " + tipo);





            return view;
        }

        if (tipo.equals("AppsInstaled"))
        {

            View view = inflater.inflate(R.layout.fragment_apps, container, false);
            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText("AppsInstalleds " + tipo);
            return view;

        } else
        {
            View view = inflater.inflate(R.layout.fragment_apps, container, false);
            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText("Apps with Ads " + tipo);
            return view;
        }





    }


    public void IniciaAppsAds(){





        new AppsFragment().LoadApplications().execute();








    }







}
