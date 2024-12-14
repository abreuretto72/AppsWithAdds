package com.abreuretto.AppsWithAds.utils;


 import android.app.Activity;
 import android.app.Dialog;
 import android.app.ProgressDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.pm.ActivityInfo;
 import android.content.pm.ApplicationInfo;
 import android.content.pm.PackageInfo;
 import android.content.pm.PackageManager;
 import android.database.Cursor;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.graphics.Canvas;
 import android.graphics.Matrix;
 import android.graphics.Paint;
 import android.graphics.drawable.Drawable;
 import android.media.ExifInterface;
 import android.net.ConnectivityManager;
 import android.net.Uri;
 import android.os.Bundle;
 import android.os.Environment;
 import android.provider.MediaStore;
 import android.support.v7.app.AlertDialog;
 import android.util.Log;
 import android.view.View;
 import android.view.Window;
 import android.widget.Button;
 import android.widget.ImageView;
 import android.widget.ProgressBar;
 import android.widget.TextView;
 import android.widget.Toast;


 import com.abreuretto.AppsWithAds.Model.Activities;
 import com.abreuretto.AppsWithAds.Model.DadosActivities;
 import com.abreuretto.AppsWithAds.Model.DadosApp;
 import com.abreuretto.AppsWithAds.Model.DadosPermissao;
 import com.abreuretto.AppsWithAds.Model.Libs;
 import com.abreuretto.AppsWithAds.Model.Permissao;
 import com.abreuretto.AppsWithAds.R;
 import com.github.thunder413.datetimeutils.DateTimeUnits;
 import com.github.thunder413.datetimeutils.DateTimeUtils;
 import com.google.android.gms.common.ConnectionResult;
 import com.google.android.gms.common.GoogleApiAvailability;
 import com.google.android.gms.common.GooglePlayServicesUtil;
 import com.googlecode.d2j.reader.DexFileReader;
 import com.googlecode.d2j.visitors.DexFileVisitor;


 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.OutputStream;
 import java.security.Permission;
 import java.sql.Date;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Enumeration;
 import java.util.List;
 import java.util.jar.JarEntry;
 import java.util.jar.JarFile;
 import java.util.zip.ZipEntry;


/**
 * Created by Abreu on 26/08/2016.
 */


/* Como chamar
Utils utils = new Utils(this);
utils.isInternetConnection();
*/

//Blibliotecas interessantes
    //https://medium.freecodecamp.org/25-new-android-libraries-which-you-definitely-want-to-try-at-the-beginning-of-2017-45878d5408c0
// https://github.com/jrvansuita/MaterialAbout





public class Utils {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1;


    private Context context;

    public Utils(Context context) {
        this.context = context;

    }

