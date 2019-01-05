package id.ac.amikom.jaka.nff.Activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import id.ac.amikom.jaka.nff.R;
import id.ac.amikom.jaka.nff.api.RestApi;
import id.ac.amikom.jaka.nff.api.RetroServer;
import id.ac.amikom.jaka.nff.model.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahData extends AppCompatActivity {
    public static final int NOTIFICATION_ID = 1;
    Bitmap bitmap;
    ImageView imageView;
    EditText nama, harga;
    Button btnsave, foto, selectImg;
    private  static final int IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnsave = (Button) findViewById(R.id.btn_insertdata);
        nama = (EditText) findViewById(R.id.edt_nama);
        harga = (EditText) findViewById(R.id.edt_harga);
        selectImg = (Button) findViewById(R.id.selectImg);
        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

            }
        });



        //button insert
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String snama = nama.getText().toString();
                String sharga = harga.getText().toString();
                String sfoto = convertToString();

                if (snama.isEmpty() ) {
                    nama.setError("nama perlu di isi");
                }else if (sharga.isEmpty()){
                    harga.setError("harga perlu di isi");}
                else {

                    RestApi api = RetroServer.getClient().create(RestApi.class);

                    Call<ResponseModel> sendbio = api.sendBiodata(snama,sharga,sfoto);
                    sendbio.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
/*
                        pd.setVisibility(View.GONE);
*/

                            Log.d("RETRO", "response : " + response.body().toString());
                            String kode = response.body().getKode();


                            if(kode.equals("1"))
                            {
                                Toast.makeText(TambahData.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TambahData.this, AdminActivity.class));
                                nama.getText().clear();
                                harga.getText().clear();

                                NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(TambahData.this)
                                        .setSmallIcon(R.drawable.baseline_cloud_upload_black_18dp) //ikon notification
                                        .setContentTitle("Sukses !") //judul konten
                                        .setAutoCancel(true)//untuk menswipe atau menghapus notification
                                        .setContentText("Anda menambahkan produk baru "+nama.getText().toString()); //isi text

/*
Kemudian kita harus menambahkan Notification dengan menggunakan NotificationManager
 */

                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                                notificationManager.notify(NOTIFICATION_ID, builder.build()
                                );

                            }else
                            {
                                Toast.makeText(TambahData.this, "Data Error tidak berhasil disimpan", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
/*
                        pd.setVisibility(View.GONE);
*/
                            Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
                        }
                    });
                }}
        });

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE);
    }

    private String convertToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("warning");
        alert.setMessage("do you want to exit");

        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TambahData.this.finish();

            }
        });
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}



