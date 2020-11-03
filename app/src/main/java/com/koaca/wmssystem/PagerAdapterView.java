package com.koaca.wmssystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class PagerAdapterView extends PagerAdapter  {

    ArrayList<View> views=new ArrayList<>();

    Spinner spinner;
    RadioGroup rg;
//    RadioButton radioButton, radioButton2,radioButton_equip, radioButton2_equip;
    String sp_des_text;


    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
//    CheckBox checkBox1;
//    CheckBox checkBox2;
//    EditText editText;


    Spinner spinner_equip;


    Spinner spinner_etc;

    EditText editText2_editable;

    private Context mContext=null;
    MainActivity mainActivity;

    public PagerAdapterView(){
    }

    public PagerAdapterView(MainActivity mainActivity,Context context) {
        mContext = context ;
        this.mainActivity=mainActivity;
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v1 = null;
        View v2 = null;
        View v3 = null;
        View v4 = null;
        View v5 = null;

        if (mContext != null) {
            LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           v1 = inflater.inflate(R.layout.cargo,container,false);
           v2 = inflater.inflate(R.layout.outsourcing,container,false);
           v3 = inflater.inflate(R.layout.equipments,container,false);
           v4 = inflater.inflate(R.layout.etc, container,false);
           v5 = inflater.inflate(R.layout.editable,container,false);




        ArrayAdapter<String> cargoradapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, mainActivity.items);
        cargoradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RadioButton radioButton = v1.findViewById(R.id.radioButton);
        RadioButton radioButton2 = v1.findViewById(R.id.radioButton2);
        spinner = v1.findViewById(R.id.spinner);
        spinner.setAdapter(cargoradapter);
        sp_des_text = spinner.getSelectedItem().toString();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str_radio1 ="";
                if (radioButton.isChecked()) {
                    str_radio1 += radioButton.getText().toString();
                }
                if (radioButton2.isChecked()) {
                    str_radio1 += radioButton2.getText().toString();
                }

                spinner.setTag(mainActivity.items[position]);
               mainActivity.textView.setText(str_radio1+"_"+mainActivity.items[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setTag(""); }
        });
        spinner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
            //outsourcing
        spinner1=v2.findViewById(R.id.spinner1_outsourcing);
        spinner3=v2.findViewById(R.id.spinner3_outsourcing);
        spinner2=v2.findViewById(R.id.spinner2_outsourcing);
        CheckBox checkBox1=v2.findViewById(R.id.checkBox9_outsourcing);
        CheckBox checkBox2=v2.findViewById(R.id.checkBox10_outsourcing);
        EditText editText=v2.findViewById(R.id.editText2_outsourcing);

        ArrayAdapter<String> spAdapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,mainActivity.items_outsourcing);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spAdapter);
        spinner2.setAdapter(spAdapter);
        spinner3.setAdapter(spAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str_check9="";
                if(checkBox1.isChecked())
                {str_check9+=checkBox1.getText().toString();}
                spinner1.setTag(mainActivity.items_outsourcing[position]);
                mainActivity.textView.setText(str_check9+"_"+mainActivity.items_outsourcing[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner1.setTag("");}
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str_check10="";
                if(checkBox2.isChecked()){str_check10+=checkBox2.getText().toString();}
                spinner2.setTag(mainActivity.items_outsourcing[position]);
                mainActivity.textView.append(","+str_check10+"_"+mainActivity.items_outsourcing[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner2.setTag("");
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ed2="";
                ed2=editText.getText().toString();
                spinner3.setTag(mainActivity.items_outsourcing[position]);
                mainActivity.textView.append(","+ed2+"_"+mainActivity.items_outsourcing[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner3.setTag(""); }
        });
        //equip
        spinner_equip=v3.findViewById(R.id.spinner_equip);
            RadioButton radioButton_equip = v3.findViewById(R.id.radioButton_equip);
            RadioButton radioButton2_equip = v3.findViewById(R.id.radioButton2_equip);
        ArrayAdapter<String> equipAdapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,mainActivity.items_equip);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_equip.setAdapter(equipAdapter);
        spinner_equip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str_radio =" ";
                if (radioButton_equip.isChecked()) {
                    str_radio += radioButton_equip.getText().toString(); }
                if (radioButton2_equip.isChecked()) {
                    str_radio += radioButton2_equip.getText().toString(); }
                spinner_equip.setTag(mainActivity.items[position]);
                mainActivity.textView.setText(str_radio+"_"+mainActivity.items_equip[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner_equip.setTag("");
            }
        });

        //etc

//        database=openOrCreateDatabase("SpinnerDataBase",MODE_PRIVATE,null);
//        String tablenameSql="create table if not exists "+"Etc"+"(_id integer PRIMARY KEY autoincrement,item text)";
//        database.execSQL(tablenameSql);
//        int etcDataCount=items_etc.length;
//        for(int i=0;i<etcDataCount;i++){
//            String dataItem=items_etc[i];
//            String etcRecord="insert or replace into "+"Etc"+"(_id,item) values ("+i+",'"+dataItem+"')";
//            database.execSQL(etcRecord);
//        }
//        String queryItem="select _id,item from "+"Etc";
//        Cursor cursor=database.rawQuery(queryItem,null);
//        int queryCount=cursor.getCount();
//
//        list_etc=new ArrayList<>();
//        for(int i=0;i<queryCount;i++){
//            cursor.moveToNext();
//            String item=cursor.getString(1);
//            list_etc.add(item);
//               }
//        cursor.close();

//        ArrayList<String> list_etc=new ArrayList<>( Arrays.asList(items_etc));
//
//        items_etc=list_etc.toArray(new String[0]);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                mContext,android.R.layout.simple_spinner_item,mainActivity.items_etc );
//        ArrayAdapter<String> menuAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items_etc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_etc=v4.findViewById(R.id.spinner_etc);
        spinner_etc.setAdapter(adapter);
        spinner_etc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_etc.setTag(mainActivity.items_etc[position]);
                mainActivity.textView.setText(mainActivity.items_etc[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner_etc.setTag("");
            } });
        spinner_etc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });

        //editable

        editText2_editable=v5.findViewById(R.id.editText2_editable);
        editText2_editable.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String editable=editText2_editable.getText().toString();
                mainActivity.textView.setText(editable);
                return true;
            }
        });

            views.add(v1);
            views.add(v2);
            views.add(v3);
            views.add(v4);
            views.add(v5);
        }
        container.addView(v1);
        container.addView(v2);
        container.addView(v3);
        container.addView(v4);
        container.addView(v5);

        return views.get(position);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
