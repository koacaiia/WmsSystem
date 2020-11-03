package com.koaca.wmssystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    String [] permission_list={
            Manifest.permission.CAMERA};
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    CaptureProcess captureProcess;

    PagerAdapterView customAdapter;

    TextView textView;
    TextView textView2;

    SearchView searchView;

    SQLiteDatabase database;
    ArrayList<String> list_etc;

    List<String> ListItems = new ArrayList<>();
    String selectedItems;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=findViewById(R.id.viewpager);
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyy년MM월dd일E요일HH시mm분").format(new Date());
        textView2.setText(timeStamp);

        customAdapter=new PagerAdapterView(this,this);
        viewPager.setAdapter(customAdapter);

        surfaceView=findViewById(R.id.surfaceView);
        requestPermissions(permission_list,0);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceHolder=surfaceView.getHolder();



        int layoutId=viewPager.getSourceLayoutResId();
        Log.d("koaca1",Integer.toString(layoutId));



        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureProcess.capture();
            }
        });

        if(savedInstanceState !=null){
            String savedText=savedInstanceState.getString("editString");
            Toast.makeText(this, savedText, Toast.LENGTH_SHORT).show();
            textView.setText(savedText);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int result:grantResults){
            if(result== PackageManager.PERMISSION_DENIED){
                Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        captureProcess=new CaptureProcess(this,surfaceHolder);
        captureProcess.preView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

       /* MenuItem item1=menu.findItem(R.id.item1);
        searchView= (SearchView) item1.getActionView();
        searchView.setQueryHint("신규 입력사항 입력");
        ActionListener listener=new ActionListener();
        searchView.setOnQueryTextListener(listener);*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
        case R.id.item1:
            return true;
            case R.id.action_account:
                ListItems.add("입출고");
                ListItems.add("용역관리");
                ListItems.add("장비관리");
                ListItems.add("기타");
                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);
                final EditText editText11=new EditText(this);
                final List SelectedItems=new ArrayList();

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("항목선택 창");
                builder.setView(editText11);
                builder.setPositiveButton("신규 등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dataName=editText11.getText().toString();
                        Toast.makeText(getApplicationContext(),dataName,Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            SelectedItems.add(which);
                        }else if(SelectedItems.contains(which)){
                                SelectedItems.remove(Integer.valueOf(which));
                            }

                    }
                });
                builder.show();
                return true;
            default :
            return super.onOptionsItemSelected(item);

        }



    }

    class ActionListener implements SearchView.OnQueryTextListener{

        //돋보기 버튼 입력
        @Override
        public boolean onQueryTextSubmit(String query) {



          String etcRecord="insert into "+"Etc"+"(item) values ('"+query+"')";
            database.execSQL(etcRecord);

            String queryItem="select _id,item from "+"Etc";
            Cursor cursor=database.rawQuery(queryItem,null);

            int etcDataCount=cursor.getCount();
            list_etc=new ArrayList<>();
            for(int i=0;i<etcDataCount;i++){
                cursor.moveToNext();
                String item=cursor.getString(1);
                list_etc.add(item);
            }
            cursor.close();
            searchView.clearFocus();
            return true;
        }

        //검색어 입력

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String savedText1=textView.getText().toString();
        outState.putString("editString",savedText1);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        captureProcess=new CaptureProcess(this,surfaceHolder);
        captureProcess.preView();
    }
}