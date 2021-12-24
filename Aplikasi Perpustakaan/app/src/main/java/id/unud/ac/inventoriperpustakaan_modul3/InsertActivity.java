package id.unud.ac.inventoriperpustakaan_modul3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener {

    int rate = 0;
    EditText edJudul, edPenulis;
    RadioButton rbFiksi, rbNonFiksi;
    CheckBox cbScience, cbFantasy, cbDrama, cbAction;
    public TextView Rate;
    private Button btnSimpan;

    protected Cursor cursor;
    DataHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        dbHelper = new DataHelper(this);

        edJudul = (EditText) findViewById(R.id.ed_judul);
        edPenulis = (EditText) findViewById(R.id.ed_penulis);

        rbFiksi = (RadioButton) findViewById(R.id.rb_fiksi);
        rbNonFiksi = (RadioButton) findViewById(R.id.rb_nonfiksi);

        cbScience = (CheckBox) findViewById(R.id.cb_science);
        cbFantasy = (CheckBox) findViewById(R.id.cb_fantasy);
        cbDrama = (CheckBox) findViewById(R.id.cb_drama);
        cbAction = (CheckBox) findViewById(R.id.cb_action);

        final TextView textRate = findViewById(R.id.txt_ptrate);
        SeekBar seekBar = findViewById(R.id.seekBar);

        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(this);
        loadData();

        //seekbar
        textRate.setText(seekBar.getProgress() + "/" + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textRate.setText(progress + "/" + seekBar.getMax());
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                rate = rate + (progressValue - progress);
                progress = progressValue;
                textRate.setText(rate + "/10");
                Rate = textRate;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    private void loadData(){
        edJudul.setText(getIntent().getStringExtra("JUDUL"));
        edPenulis.setText(getIntent().getStringExtra("NAMA"));
    }

    @Override
    public void onClick(View v) {
        if(v==btnSimpan){
//            String mode = getIntent().getStringExtra("MODE");

                String judul = edJudul.getText().toString();
                String nama = edPenulis.getText().toString();
                String kategori = "";
                String genre = "";
                String rate = Rate.getText().toString();

                //radio button
                if (rbFiksi.isChecked()) {
                    kategori += "Fiksi";
                } else if (rbNonFiksi.isChecked()) {
                    kategori += "Non Fiksi";
                } else {
        //            Toast.makeText(this, "Kategori tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

                //checkbox
                if (cbScience.isChecked()) {
                    genre += "Science ";
                }if (cbFantasy.isChecked()) {
                    genre += "Fantasy ";
                }if (cbDrama.isChecked()) {
                    genre += "Drama ";
                }if (cbAction.isChecked()) {
                    genre += "Action ";
                }
//                else{
//        //            Toast.makeText(this, "Genre tidak boleh kosong", Toast.LENGTH_SHORT).show();
//                }

                //Validasi
                if(judul.equals("") || nama.equals("")){
                    Toast.makeText(this, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {
//                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String finalGenre = genre;
                    dbHelper.dml("insert into tb_buku( judul, nama, kategori, genre, rating) values ('" +
                            String.valueOf(judul) + "','" +
                            String.valueOf(nama) + "','" +
                            String.valueOf(kategori) + "','" +
                            String.valueOf(genre) + "','" +
                            String.valueOf(rate) + "')");
                    Toast.makeText(this, "Data berhasil dimasukkan", Toast.LENGTH_SHORT).show();

                }

            finish();
        }
    }

}