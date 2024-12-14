package com.abreuretto.AppsWithAds.Banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.abreuretto.AppsWithAds.Model.Activities;
import com.abreuretto.AppsWithAds.Model.DadosActivities;
import com.abreuretto.AppsWithAds.Model.DadosAds;
import com.abreuretto.AppsWithAds.Model.DadosApp;
import com.abreuretto.AppsWithAds.Model.DadosLib;
import com.abreuretto.AppsWithAds.Model.DadosPackage;
import com.abreuretto.AppsWithAds.Model.DadosPermissao;
import com.abreuretto.AppsWithAds.Model.Libs;
import com.abreuretto.AppsWithAds.Model.Permissao;
import com.abreuretto.AppsWithAds.Model.TabPermissao;
import com.abreuretto.AppsWithAds.Utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by abreuretto on 29/01/2015.
 */
public class DB extends SQLiteOpenHelper
{
    private static final int REQUEST_PERMISSIONS_CODE = 200;
    private static String DB_PATH = "/data/data/com.abreuretto.AppsWithAds/databases/";
    private Context context;
    private static String DB_NAME = "showappswithads.db";
    public SQLiteDatabase myDataBase;


    public DB(Context context) throws IOException {
        super(context,DB_NAME,null,1);
        this.context=context;


//context.deleteDatabase(DB_NAME);


       // ("/data/data/com.ronda.abreuretto.ronda/databases/sekron.sqlite");



        if(android.os.Build.VERSION.SDK_INT >= 4.2)
        {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }



        boolean dbexist = checkdatabase();
        if (dbexist) {
            //If the database exists, open it
            try {
                opendatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //else create new database
            createdatabase();
        }
    }



    public void createdatabase() throws IOException {

        this.getReadableDatabase();
        try {
            copydatabase();
        } catch(IOException e) {
            throw new Error("Error copying database");
        }
    }


    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }






    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
       // String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(DB_PATH+"showappswithads.db");

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //now clear and close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();

    }



    public void copydatabaseVolta() throws IOException {
        //Open your local db as the input stream


        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();







        //copyDatabase(context, "showappswithads.db");



    }


    public static void copyDatabase(Context c, String DATABASE_NAME) {
        String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
        File f = new File(databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        Log.d("testing", " testing db path " + databasePath);
        Log.d("testing", " testing db exist " + f.exists());

        if (f.exists()) {
            try {

                File directory = new File("/mnt/sdcard/DB_DEBUG");
                if (!directory.exists())
                    directory.mkdir();

                myOutput = new FileOutputStream(directory.getAbsolutePath()
                        + "/" + DATABASE_NAME);
                myInput = new FileInputStream(databasePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
            } catch (Exception e) {
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }



    public void copyFile(String inputPath, String inputFile, String outputPath) {




        InputStream in = null;
        OutputStream out = null;

        boolean externalStorageWritable = isExternalStorageWritable();
        File file = new File(outputPath);
        boolean canWrite = file.canWrite();
        boolean isFile = file.isFile();
        long usableSpace = file.getUsableSpace();

        Log.d("ABREURETTO", "externalStorageWritable: " + externalStorageWritable);
        Log.d("ABREURETTO", "filePath: " + outputPath);
        Log.d("ABREURETTO", "canWrite: " + canWrite);
        Log.d("ABREURETTO", "isFile: " + isFile);
        Log.d("ABREURETTO", "usableSpace: " + usableSpace);








        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath +File.separator+ "showappswithads.db");

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



    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // constructor


    //
    // PUBLIC METHODS TO ACCESS DB CONTENT
    //

    public  boolean setApp(ArrayList<DadosApp> tudo) {

        ArrayList<DadosApp> cb = new ArrayList<DadosApp>();
        boolean retorna = true;
        try {
            cb.addAll(tudo);
            SQLiteDatabase db = this.getWritableDatabase();
            for(int i=0;i<cb.size();i++)
            {
                DadosApp app = cb.get(i);

                int conta = getAppCount(app.getPackageName());
                if (conta == 0)
                {
                    ContentValues insertValues = new ContentValues();
                    insertValues.put("NomeApp", app.getNomeApp());
                    insertValues.put("PackageName", app.getPackageName());
                    insertValues.put("SourceDir", app.getSourceDir());
                    insertValues.put("targetSdkVersion", app.getTargetOsVersion());
                    insertValues.put("IconUri", app.getIconUri());
                    insertValues.put("Sistema", app.getSistema());
                    insertValues.put("DataInstala", app.getDataInstala());
                    insertValues.put("TamanhoFile", app.getTamanhoFile());
                    insertValues.put("VersionName", app.getVersionName());
                    insertValues.put("VersionCode", app.getVersionCode());
                    insertValues.put("ProcessName", app.getProcessName());
                    insertValues.put("PublicSourceDir", app.getPublicSourceDir());
                    insertValues.put("NativeLibDir", app.getNativeLibDir());
                    String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
                    app.setDTInclusao(data);
                    insertValues.put("DTInclusao", app.getDTInclusao());
                    insertValues.put("DataUpdate", app.getDataUpdate());
                    db.insert("Cad_App", null, insertValues);
                }
            }
            db.close();
        }
        catch(Exception e)
        {

            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }
        return retorna;
    }



    public ArrayList<DadosApp> getApp() {

        ArrayList<DadosApp> cb = new ArrayList<DadosApp>();

        try {
            String query  = "SELECT * FROM Cad_App";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    DadosApp app  = new DadosApp();
                    app.setID(cursor.getString(0));
                    app.setNomeApp(cursor.getString(1));
                    app.setPackageName(cursor.getString(2));
                    app.setSourceDir(cursor.getString(3));
                    app.setTargetOSVersion(cursor.getString(4));
                    app.setIconUri(cursor.getString(5));
                    app.setSistema(cursor.getString(6));
                    app.setDataInstala(cursor.getString(7));
                    app.setTamanhoFile(cursor.getString(8));
                    app.setVersionName(cursor.getString(9));
                    app.setVersionCode(cursor.getString(10));
                    app.setProcessName(cursor.getString(11));
                    app.setPublicSourceDir(cursor.getString(12));
                    app.setNativeLibDir(cursor.getString(13));
                    app.setDTInclusao(cursor.getString(14));
                    app.setDataUpdate(cursor.getString(15));
                    cb.add(app);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return cb;
    }


    public int getAppCount (String PackageName) {

        int retorno = 0;


        try {
            String query  = "SELECT Count(*) FROM Cad_App where PackageName = '"+PackageName+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }



    public boolean deleteAllApp () {
        boolean retorna = true;
        try {
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            db.delete("Cad_App", null, null);
            db.close();
        }
        catch(Exception e)
        {
            retorna = false;
        }
        return retorna;
    }





    //-------------------------------------------------------------------------------------


    public  boolean setPackage(ArrayList<DadosPackage> tudo) {

        ArrayList<DadosPackage> cb = new ArrayList<DadosPackage>();
        boolean retorna = true;
        try {
            cb.addAll(tudo);
            SQLiteDatabase db = this.getWritableDatabase();
            for(int i=0;i<cb.size();i++)
            {
                DadosPackage app = cb.get(i);

                int conta = getPackageCount(app.getNomeApp(), app.getPackageName());
                if (conta == 0)
                {
                    ContentValues insertValues = new ContentValues();
                    insertValues.put("NomeApp", app.getNomeApp());
                    insertValues.put("PackageName", app.getPackageName());
                    insertValues.put("TemIntent", app.getTemIntent());

                    String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
                    app.setDTinclusao(data);
                    insertValues.put("DTInclusao", app.getDTinclusao());
                    db.insert("Cad_Package", null, insertValues);
                }
            }
            db.close();
        }
        catch(Exception e)
        {

            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }

        return retorna;
    }



    public ArrayList<DadosPackage> getPackage () {

        ArrayList<DadosPackage> cb = new ArrayList<DadosPackage>();

        try {
            String query  = "SELECT * FROM Cad_Package";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    DadosPackage app  = new DadosPackage();
                    app.setID(cursor.getString(0));
                    app.setNomeApp(cursor.getString(1));
                    app.setPackageName(cursor.getString(2));
                    app.setTemIntent(cursor.getString(3));

                    app.setDTinclusao(cursor.getString(4));
                    cb.add(app);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return cb;
    }


    public int getPackageCount (String Nome, String PackageName) {

        int retorno = 0;


        try {
            String query  = "SELECT Count(*) FROM Cad_Package where NomeApp = '"+Nome+"' and   PackageName = '"+PackageName+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }



    public boolean deleteAllPackage () {
        boolean retorna = true;
        try {
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            db.delete("Cad_Package", null, null);
            db.close();
        }
        catch(Exception e)
        {
            retorna = false;
        }
        return retorna;
    }






//-------------------------------------------------------------------------------------------------------------



    public  boolean setLibs(ArrayList<DadosApp> tudo) {

        ArrayList<DadosApp> cb = new ArrayList<DadosApp>();
        boolean retorna = true;
        try {
            cb.addAll(tudo);
            SQLiteDatabase db = this.getWritableDatabase();
            for(int i=0;i<cb.size();i++)
            {
                DadosApp app = cb.get(i);
                ArrayList<Libs> lib = app.getDadosLib();

                for(int j=0;j<lib.size();j++)
                {
                int conta = getLibsCount(app.getPackageName(), lib.get(j).getNomeLibs());
                if (conta == 0)
                {
                    ContentValues insertValues = new ContentValues();
                    insertValues.put("PackageName", app.getPackageName());
                    insertValues.put("LibName", lib.get(j).getNomeLibs());
                    String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
                    app.setDTInclusao(data);
                    insertValues.put("DTInclusao", app.getDTInclusao());
                    db.insert("Cad_Libs", null, insertValues);
                }
                }
            }
            db.close();


        }
        catch(Exception e)
        {

            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }

        return retorna;
    }



    public ArrayList<DadosLib> getLibs () {

        ArrayList<DadosLib> cb = new ArrayList<DadosLib>();

        try {
            String query  = "SELECT * FROM Cad_Libs";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    DadosLib app  = new DadosLib();
                    app.setID(cursor.getString(0));
                    app.setPackageName(cursor.getString(1));
                    app.setLibName(cursor.getString(2));
                    app.setDTInclusao(cursor.getString(3));
                    cb.add(app);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return cb;
    }


    public int getLibsCount (String PackageName, String LibName) {

        int retorno = 0;


        try {
            String query  = "SELECT Count(*) FROM Cad_Libs where PackageName = '"+PackageName+"' and LibName = '"+LibName+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }



    public boolean deleteAllLibs() {
        boolean retorna = true;
        try {
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            db.delete("Cad_Libs", null, null);
            db.close();
        }
        catch(Exception e)
        {
            retorna = false;
        }
        return retorna;
    }




//--------------------------------------------------------------------------------------------------------


    public  boolean setPermissao(ArrayList<DadosApp> tudo) {

        ArrayList<DadosApp> cb = new ArrayList<DadosApp>();
        boolean retorna = true;
        try {
            cb.addAll(tudo);
            SQLiteDatabase db = this.getWritableDatabase();
            for(int i=0;i<cb.size();i++)
            {
                DadosApp app = cb.get(i);
                ArrayList<Permissao> permissao = app.getDadosPermissao();


                for(int j=0;j<permissao.size();j++)
                {
                int conta = getPermissaoCount(app.getPackageName(), permissao.get(j).getNomePermissao());
                if (conta == 0)
                {
                    ContentValues insertValues = new ContentValues();
                    insertValues.put("PackageName", app.getPackageName());
                    insertValues.put("Permissao", permissao.get(j).getNomePermissao());
                    String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
                    app.setDTInclusao(data);
                    insertValues.put("DTInclusao", app.getDTInclusao());
                    db.insert("Cad_Permissao", null, insertValues);
                }
            }}
            db.close();
        }
        catch(Exception e)
        {

            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }

        return retorna;
    }



    public ArrayList<DadosPermissao> getPermissao(String pm) {

        ArrayList<DadosPermissao> cb = new ArrayList<DadosPermissao>();

        try {
            String query  = "SELECT * FROM Cad_Permissao where PackageName = '"+pm+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    DadosPermissao app  = new DadosPermissao();
                    app.setID(cursor.getString(0));
                    app.setPackageName(cursor.getString(1));
                    app.setPermissao(cursor.getString(2));
                    app.setDTInclusao(cursor.getString(3));
                    cb.add(app);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return cb;
    }


    public int getPermissaoCount (String PackageName, String Permissao) {

        int retorno = 0;


        try {
            String query  = "SELECT Count(*) FROM Cad_Permissao where PackageName = '"+PackageName+"' and Permissao = '"+Permissao+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }



    public boolean deleteAllPermissao() {
        boolean retorna = true;
        try {
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            db.delete("Cad_Permissao", null, null);
            db.close();
        }
        catch(Exception e)
        {
            retorna = false;
        }
        return retorna;
    }





//---------------------------------------------------------------------------------------------------
    public  boolean setActivities(ArrayList<DadosApp> tudo)
    {
        ArrayList<DadosApp> cb = new ArrayList<DadosApp>();
        boolean retorna = true;
        try
        {
            cb.addAll(tudo);
            SQLiteDatabase db = this.getWritableDatabase();

            for(int i=0;i<cb.size();i++)
            {
                DadosApp app = cb.get(i);
                ArrayList<Activities> lib = app.getDadosActivities();

                for(int j=0;j<lib.size();j++)
                {
                    int conta = getActivitiesCount(app.getPackageName(), lib.get(j).getNomeAtividade());
                    if (conta == 0)
                    {
                        ContentValues insertValues = new ContentValues();
                        insertValues.put("PackageName", app.getPackageName());
                        insertValues.put("ActivityName", lib.get(j).getNomeAtividade());
                        String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
                        app.setDTInclusao(data);
                        insertValues.put("DTInclusao", app.getDTInclusao());
                        db.insert("Cad_Activities", null, insertValues);
                    }
                }
            }
            db.close();
        }
        catch(Exception e)
        {

            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }

        return retorna;
    }





    public int getActivitiesCount (String PackageName, String activit) {

        int retorno = 0;


        try {
            String query  = "SELECT Count(*) FROM Cad_Activities where PackageName = '"+PackageName+"' and ActivityName = '"+activit+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }




    //------------------------------------------------------------------------------------------
    public  boolean setLibsOne(DadosLib tudo)
    {
        boolean retorna = true;
        try {

            SQLiteDatabase db = this.getWritableDatabase();

                    int conta = getLibsCountOne(tudo.getPackageName(), tudo.getLibName());
                    if (conta == 0)
                    {
                        ContentValues insertValues = new ContentValues();
                        insertValues.put("PackageName", tudo.getPackageName());
                        insertValues.put("LibName", tudo.getLibName());
                        String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
                        tudo.setDTInclusao(data);
                        insertValues.put("DTInclusao", tudo.getDTInclusao());
                        db.insert("Cad_Libs", null, insertValues);
                    }
                    else
                    {
                        updateLibsOne(tudo);
                    }


            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }
        return retorna;
    }

    public int getLibsCountOne (String PackageName, String LibName) {

        int retorno = 0;
        try {
            String query  = "SELECT Count(*) FROM Cad_Libs where PackageName = '"+PackageName+"' and LibName = '"+LibName+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }




    public boolean updateLibsOne(DadosLib tudo) {
        boolean retorna = true;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String packname = tudo.getPackageName().toString();
                String libname =  tudo.getLibName().toString();
                ContentValues insertValues = new ContentValues();
                insertValues.put("PackageName", tudo.getPackageName());
                insertValues.put("LibName", tudo.getLibName());
                db.update("Cad_Libs", insertValues, "PackageName = '"+packname+"' AND LibName = '"+libname+"'", null);
                db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }
        return retorna;
    }








    //------------------------------------------------------------------------------------------
    public  boolean setPermissaoOne(DadosPermissao tudo)
    {
        boolean retorna = true;
        try {

            SQLiteDatabase db = this.getWritableDatabase();

            int conta = getPermissaoCountOne(tudo.getPackageName(), tudo.getPermissao());
            if (conta == 0)
            {
                ContentValues insertValues = new ContentValues();
                insertValues.put("PackageName", tudo.getPackageName());
                insertValues.put("Permissao", tudo.getPermissao());
                String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
                tudo.setDTInclusao(data);
                insertValues.put("DTInclusao", tudo.getDTInclusao());
                db.insert("Cad_Permissao", null, insertValues);
            }
            else
            {
                updatePermissaoOne(tudo);
            }


            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }
        return retorna;
    }

    public int getPermissaoCountOne (String PackageName, String Permissao) {

        int retorno = 0;
        try {
            String query  = "SELECT Count(*) FROM Cad_Permissao where PackageName = '"+PackageName+"' and Permissao = '"+Permissao+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }





    public boolean updatePermissaoOne(DadosPermissao tudo) {
        boolean retorna = true;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String packname = tudo.getPackageName().toString();
            String permissao =  tudo.getPermissao().toString();
            ContentValues insertValues = new ContentValues();
            insertValues.put("PackageName", tudo.getPackageName());
            insertValues.put("Permissao", tudo.getPermissao());
            db.update("Cad_Permissao", insertValues, "PackageName = '"+packname+"' AND Permissao = '"+permissao+"'", null);
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }
        return retorna;
    }












    //------------------------------------------------------------------------------------------
    public  boolean setActivitiesOne(DadosActivities tudo)
    {
        boolean retorna = true;
        try {

            SQLiteDatabase db = this.getWritableDatabase();

            int conta = getActivitiesCountOne(tudo.getPackageName(), tudo.getActivityName());
            if (conta == 0)
            {
                ContentValues insertValues = new ContentValues();
                insertValues.put("PackageName", tudo.getPackageName());
                insertValues.put("ActivityName", tudo.getActivityName());
                String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
                tudo.setDTInclu(data);
                insertValues.put("DTInclusao", tudo.getDTInclu());
                db.insert("Cad_Activities", null, insertValues);
            }
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }
        return retorna;
    }

    public int getActivitiesCountOne (String PackageName, String Activities) {

        int retorno = 0;
        try {
            String query  = "SELECT Count(*) FROM Cad_Activities where PackageName = '"+PackageName+"' and ActivityName = '"+Activities+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }


//-------------------------------------------------------------------------------------------------------

    public  boolean setAppOne(DadosApp tudo) {


        boolean retorna = true;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

                int conta = getAppOneCount(tudo.getPackageName());
                if (conta == 0)
                {
                    ContentValues insertValues = new ContentValues();
                    insertValues.put("NomeApp", tudo.getNomeApp());
                    insertValues.put("PackageName", tudo.getPackageName());
                    insertValues.put("SourceDir", tudo.getSourceDir());
                    insertValues.put("targetSdkVersion", tudo.getTargetOsVersion());
                    insertValues.put("IconUri", tudo.getIconUri());
                    insertValues.put("Sistema", tudo.getSistema());
                    insertValues.put("DataInstala", tudo.getDataInstala());
                    insertValues.put("TamanhoFile", tudo.getTamanhoFile());
                    insertValues.put("VersionName", tudo.getVersionName());
                    insertValues.put("VersionCode", tudo.getVersionCode());
                    insertValues.put("ProcessName", tudo.getProcessName());
                    insertValues.put("PublicSourceDir", tudo.getPublicSourceDir());
                    insertValues.put("NativeLibDir", tudo.getNativeLibDir());
                    String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
                    tudo.setDTInclusao(data);
                    insertValues.put("DTInclusao", tudo.getDTInclusao());
                    insertValues.put("DataUpdate", tudo.getDataUpdate());
                    db.insert("Cad_App", null, insertValues);
                }
                else
                {
                    updateAppOne(tudo);
                }


            db.close();
        }
        catch(Exception e)
        {

            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }
        return retorna;
    }



    public int getAppOneCount (String PackageName) {

        int retorno = 0;


        try {
            String query  = "SELECT Count(*) FROM Cad_App where PackageName = '"+PackageName+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }




    public boolean updateAppOne(DadosApp tudo) {
        boolean retorna = true;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String packname = tudo.getPackageName().toString();
            ContentValues insertValues = new ContentValues();
            insertValues.put("NomeApp", tudo.getNomeApp());
            insertValues.put("SourceDir", tudo.getSourceDir());
            insertValues.put("targetSdkVersion", tudo.getTargetOsVersion());
            insertValues.put("IconUri", tudo.getIconUri());
            insertValues.put("Sistema", tudo.getSistema());
            insertValues.put("DataInstala", tudo.getDataInstala());
            insertValues.put("TamanhoFile", tudo.getTamanhoFile());
            insertValues.put("VersionName", tudo.getVersionName());
            insertValues.put("VersionCode", tudo.getVersionCode());
            insertValues.put("ProcessName", tudo.getProcessName());
            insertValues.put("PublicSourceDir", tudo.getPublicSourceDir());
            insertValues.put("NativeLibDir", tudo.getNativeLibDir());
            String data =  Utils.FormataData(1, "yyyy/MM/dd HH:mm:ss", null);
            tudo.setDataUpdate(data);
            insertValues.put("DTInclusao", tudo.getDTInclusao());
            insertValues.put("DataUpdate", tudo.getDataUpdate());
            db.update("Cad_App", insertValues, "PackageName = '"+packname+"'", null);
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
            retorna = false;
        }
        return retorna;
    }










    public ArrayList<DadosAds> getAds()
    {

        ArrayList<DadosAds> cb = new ArrayList<DadosAds>();

        try {
            String query  = "SELECT * FROM Tab_Ads";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    DadosAds app  = new DadosAds();
                    app.setID(cursor.getString(0));
                    app.setAdWord(cursor.getString(1));

                    cb.add(app);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return cb;
    }




    public int getAppVersaoCount (String PackageName, String versao) {

        int retorno = 0;


        try {
            String query  = "SELECT Count(*) FROM Cad_App where PackageName = '"+PackageName+"' and VersionName = '"+versao+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }




    public int VeSeTemAds (String PackageName) {

        int retorno = 0;
        try {
            String query  = "SELECT Count(*) FROM Cad_Libs where PackageName = '"+PackageName+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {
                    retorno =  cursor.getInt(0);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return retorno;
    }



    public TabPermissao getTabPermissao(String permissao)
    {

        TabPermissao cb = new TabPermissao();

        try {
            String query  = "SELECT * FROM Tab_Permissao where Permissao = '"+permissao+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {

                    cb.setPermissao(cursor.getString(0));
                    cb.setPortugues(cursor.getString(1));
                    cb.setIngles(cursor.getString(2));
                    cb.setIconeName(cursor.getString(3));
                    cb.setGrupo(cursor.getString(4));
                    cb.setIndice(cursor.getInt(5));




                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return cb;
    }



    public TabPermissao getTabPermissaoGrupo(String grupo)
    {

        TabPermissao cb = new TabPermissao();

        try {
            String query  = "SELECT * FROM Tab_Permissao where Grupo = '"+grupo+"'";
            SQLiteDatabase  db    = SQLiteDatabase.openDatabase( DB_PATH + DB_NAME , null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor  = db.rawQuery(query, null);
            if (cursor.moveToFirst())
            {
                do
                {

                    cb.setPermissao(cursor.getString(0));
                    cb.setPortugues(cursor.getString(1));
                    cb.setIngles(cursor.getString(2));
                    cb.setIconeName(cursor.getString(3));
                    cb.setGrupo(cursor.getString(4));
                    cb.setIndice(cursor.getInt(5));




                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


        }
        catch(Exception e)
        {
            Log.e("FALHA NOSSA DB............", "failed to insert", e);
        }
        return cb;
    }





}

