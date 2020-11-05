package com.koaca.wmssystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
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

    public SQLiteDatabase database;


    List<String> ListItems = new ArrayList<>();

    String[] items_cargo = {"코만푸드", "M&F", "SPC", "공차", "케이비켐", "BNI","기타","스위치코리아","서강비철", "제임스포워딩","스위치코리아"};
    Integer[] items_outsourcing={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
//    Integer[] items_outsourcing={"가","나","다","라"};
    String[] items_equip = {"FF02 (인천04마1068)", "FF04 인천04사4252", "FF20 (BR18S-00095)", "FF21 (BR18S-2-00016)", "FF25 (FBA03-1910-04218)",
            "FK11 (FBRW25-R75C-600M)"};
    String[] items_etc={"구매발주내역 입고","폐기물 수거","시설물 파손,수리","식약처,견품반출","세관검사","SPC 작업용 팔렛트 입고",
            "공차 작업용 팔렛트 입고","기타화주팔렛트 입고"};

    ArrayList<String> list_cargo;
    ArrayList<Integer> list_ousourcing;
    ArrayList<String> list_equip;
    ArrayList<String> list_etc;

    String[] list_item;

    String selectedItemsText;
    String deletedItmesText;

    int delete_int;

    int selectedId = 0;
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

        database=openOrCreateDatabase("WmsDataBase",MODE_PRIVATE,null);
        integratedData();

       //        if(savedInstanceState !=null){
//            String savedText=savedInstanceState.getString("editString");
//            Toast.makeText(this, savedText, Toast.LENGTH_SHORT).show();
//            textView.setText(savedText);
//        }
    }


    public void deletedData(String items, int position){
        String deldedData="delete from "+items+" where _id= "+position;
        database.execSQL(deldedData);

    }


    private void integratedData() {
        cargo_Data();
        outsourcing_Data();
        equp_Data();
        etc_Data();
    }

    private void etc_Data() {
        int etcCount=items_etc.length;
        list_etc=new ArrayList<>();
        String tableName_etc="create table if not exists "+"etc"+"(_id integer PRIMARY KEY autoincrement,etc text)";
        database.execSQL(tableName_etc);
        for(int i=0;i<etcCount;i++){
            String etcItem=items_etc[i];
            String etcInsert="insert or replace into "+"etc"+"(_id,etc) values ("+i+",'"+etcItem+"')";
            database.execSQL(etcInsert);}

        String queryEtc="select _id,etc from "+"etc";
        Cursor cursor3=database.rawQuery(queryEtc,null);
        int queryCount3=cursor3.getCount();
        for(int i=0;i<queryCount3;i++){
            cursor3.moveToNext();
            String items=cursor3.getString(1);
            list_etc.add(items);}
        cursor3.close();
        items_etc=list_etc.toArray(new String[0]);
    }

    private void equp_Data() {
        int equipCount=items_equip.length;
        list_equip=new ArrayList<>();
        String tableName_equip="create table if not exists "+"equip"+"(_id integer PRIMARY KEY autoincrement,equip text)";
        database.execSQL(tableName_equip);
        for(int i=0;i<equipCount;i++){
            String equipItem=items_equip[i];
            String equipInsert="insert or replace into "+"equip"+"(_id,equip) values ("+i+",'"+equipItem+"')";
            database.execSQL(equipInsert);}

        String queryEquip="select _id,equip from "+"equip";
        Cursor cursor2=database.rawQuery(queryEquip,null);
        int queryCount2=cursor2.getCount();
        for(int i=0;i<queryCount2;i++){
            cursor2.moveToNext();
            String items=cursor2.getString(1);
            list_equip.add(items);}
        cursor2.close();
        items_equip=list_equip.toArray(new String[0]);
    }

    private void outsourcing_Data() {
        int outsourcingCount=items_outsourcing.length;
        list_ousourcing=new ArrayList<>();
        String tableName_outsourcing="create table if not exists "+"outsourcing"+"(_id integer PRIMARY KEY autoincrement,outsourcing integer)";
        database.execSQL(tableName_outsourcing);
        for(int i=0;i<outsourcingCount;i++){
            int outsourcingItem=items_outsourcing[i];
            String outsourcingInsert="insert or replace into "+"outsourcing"+"(_id,outsourcing) values ("+i+","+outsourcingItem+")";
            database.execSQL(outsourcingInsert);}

        String queryOutsourcing="select _id,outsourcing from "+"outsourcing";
        Cursor cursor1=database.rawQuery(queryOutsourcing,null);

        int queryCount1=cursor1.getCount();
        for(int i=0;i<queryCount1;i++){
            cursor1.moveToNext();
            int items=cursor1.getInt(1);
            list_ousourcing.add(items);}
        cursor1.close();
        items_outsourcing=list_ousourcing.toArray(new Integer[0]);
    }

    private void cargo_Data() {
        int cargoCount=items_cargo.length;
        list_cargo=new ArrayList<>();

        String tableName_cargo="create table if not exists "+"cargo"+"(_id integer PRIMARY KEY autoincrement,cargo text)";
        database.execSQL(tableName_cargo);
        for(int i=0;i<cargoCount;i++){
            String cargoItem=items_cargo[i];
            String cargoInsert="insert or replace into "+"cargo"+"(_id,cargo) values ("+i+",'"+cargoItem+"')";
            database.execSQL(cargoInsert);}

        String queryCargo="select _id,cargo from "+"cargo";
        Cursor cursor=database.rawQuery(queryCargo,null);
        int queryCount=cursor.getCount();
        for(int i=0;i<queryCount;i++){
            cursor.moveToNext();
            String items=cursor.getString(1);
            list_cargo.add(items);}
                cursor.close();


        items_cargo=list_cargo.toArray(new String[0]);
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
        switch(item.getItemId())
        { case R.id.action_account:
            final List<String> ListItems = new ArrayList<>();
                ListItems.add("cargo");
                ListItems.add("outsourcing");
                ListItems.add("equip");
                ListItems.add("etc");
                final CharSequence[] items_add =  ListItems.toArray(new String[ ListItems.size()]);
                final EditText editText11=new EditText(this);
                final List SelectedItems=new ArrayList();
                int defaultItem=0;
                SelectedItems.add(defaultItem);

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("항목선택 창");
                builder.setView(editText11);
                builder.setSingleChoiceItems(items_add, defaultItem,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectedItems.clear();
                        SelectedItems.add(which);
                        selectedItemsText=items_add [which].toString();
                        Toast.makeText(MainActivity.this, selectedItemsText, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("신규 등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dataName=editText11.getText().toString();
                        String dataQuery="insert or replace into "+selectedItemsText+"("+selectedItemsText+") values ('"+dataName+"')";
                        database.execSQL(dataQuery);

                        Toast.makeText(getApplicationContext(),dataName,Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                builder.show();
                break;

            case R.id.action_account1:
                final List<String> ListItems_delete = new ArrayList<>();
                ListItems_delete.add("cargo");
                ListItems_delete.add("outsourcing");
                ListItems_delete.add("equip");
                ListItems_delete.add("etc");
                final CharSequence[] items_delete = ListItems_delete.toArray(new String[ ListItems_delete.size()]);
//                final EditText editText11=new EditText(this);
                final List SelectedItems_delete=new ArrayList();
                int defaultItem_delete=0;
                SelectedItems_delete.add(defaultItem_delete);

                AlertDialog.Builder builder_delete=new AlertDialog.Builder(this);
                builder_delete.setTitle("항목선택 창");
//                builder_delete.setView(editText11);
                builder_delete.setSingleChoiceItems(items_delete, defaultItem_delete,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete_int=which;

                            }
                        });
                builder_delete.setPositiveButton("항목 수정 ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(delete_int){
                            case 0:
                                list_item=items_cargo;
                                selectedItemsText="cargo";
                                list_editedItem(list_item,selectedItemsText);
                                break;
                            case 1:
                                Toast.makeText(MainActivity.this, "관리자에게 문의 바랍니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                list_item=items_equip;
                                selectedItemsText="equip";
                                list_editedItem(list_item,selectedItemsText);
                                break;
                            case 3:
                                selectedItemsText="etc";
                                list_editedItem(list_item,selectedItemsText);
                                break;

                        }
                    }
                });
                builder_delete.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                builder_delete.show();
                break;}
                return true;
    }

    private void list_editedItem(String[] list_item,String selectedItemsText) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        final EditText editText_list_edit=new EditText(this);
        final List SelectedItems=new ArrayList();
        int defaultItem=0;

        SelectedItems.add(defaultItem);
        builder.setTitle("항목선택 창");
//        builder.setView(editText_list_edit);

         builder.setSingleChoiceItems(list_item, defaultItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        SelectedItems.clear();
//                        SelectedItems.add(which);
                        selectedId=which;
                        deletedItmesText=list_item[which].toString();
//                        Toast.makeText(MainActivity.this, selectedItemsText, Toast.LENGTH_SHORT).show();

                    }
                });
        builder.setPositiveButton("항목 삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String deletedQuery="delete from "+selectedItemsText+" where "+selectedItemsText+" = "+"'"+deletedItmesText+"'";
                database.execSQL(deletedQuery);


            }
        });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
//        builder.setNeutralButton("항목 추가", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String dataName=editText_list_edit.getText().toString();
//                String dataQuery="insert or replace into "+selectedItemsText+"("+selectedItemsText+") values ('"+dataName+"')";
//                database.execSQL(dataQuery);
//                integratedData();
//                Toast.makeText(getApplicationContext(),dataName,Toast.LENGTH_SHORT).show();
//
//            }
//        });

        builder.show();
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

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        String savedText1=textView.getText().toString();
//        outState.putString("editString",savedText1);
//    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        captureProcess=new CaptureProcess(this,surfaceHolder);
        captureProcess.preView();
    }

}