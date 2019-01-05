package id.ac.amikom.jaka.nff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import id.ac.amikom.jaka.nff.adapter.RecylerAdapter;
import id.ac.amikom.jaka.nff.api.RestApi;
import id.ac.amikom.jaka.nff.api.RetroServer;
import id.ac.amikom.jaka.nff.model.DataModel;
import id.ac.amikom.jaka.nff.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataModel> mItems = new ArrayList<>();
    ProgressBar pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Menginisiasi Toolbar dan mensetting sebagai actionbar

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setSupportActionBar(toolbar);
        // Menginisiasi  NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Mengatur Navigasi View Item yang akan dipanggil untuk menangani item klik menu navigasi


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Memeriksa apakah item tersebut dalam keadaan dicek  atau tidak,
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Menutup  drawer item klik
                drawerLayout.closeDrawers();
                //Memeriksa untuk melihat item yang akan dilklik dan melalukan aksi
                switch (menuItem.getItemId()){
                    // pilihan menu item navigasi akan menampilkan pesan toast klik kalian bisa menggantinya
                    //dengan intent activity
                    case R.id.navigation1:
                        Toast.makeText(getApplicationContext(),"Product Telah Dipilih",Toast.LENGTH_SHORT).show();
                        Intent i =new Intent(getApplicationContext(),AdminActivity.class);  startActivity(i);
                        return true;
                    case R.id.navigation2:
                        Toast.makeText(getApplicationContext(),"Setting Telah Dipilih",Toast.LENGTH_SHORT).show();
                        Intent ii =new Intent(getApplicationContext(),AdminSetting.class);  startActivity(ii);
                        return true;

                    default:
                        moveTaskToBack(true);
                        Toast.makeText(getApplicationContext(),"Anda Telah Keluar",Toast.LENGTH_SHORT).show();
                        return false;
                }

            }
        });


        // Menginisasi Drawer Layout dan ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Kode di sini akan merespons setelah drawer menutup disini kita biarkan kosong
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                //  Kode di sini akan merespons setelah drawer terbuka disini kita biarkan kosong
                super.onDrawerOpened(drawerView);
            }
        };
        //Mensetting actionbarToggle untuk drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //memanggil synstate
        actionBarDrawerToggle.syncState();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, TambahData.class));

            }
        });

        pd = (ProgressBar) findViewById(R.id.pd);
        pd.setIndeterminate(true);
        pd.setVisibility(View.VISIBLE);

        mRecycler = (RecyclerView) findViewById(R.id.recyclerTemp);
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);
        // pd.setVisibility(View.VISIBLE);

        RestApi api = RetroServer.getClient().create(RestApi.class);
        Call<ResponseModel> getdata = api.getData();
        getdata.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.setVisibility(View.GONE);
                Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                mItems = response.body().getResult();
//                String iTems []= new String[mItems.size()];
//                for (int i=0; i<mItems.size();i++ ){
//                    iTems[i]= mItems.get(i).getNama();
//
//                }
                mAdapter = new RecylerAdapter(AdminActivity.this,mItems);
                mRecycler.setAdapter(mAdapter);
                //mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.setVisibility(View.GONE);
                //pd.setVisibility(View.GONE);
                Log.d("RETRO", "FAILED : respon gagal");

            }
        });

    }

    @Override
    public void onBackPressed() {


        startActivity(new Intent(AdminActivity.this, MainActivity.class));

    }
}
