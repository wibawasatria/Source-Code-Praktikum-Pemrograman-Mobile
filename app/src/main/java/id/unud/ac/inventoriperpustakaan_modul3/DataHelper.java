package id.unud.ac.inventoriperpustakaan_modul3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_perpus";
    private static final int DB_VERSION = 1;

    // create tabel
    private final String create_buku = "CREATE TABLE `tb_buku` (id_buku integer primary key autoincrement,  judul text, nama text, kategori text, genre text, rating text, verif int);";
//    private String create_buku = "CREATE TABLE `tb_buku` (\n" +
//            "\t`id_buku`INTEGER autoincrement,\n" +
//            "\t`nama`\tTEXT,\n" +
//            "\tPRIMARY KEY(`id_buku`)\n" +
//            ");";

    public DataHelper(Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db){
//        String sql = "create table tb_buku(id_buku integer primary key autoincrement,  judul text null, nama text null, kategori text null, genre text null, rating text null, verif int null);";
//        Log.d("Data", "Oncreate: "+ sql);
//        db.execSQL(sql);
//    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS tb_buku");
        db.execSQL(create_buku);
    }

//    @Override
//    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){
//
//    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_buku");
    }

    public void dml(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
//        db.close();
    }

    public String cari(String query){
        String hasil = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            hasil = cursor.getString(0);
        }
//        db.close();
        return hasil;
    }

    public Bitmap cari_blob(){
        Bitmap bitmap = null;
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        try
        {
            String selectQuery = "select foto from profile;";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    // Convert blob activity_kanvas to byte array
                    byte[] blob = cursor.getBlob(cursor.getColumnIndex("foto"));
                    // Convert the byte array to Bitmap
                    bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                }

            }
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
        return bitmap;
    }

    public String[] cari_array1D(String query, int baris){
        String[] data = new String[baris];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int counter = 0;
        while (cursor.moveToNext()){
            data[counter] =  cursor.getString(0);
            counter++;
        }
        db.close();
        return data;
    }

    public String[][] cari_array(String query, int baris, int kolom){
        String[][] data = new String[baris][kolom];
        SQLiteDatabase db = this.getReadableDatabase();

        int counter_baris = 0;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            for (int i=0; i<kolom; i++){
                data[counter_baris][i] = cursor.getString(i);
            }
            counter_baris++;
        }
//        db.close();
        return data;
    }

}