    public boolean TemConexaoInternet() {
        boolean connected = false;
        try {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conn.getActiveNetworkInfo() != null
                    && conn.getActiveNetworkInfo().isAvailable()
                    && conn.getActiveNetworkInfo().isConnected()) {
                connected = true;
            }
        } catch (Exception e) {
            Log.e("ALUTAL", "Erro InternetCon " + e + " Read message " + e.getMessage());
        }
        return connected;
    }


    public static String FormataData(int Tipo, String Formato, java.util.Date datajava) {


        String data = null;
        java.util.Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(Formato);
        if (Tipo == 1) {


            data = formatter.format(today);
        }
        if (Tipo == 2) {
            data = formatter.format(datajava);
        }
        return data;
    }

    public int StrtoInt(String str) {
        int recebe = -1;
        try {
            recebe = Integer.parseInt(str.toString());
        } catch (NumberFormatException nfe) {
            recebe = -1;
        }
        return recebe;
    }

    public String InttoStr(int num) {
        String recebe = null;
        try {
            recebe = Integer.toString(num);
        } catch (NumberFormatException nfe) {
            recebe = null;
        }
        return recebe;
    }

    public String DoubletoStr(double num) {
        String recebe = null;
        try {
            recebe = Double.toString(num);
        } catch (NumberFormatException nfe) {
            recebe = null;
        }
        return recebe;
    }

    public double StrtoDouble(String num) {
        double recebe = -1;
        try {
            recebe = Double.parseDouble(num);
        } catch (NumberFormatException nfe) {
            recebe = -1;
        }
        return recebe;
    }


    public int CalculaTempo(Date startTime, Date endTime) {
        //int hours = p.getHours();
        //int minutes = p.getMinutes();

        int diff = DateTimeUtils.getDateDiff(startTime, endTime, DateTimeUnits.DAYS);


        return diff;

    }


    private static double CalculaDistancia(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }
        return (dist);
    }


    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    public static String ComprimeImagem(String imageUri, String Dire, String NomeArquivo) {

        String filePath = imageUri;
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename(Dire, NomeArquivo);
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public static String getFilename(String Dire, String NomeArquivo) {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), Dire);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + NomeArquivo + ".jpg");
        return uriSting;

    }

    /*
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

*/
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;

    }


    public static boolean CriaDiretorio(String tempDir) {
        File tempdir = new File(tempDir);

        if (!tempdir.exists())
            tempdir.mkdirs();

        return (tempdir.isDirectory());
    }


    public static int TemPlayServices(Context context) {

        int retorna = 0;


        int status;
        status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);


        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {

                retorna = 1;

            } else {

                retorna = 2;
                // Log.i(TAG, "This device is not supported.");
                // finish();
            }

        }
        return retorna;
    }


    public String ExibirMensagem(String titulo, String texto)
    {

        final String retorna = null;
        AlertDialog.Builder mensagem = new AlertDialog.Builder(context);
        mensagem.setTitle(titulo);
        mensagem.setMessage(texto);

        mensagem.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();


                    }
                });

        //mensagem.setNeutralButton("OK", null);
        mensagem.show();
        return retorna;
    }

    public static class NotificationDialog extends Dialog implements android.view.View.OnClickListener
    {
        String nMessage;
        TextView nMessageTv;
        Button okBtn;
        ImageView image;

        public NotificationDialog(Context context) {
            super(context);
            setupView();
            setupEvent();
        }

        public NotificationDialog(Context context, String message) {
            super(context);
            setMessage(message);
            setupView();
            setupEvent();
        }

        public NotificationDialog(Context context, String title, String message) {
            super(context);
            this.setNotificationTitle(title);
            this.setMessage(message);
            setupView();
            setupEvent();
        }

        public void setNotificationTitle(String title) {
            this.setTitle(title);
        }

        public void setMessage(String message) {
            this.nMessage = message;
            nMessageTv.setText(nMessage);
        }

        private void setupView() {
            this.setContentView(R.layout.notification);
            this.nMessageTv = (TextView) this.findViewById(R.id.notifDialog_text);
            this.okBtn = (Button) this.findViewById(R.id.notifDialog_ok);
            this.image = (ImageView) this.findViewById(R.id.imageDialog);
        }

        private void setupEvent() {
            this.okBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.notifDialog_ok:
                    this.dismiss();
                    break;
                default:
                    break;
            }
        }
    }










    public static class Progresso extends ProgressDialog implements android.view.View.OnClickListener
    {
        String nMessage;
        TextView nMessageTv;
        ProgressBar okBtn;

        public Progresso(Context context)
        {
            super(context);
            setupViewPB();
            setupEventPB();
        }

        public Progresso(Context context, String message) {
            super(context);
            setMessage(message);
            setupViewPB();
            setupEventPB();
        }

        public Progresso(Context context, String title, String message) {
            super(context);
            this.setNotificationTitle(title);
            this.setMessage(message);
            setupViewPB();
            setupEventPB();
        }

        public void setNotificationTitle(String title) {
            this.setTitle(title);
        }

        public void setMessage(String message) {
            this.nMessage = message;
            nMessageTv.setText(nMessage);
        }

        private void setupViewPB() {
            this.setContentView(R.layout.progresso);
            this.nMessageTv = (TextView) this.findViewById(R.id.editText);
            this.okBtn = (ProgressBar) this.findViewById(R.id.progressBar2);

        }

        private void setupEventPB() {
            this.okBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.notifDialog_ok:
                    this.dismiss();
                    break;
                default:
                    break;
            }
        }
    }





    private void ExecutaADB() {

        String command = "pm list packages";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        StringBuilder log = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line + "\n");

                // listaBiblio.add(line);

                //   Log.d("Bibliotecas", "Bibliotecas: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }





    public static ArrayList<DadosApp> checkForLaunchIntentPackage(Context context, List<ApplicationInfo> list, PackageManager packageManager)
    {

        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        ArrayList<DadosApp> applist = new ArrayList<DadosApp>();

        for (PackageInfo info : packages)
        {
            try {
                ApplicationInfo app = info.applicationInfo;
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
                        DadosApp dadosApp = new DadosApp();
                        dadosApp.setPackageName(app.packageName);
                        dadosApp.setNomeApp(app.loadLabel(packageManager).toString());
                        dadosApp.setSistema("N");
                        dadosApp.setSourceDir(app.sourceDir);
                        if (app.icon != 0) {
                            Uri uri = Uri.parse("android.resource://" + app.packageName + "/" + app.icon);
                            dadosApp.setIconUri(uri.toString());
                        }
                        dadosApp.setTargetOSVersion(pegaOS(app.targetSdkVersion));
                        dadosApp.setProcessName(app.processName);
                        dadosApp.setPublicSourceDir(app.publicSourceDir);
                        dadosApp.setNativeLibDir(app.nativeLibraryDir);
                        String dir = app.sourceDir;

                        long installed = new File(dir).lastModified();
                        String datainstalado = getInstallDate(installed, context);
                        dadosApp.setDataInstala(datainstalado);

                        String dataupdate = getInstallDate(info.lastUpdateTime, context);
                        dadosApp.setDataUpdate(dataupdate);

                        File file = new File(app.publicSourceDir);
                        long filesize = file.length();

                        double calculaKB = filesize / 1024;
                        double calculaMega = calculaKB / 1024;
                        String CauculaFinal = String.format("%.3f", calculaMega);
                        dadosApp.setTamanhoFile(CauculaFinal);

                        ArrayList<Activities> ListaActivities = PegaActivities(app.packageName, packageManager);
                        ArrayList<Libs> libs = PegaDex(app.publicSourceDir);
                        ArrayList<Permissao> ListaPermissao = PegaPermissao(packageManager, app.packageName);
                        dadosApp.setDadosPermissao(ListaPermissao);
                        dadosApp.setDadosLib(libs);
                        dadosApp.setDadosActivities(ListaActivities);
                        applist.add(dadosApp);
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applist;
    }


    public static String pegaOS(int Versao)
    {
        String os = null;
        switch (Versao) {
            case 1:
                os = "Android 1.0(BASE)";
                break;
            case 2:
                os = "Android 1.1(BASE_1_1)";
                break;
            case 3:
                os = "Android 1.5(CUPCAKE)";
                break;
            case 4:
                os = "Android 1.6(DONUT)";
                break;
            case 5:
                os = "Android 2.0(ECLAIR)";
                break;
            case 6:
                os = "Android 2.0.1(ECLAIR_0_1)";
                break;
            case 7:
                os = "Android 2.1(ECLAIR_MR1)";
                break;
            case 8:
                os = "Android 2.2(FROYO)";
                break;
            case 9:
                os = "Android 2.3(GINGERBREAD)";
                break;
            case 10:
                os = "Android 2.3.3(GINGERBREAD_MR1)";
                break;
            case 11:
                os = "Android 3.0(HONEYCOMB)";
                break;
            case 12:
                os = "Android 3.1(HONEYCOMB_MR1)";
                break;
            case 13:
                os = "Android 3.2(HONEYCOMB_MR2)";
                break;
            case 14:
                os = "Android 4.0(ICE_CREAM_SANDWICH)";
                break;
            case 15:
                os = "Android 4.0.3(ICE_CREAM_SANDWICH_MR1)";
                break;
            case 16:
                os = "Android 4.1(JELLY_BEAN)";
                break;
            case 17:
                os = "Android 4.2(JELLY_BEAN_MR1)";
                break;
            case 18:
                os = "Android 4.3(JELLY_BEAN)";
                break;
            case 19:
                os = "Android 4.4(KITKAT)";
                break;
            case 20:
                os = "Android 4.4W(KITKATWEAR)";
                break;
            case 21:
                os = "Android 5.0(LOLLYPOP)";
                break;
            case 22:
                os = "Android 5.1(LOLLYPOP)";
                break;
            case 23:
                os = "Android 6.0(Marshmalow)";
                break;
            case 24:
                os = "Android 7.0(NOUGET)";
                break;
            case 25:
                os = "Android 7.1.1(NOUGET)";
                break;
            case 26:
                os = "Android 8(O)";
                break;
        }

        return os;

    }


    public static ArrayList<Activities> PegaActivities(String PackageName, PackageManager packageManager) {

        ArrayList<Activities> lista = new ArrayList<Activities>();
        ActivityInfo[] activityInfo = new ActivityInfo[0];
        try {
            activityInfo = packageManager.getPackageInfo(PackageName, PackageManager.GET_ACTIVITIES).activities;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (activityInfo != null) {
            for (int h = 0; h < activityInfo.length; h++) {
                Activities activities = new Activities();
                activities.setNomeAtividade(activityInfo[h].name);
                lista.add(activities);
            }
        }

        return lista;
    }


    public static String getInstallDate(long installTimeInMilliseconds, Context context) {

        java.util.Date dateTime = new java.util.Date((long) installTimeInMilliseconds);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        String formattedDtm = dateFormat.format(dateTime);
        return formattedDtm;
    }


    public static ArrayList<Libs> PegaDex(String Dir)
    {

        String apkPath = Dir;
        JarFile containerJar = null;
        ArrayList<String> arqsDex = new ArrayList<String>();
        ArrayList<Libs> listaBiblio = new ArrayList<Libs>();
        ArrayList<String> listaLib = new ArrayList<String>();

        try
        {


            // Open the apk container as a jar..
            containerJar = new JarFile(apkPath);
            Enumeration<JarEntry> entra = containerJar.entries();

            while (entra.hasMoreElements()) {
                JarEntry jarEntry = entra.nextElement();
                String path = jarEntry.toString();
                if (path.endsWith(".dex")) {
                    arqsDex.add(path);
                }
            }

            ZipEntry ze = null;
            for (int h = 0; h < arqsDex.size(); h++) {
                ze = containerJar.getEntry(arqsDex.get(h));
                DexFileVisitor visitor = new DexFileVisitor();
                DexFileReader reader = new DexFileReader(containerJar.getInputStream(ze));
                List<String> teste2 = reader.getClassNames();
                for (int j = 0; j < teste2.size(); j++) {
                    String monta = teste2.get(j);
                    int achou = monta.lastIndexOf("/");
                    if (achou > -1) {
                        String montax = monta.substring(1, achou);
                        if (listaLib.indexOf(montax) == -1) {
                            Libs libs = new Libs();
                            libs.setNomeLibs(montax);
                            listaLib.add(montax);
                            listaBiblio.add(libs);
                        }

                    }
                }
            }



        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (containerJar != null)
                try
                {
                    //return listaBiblio;
                    containerJar.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }

        return listaBiblio;

    }

public static ArrayList<Permissao> PegaPermissao(PackageManager packageManager, String PackageName )
{
    ArrayList<Permissao> listaPermisao = new ArrayList<Permissao>();
    try {
        PackageInfo packageInfo = packageManager.getPackageInfo(PackageName, PackageManager.GET_PERMISSIONS);
        String[] requestedPermissions = packageInfo.requestedPermissions;
        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1)
        {

            if (null != packageManager.getLaunchIntentForPackage(packageInfo.applicationInfo.packageName)) {

                if (requestedPermissions != null) {
                    for (int j = 0; j < requestedPermissions.length; j++)
                    {
                        Permissao permi = new Permissao();

                        permi.setNomePermissao(requestedPermissions[j]);
                        listaPermisao.add(permi);
                        // Log.d("ABREU_RETTO", "Permisao: " + permi.getPackageName() + " Permissao: " + permi.getPermissao());
                    }
                }
            }
        }
    } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
    }
    return listaPermisao;
}






    public static void CopiaDatabase(Context context){

        String DB_PATH=null;
        String dirsource=null;
        InputStream in = null;
        OutputStream out = null;

        if(android.os.Build.VERSION.SDK_INT >= 4.2)
        {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        dirsource = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();







        try {

            File dir = new File (dirsource);

            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(DB_PATH + "showappswithads.db");
            out = new FileOutputStream(dirsource +File.separator+ "showappswithads.db");

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }




    }

    public static String ConverteLongToDateTime(long datetime) {

        long ms = datetime;
        long segundos = ms / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        long horas = minutos / 60;
        minutos = minutos % 60;
      //  long dias = horas / 24;
      //  horas = horas % 24;


        String tempo2 = String.format("%02d:%02d:%02d", horas, minutos, segundos);
        return tempo2;
    }



    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        DateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}





