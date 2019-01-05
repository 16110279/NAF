package id.ac.amikom.jaka.nff.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import id.ac.amikom.jaka.nff.R;

public class MainActivity extends AppCompatActivity {
    EditText nama, harga;
    Button btnsave, btnAdmin, btnTampildata, btnupdate,btndelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnTampildata = (Button) findViewById(R.id.btntampildata);
        btnAdmin = (Button) findViewById(R.id.btnAdmin);




        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });



        //btn tampil data
        btnTampildata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });








    }


}
