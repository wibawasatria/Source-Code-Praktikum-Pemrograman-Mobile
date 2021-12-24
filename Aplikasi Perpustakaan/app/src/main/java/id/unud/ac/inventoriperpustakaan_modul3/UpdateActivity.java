package id.unud.ac.inventoriperpustakaan_modul3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView hJudul, hNama, hGenre, hKategori, hRating;
    private RadioButton rbValid, rbNonvalid;
    private Button btnSimpan;
    DataHelper dbHelper;

    int id = 0, tempVerif=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DataHelper(this);

        hJudul = findViewById(R.id.txt_hasil_judul);
        hNama = findViewById(R.id.txt_hasil_nama);
        hKategori = findViewById(R.id.txt_hasil_kategori);
        hGenre = findViewById(R.id.txt_hasil_genre);
        hRating = findViewById(R.id.txt_hasil_rating);

        rbValid = findViewById(R.id.rb_valid);
        rbNonvalid = findViewById(R.id.rb_novalid);

        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(this);
        loadData();

        if(tempVerif == 1){
            rbValid.setChecked(true);
        }else if(tempVerif == 0){
            rbNonvalid.setChecked(true);
        }
        else{
            Toast.makeText(this, "Silahkan validasi data buku", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData(){
        id = Integer.parseInt(getIntent().getStringExtra("ID")) ;
        hJudul.setText(getIntent().getStringExtra("JUDUL"));
        hNama.setText(getIntent().getStringExtra("NAMA"));
        hKategori.setText(getIntent().getStringExtra("KATEGORI"));
        hGenre.setText(getIntent().getStringExtra("GENRE"));
        hRating.setText(getIntent().getStringExtra("RATING"));

        if(getIntent().getStringExtra("VERIF") != null){
            tempVerif = Integer.parseInt(getIntent().getStringExtra("VERIF"));
        }else if(getIntent().getStringExtra("VERIF") == null){
            tempVerif = 3;
        }

    }

    @Override
    public void onClick(View v) {
        if(v==btnSimpan){
            int verif = 0;
            if (rbValid.isChecked()) {
                verif = 1;
            } else if (rbNonvalid.isChecked()) {
                verif = 0;
            } else {
                //            Toast.makeText(this, "Kategori tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
            dbHelper.dml("update tb_buku set verif = '"+ verif +"' where id_buku = '"+id+"';");
            Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),ListBukuActivity.class);
            startActivity(i);
        }
    }
}