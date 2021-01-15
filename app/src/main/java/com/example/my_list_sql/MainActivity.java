package com.example.my_list_sql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvCV;
    ArrayList<CongViec> arrayCV;
    CongViec_Adaptor adaptor;
    MainActivity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCV = (ListView) findViewById(R.id.listviewCV);
        arrayCV = new ArrayList<>();
        adaptor = new CongViec_Adaptor(this, R.layout.line_cv, arrayCV);

        lvCV.setAdapter(adaptor);
        database = new Database(this, "GhiChu.sql", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS app_list(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");

        //database.QueryData("INSERT INTO app_list VALUES(NULL, 'LAM BAI TAP ANDROID')");

        GetDataCV();

    }

    private void GetDataCV(){
        Cursor dataCV = database.GetData("SELECT * FROM app_list");

        arrayCV.clear();
        while (dataCV.moveToNext()){
            String ten = dataCV.getString(1);
            int id = dataCV.getInt(0);
            arrayCV.add(new CongViec(id, ten));
            //Toast.makeText(MainActivity.this, ten, Toast.LENGTH_LONG).show();
        }
        adaptor.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_job, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.icon_them){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_job);
        final TextView editTen = dialog.findViewById(R.id.dailog_edit);
        Button btnThem = (Button) dialog.findViewById(R.id.dailog_them);
        Button btnHuy = (Button) dialog.findViewById(R.id.dailog_huy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                GetDataCV();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCV = editTen.getText().toString();
                if(tenCV.equals("")){
                    Toast.makeText(MainActivity.this, "Vui long nhap ten cong viec", Toast.LENGTH_LONG).show();

                }
                else{
                    database.QueryData("INSERT INTO app_list VALUES(NULL, '"+tenCV +"')");
                    Toast.makeText(MainActivity.this, "Da them thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCV();
                }
            }
        });
        dialog.show();
    }
    public void DialogXoaCV(final String tenCV, final int id){
        final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Ban co muon xoa Cong viec " + tenCV + "khong?");
        dialogXoa.setPositiveButton("Co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM app_list WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Da xoa " + tenCV, Toast.LENGTH_SHORT).show();
                GetDataCV();
            }
        });
        dialogXoa.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
    public void DialogSuaCV(String ten, final int id){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua);

        Button huy = (Button) dialog.findViewById(R.id.dialog_sua_button_huy);
        Button xacnhan = (Button) dialog.findViewById(R.id.dialog_sua_button);
        final EditText edtCv = (EditText) dialog.findViewById(R.id.dialog_sua_edittext);
        edtCv.setText(ten);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ND_CV = edtCv.getText().toString();
                database.QueryData("UPDATE app_list SET TenCV ='"+ND_CV+"' WHERE Id = '"+id +"'");
                dialog.dismiss();
                GetDataCV();
            }
        });
        dialog.show();
    }
}
