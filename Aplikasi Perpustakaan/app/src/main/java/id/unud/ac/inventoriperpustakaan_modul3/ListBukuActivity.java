package id.unud.ac.inventoriperpustakaan_modul3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListBukuActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvBuku;
    private FloatingActionButton fabAdd;
    private DataHelper dbHelper;
    private AdapterBuku adapterBuku;
    private ArrayList<Buku> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_buku);

        dbHelper = new DataHelper(ListBukuActivity.this);
        adapterBuku = new AdapterBuku(data, ListBukuActivity.this);

        rvBuku = findViewById(R.id.rv_list_buku);
        RecyclerView.LayoutManager layRV = new LinearLayoutManager(ListBukuActivity.this);
        rvBuku.setLayoutManager(layRV);
        rvBuku.setItemAnimator(new DefaultItemAnimator());
        rvBuku.setAdapter(adapterBuku);

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onClick(View v) {
        if(v == fabAdd){
            Intent i_input = new Intent(ListBukuActivity.this, InsertActivity.class);
            i_input.putExtra("MODE", "TAMBAH");
            i_input.putExtra("JUDUL", "");
            i_input.putExtra("NAMA", "");
            startActivity(i_input);
        }
    }

    private void loadData(){
        //reset data
        data.clear();

        int baris = Integer.parseInt(dbHelper.cari("select count(*) from tb_buku;"));
        int kolom = 7;

        String temp[][] = dbHelper.cari_array("select * from tb_buku", baris, kolom);
        for (int i = 0; i < temp.length; i++){
            Buku b = new Buku();
            b.setId(temp[i][0]);
            b.setJudul(temp[i][1]);
            b.setNama(temp[i][2]);
            b.setKategori(temp[i][3]);
            b.setGenre(temp[i][4]);
            b.setRating(temp[i][5]);
            b.setVerif(temp[i][6]);
            data.add(b);
            adapterBuku.notifyDataSetChanged();
        }
    }
}