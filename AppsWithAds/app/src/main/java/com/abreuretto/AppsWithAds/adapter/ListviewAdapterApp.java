package com.abreuretto.AppsWithAds.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abreuretto.AppsWithAds.Banco.DB;
//import com.abreuretto.AppsWithAds.MainActivity;
import com.abreuretto.AppsWithAds.Model.DadosApp;
import com.abreuretto.AppsWithAds.Model.DadosPermissao;
import com.abreuretto.AppsWithAds.Model.Permissao;
import com.abreuretto.AppsWithAds.Model.TabPermissao;
import com.abreuretto.AppsWithAds.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import static java.lang.Math.acos;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.StrictMath.cos;


public class ListviewAdapterApp extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<DadosApp> listItem;
    private Context context;
    private PackageManager packageManager;
    private DB db;
    ArrayList<DadosPermissao>  permiLista = new ArrayList<DadosPermissao>();
    private String[] escolheicone = new String[17];


    public ListviewAdapterApp(Context context, ArrayList<DadosApp> listItem) {
        this.listItem = listItem;
        inflater = LayoutInflater.from(context);
        this.context = context;
        packageManager = context.getPackageManager();
        try {
            db = new DB(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    static class Holder {
        @InjectView(R.id.imgThumbnail)
        ImageView thumbnail;

        @InjectView(R.id.textTitle)
        TextView title;




        @InjectView(R.id.textRating)
        TextView rating;
        @InjectView(R.id.textCategories)
        TextView categories;
        @InjectView(R.id.textDistance)
        TextView distance;
        @InjectView(R.id.textAddress)
        TextView address;







        Holder(View v) {
            ButterKnife.inject(this, v);
        }
    }


    public static long getDistanceMeters(double lat1, double lng1, double lat2, double lng2) {

        double l1 = toRadians(lat1);
        double l2 = toRadians(lat2);
        double g1 = toRadians(lng1);
        double g2 = toRadians(lng2);
        double dist = acos(sin(l1) * sin(l2) + cos(l1) * cos(l2) * cos(g1 - g2));
        if(dist < 0) {
            dist = dist + Math.PI;
        }
        return Math.round(dist * 6378100);
    }



    public void inicializa(){

        for (int i=0; i<17;i++){




            escolheicone[i] = "";


        }

    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;




        if (view == null) {
            view = inflater.inflate(R.layout.item_listview, viewGroup, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        LinearLayout ratingCntr = (LinearLayout)view.findViewById(R.id.ratingCntr);
        holder.title.setText(listItem.get(i).getNomeApp());
        Picasso.with(context).load(listItem.get(i).getIconUri()).into(holder.thumbnail);
        holder.categories.setText(listItem.get(i).getTamanhoFile()+" MB");
        holder.address.setText("Instalação: "+listItem.get(i).getDataInstala());
        holder.distance.setText("    V: "+listItem.get(i).getVersionName());






        String permissao = listItem.get(i).getPackageName();


        permiLista.clear();

        permiLista.addAll(db.getPermissao(permissao));



        inicializa();

        if(ratingCntr != null && ratingCntr.getChildCount() == 0)
        {

            ratingCntr.removeAllViews();


            for (DadosPermissao permite : permiLista)
            {
                String ajustaPermissao = permite.getPermissao().replace("android.permission.","");
                TabPermissao tabPermissao = db.getTabPermissao(ajustaPermissao);

                if (tabPermissao.getGrupo() != null) {
                    String grupo = tabPermissao.getGrupo();
                    int indice = tabPermissao.getIndice();
                    escolheicone[indice] = escolheicone[indice] + grupo;
                }


            }

            for (int j=0; j<17;j++){


                if (escolheicone[j] != null)
                {
                    TabPermissao imageName = db.getTabPermissaoGrupo(escolheicone[j]);
                    if (imageName.getIconeName() != null)
                    {
                        Uri path = Uri.parse("android.resource://com.abreuretto.showappswithads/drawable/" + imageName.getIconeName());
                        ImageView iv = new ImageView(context);
                        Permissao permi = new Permissao();
                        permi.setNomePermissao(imageName.getIconeName());
                        permi.setPortugues(imageName.getPortugues());
                        permi.setIngles(imageName.getIngles());
                        iv.setTag(permi);
                        Picasso.with(context).load(path).into(iv);
                        ratingCntr.addView(iv);
                        iv.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {

                                if (view.getTag().toString() != null) {
                                    Permissao permi = new Permissao();
                                    String testando = view.getTag().toString();
                                    Permissao tag = (Permissao) view.getTag();
                                    Toast.makeText(context, "Permissão: " + tag.getPortugues(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }


                }


            }


/*
            for (DadosPermissao perm : permiLista)
            {
                String ajustaPermissao = perm.getPermissao().replace("android.permission.","");
                TabPermissao imageName = db.getTabPermissao(ajustaPermissao);
                if (imageName.getIconeName() != null)
                {
                    Uri path = Uri.parse("android.resource://com.abreuretto.showappswithads/drawable/" + imageName.getIconeName());
                    ImageView iv = new ImageView(context);
                    Permissao permi = new Permissao();
                    permi.setNomePermissao(imageName.getIconeName());
                    permi.setPortugues(imageName.getPortugues());
                    permi.setIngles(imageName.getIngles());
                    iv.setTag(permi);
                    Picasso.with(context).load(path).into(iv);
                    ratingCntr.addView(iv);
                    iv.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            Permissao permi = new Permissao();
                            String testando =  view.getTag().toString();

                            Permissao tag = (Permissao) view.getTag();

                            Toast.makeText(context, "Permissão: "+tag.getPortugues(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            */

        }





        return view;





        /*



        String endereco="";
        try {
            endereco = listItem.get(i).getVenue().getLocation().getAddress();
        }
        catch (Exception e)
        {
            endereco = "";
        }
        String cidade="";
        try {
            cidade = listItem.get(i).getVenue().getLocation().getCity();
        }
        catch (Exception e)
        {
            cidade = "";
        }
        String pais="";
        try {
            pais = listItem.get(i).getVenue().getLocation().getCountry();
        }
        catch (Exception e)
        {
            pais = "";
        }
        String ende="";
        if (!TextUtils.isEmpty(endereco))
        {
            ende = endereco+"@";
        }
        if (!TextUtils.isEmpty(cidade))
        {
            ende = ende+cidade+"@";
        }
        if (!TextUtils.isEmpty(pais))
        {
            ende = ende+pais;
        }

        if (listItem.get(i).getVenue().getCategories().size() > 0) {
        holder.categories.setText(listItem.get(i).getVenue().getCategories().get(0).getName());
       }
        holder.rating.setText("" + listItem.get(i).getVenue().getRating());
        double latx =  listItem.get(i).getVenue().getLocation().getLat();
        double lonx =  listItem.get(i).getVenue().getLocation().getLng();
        Long dis = getDistanceMeters(latx,lonx, lat,lon);
        double dista = ((double) dis / 1000);
        dista = listItem.get(i).getVenue().getLocation().getDistance();

        String endex = ende.replaceAll("@", " ");



        if (dista > 0.999) {
            DecimalFormat df = new DecimalFormat("#.###");
            String km = df.format(dista);
            holder.address.setText(endex+" - " + km + " km");
        } else
        {
            double distax = dista * 1000;
            DecimalFormat df = new DecimalFormat("###");
            String km = df.format(distax);
            holder.address.setText(endex+" - " + km + " m");
        }

        String colori = "B2DFDB";
        if (listItem.get(i).getVenue().getRatingColor() != null) {
            holder.rating.setBackgroundColor(((Color.parseColor("#" + colori))));
        }
        else
        {
            holder.rating.setBackgroundColor(((Color.parseColor("#" + colori))));
        }







        if (listItem.get(i).getVenue().getPhotos().getListGroups().size() > 0) {
            String prefix = listItem.get(i).getVenue().getPhotos().getListGroups().get(0).getListPhoto().get(0).getPrefix();
            String size = listItem.get(i).getVenue().getPhotos().getListGroups().get(0).getListPhoto().get(0).getWidth() + "x" + listItem.get(i).getVenue().getPhotos().getListGroups().get(0).getListPhoto().get(0).getHeight();
            String suffix = listItem.get(i).getVenue().getPhotos().getListGroups().get(0).getListPhoto().get(0).getSuffix();
            String urlPhoto = prefix + size + suffix;
            Picasso.with(context).load(urlPhoto).into(holder.thumbnail);
        }
        else
        {
            if (listItem.get(i).getVenue().getCategories().size() > 0) {
                String id = listItem.get(i).getVenue().getCategories().get(0).getId();
                String prefi = listItem.get(i).getVenue().getCategories().get(0).getIcon().getPrefix();
                String sufi = listItem.get(i).getVenue().getCategories().get(0).getIcon().getSuffix();
                String urlPhoto = null;
                String[] arquivo = BaseUtils.PegaArquivonaUrl(prefi);
                urlPhoto = "https://foursquare.com/img/categories_v2/" + arquivo[0] + "/" + arquivo[1] + "bg_64" + sufi;
                Log.d("Abreu - ", urlPhoto);
                Picasso.with(context).load(urlPhoto).into(holder.thumbnail);
            }
        }

        */



    }
}