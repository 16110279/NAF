package id.ac.amikom.jaka.nff.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import id.ac.amikom.jaka.nff.R;

import com.squareup.picasso.Picasso;
import id.ac.amikom.jaka.nff.api.RestApi;
import id.ac.amikom.jaka.nff.api.RetroServer;
import id.ac.amikom.jaka.nff.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditData extends AppCompatActivity {
    EditText nama, harga, fototeks;
    ImageView foto;

    Button btnsave, btnAdmin, btnTampildata, btnupdate, btndelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);


        nama = (EditText) findViewById(R.id.edt_nama);
        harga = (EditText) findViewById(R.id.edt_harga);
        foto = (ImageView) findViewById(R.id.ivFoto);

        btnTampildata = (Button) findViewById(R.id.btntampildata);
        btnAdmin = (Button) findViewById(R.id.btnAdmin);
        btnupdate =(Button) findViewById(R.id.btnUpdate);
        btnsave = (Button) findViewById(R.id.btn_insertdata);
        btndelete=(Button) findViewById(R.id.btnhapus);




        Intent data = getIntent();
        final String iddata = data.getStringExtra("id");
        if(iddata != null) {
            nama.setText(data.getStringExtra("nama"));
            harga.setText(data.getStringExtra("harga"));

            Picasso.with(this).load(RetroServer.img_url+(data.getStringExtra("foto"))).error(R.mipmap.ic_launcher)
                    .into(foto);

        }






        //btn update
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestApi api = RetroServer.getClient().create(RestApi.class);
                Call<ResponseModel> update = api.updateData(iddata, nama.getText().toString(),harga.getText().toString());
                update.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        Log.d("Retro", getResources().getString(R.string.response));
                        Toast.makeText(EditData.this,response.body().getPesan(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditData.this, AdminActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("Retro", getResources().getString(R.string.failure));

                    }
                });
            }
        });

        //btn delete
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RestApi api = RetroServer.getClient().create(RestApi.class);
                Call<ResponseModel> del  = api.deleteData(iddata);
                del.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        Log.d("Retro", getResources().getString(R.string.response));
                        Toast.makeText(EditData.this, response.body().getPesan(),Toast.LENGTH_SHORT).show();
                        Intent gotampil = new Intent(EditData.this,AdminActivity.class);
                        startActivity(gotampil);

                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("Retro", getResources().getString(R.string.failure));
                    }
                });
            }
        });



    }
}

