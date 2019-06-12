package app.aya.clientsoffline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by egypt2 on 10-Dec-18.
 */

public class ClientsList extends Activity {

    EditText search_text ;
    ListView list_view;
    Button search_button,add_button;
    String ls_search_text ,ls_username  ,ls_phone ;

    HashMap<String, Integer> hashMap_position ;
    List<DataClients> list_dataclients  ;
    ArrayList<String> arrayList_data   ;

    TextView start, tab_start , tab_Privacy ,Privacy  ,add_client , tab_add_client , tab_contact_us , tab_recordclients,tab_search;
    private int height_start , height_Privacy , height_add_client , height_contact_us , height_recordclients , height_search;
    ArrayList<HashMap<String,String>>           arrayList_employee;
    HashMap<String,String>      hash_employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientslist);

        search_text=(EditText)findViewById(R.id.search_text);
        search_button=(Button)findViewById(R.id.search_button);
        list_view = (ListView)findViewById(R.id.clientslist);
        add_button = (Button)findViewById(R.id.add_button);

        hash_employees = new HashMap<String, String>();
        arrayList_employee= new ArrayList<HashMap<String, String>>();
        list_dataclients = new ArrayList<>();
        arrayList_data = new ArrayList<String>();
        hashMap_position = new HashMap<String, Integer>();
        //------------
        list_view.setTextFilterEnabled(true);

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // When user changed the Text
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    }

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<String> search = new ArrayList<String>();
                int lenghtSearch = search_text.getText().length();
                search.clear();
                for (int i = 0 ; i<arrayList_data.size() ; i++){
                    if (lenghtSearch <= arrayList_data.get(i).length()){
                        if (search_text.getText().toString().equalsIgnoreCase((String) arrayList_data.get(i).subSequence(0,lenghtSearch) ) ){
                            search.add(arrayList_data.get(i));
                        }
                    }
                }
                list_view.setAdapter(new CustomArrayAdapter(ClientsList.this,search ,ls_username , list_dataclients , hashMap_position));

            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ls_search_text = search_text.getText().toString();

                if (ls_search_text.isEmpty()){
                    Toast.makeText(ClientsList.this, " يجب كتابه اسم العميل قبل الضغط على بحث", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(ClientsList.this, "جارى البحث ......", Toast.LENGTH_SHORT).show();

                }
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddClients.class);
                    intent.putExtra("username", ls_username);
                    intent.putExtra("phone", ls_phone);
                    startActivity(intent);

            }
        });
        //------------
        retrieveemployee();

        list_view.setAdapter(new ListViewAdapterClients(ClientsList.this,arrayList_employee ,ls_username ));
      //---------floating ---------------
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                createDeclearLayout();
            }
        });
    }

    private void createDeclearLayout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClientsList.this);

        View listViewClient = getLayoutInflater().inflate(R.layout.layout_phone, null);
        start = (TextView) listViewClient.findViewById(R.id.start);
        tab_start = (TextView) listViewClient.findViewById(R.id.tab_start);

        Privacy = (TextView) listViewClient.findViewById(R.id.Privacy);
        tab_Privacy = (TextView) listViewClient.findViewById(R.id.tab_Privacy);

       add_client = (TextView) listViewClient.findViewById(R.id.add_client);
        tab_add_client = (TextView) listViewClient.findViewById(R.id.tab_add_client);

        tab_search = (TextView)listViewClient.findViewById(R.id.tab_search);

        tab_recordclients=(TextView)listViewClient.findViewById(R.id.tab_record_client);

        tab_contact_us=(TextView)listViewClient.findViewById(R.id.tab_contact_us);
        builder.setNegativeButton("إغلاق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }

        });
        builder.setView(listViewClient);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //----------- set height
        setHeight();

        //------------

    }

    public void retrieveemployee (){
        int len , id = 0;
        String  key = ""  , employee="" ;

        SharedPreferences sh = getSharedPreferences("newemployee", MODE_PRIVATE);
        len =  sh.getAll().size();
        Log.d(TAG, "retrieveemployee: length" + len);
        if (len!=0){
        for( ;id < len;id++ ){
            key = "employee_"+id;
            employee=   sh.getString(key,"");

            hash_employees.put("name"+id,employee);
            arrayList_employee.add(hash_employees);
        }}
        else{
            hash_employees.put("name0","اختبار");
        }
        Log.d(TAG, "retrieveemployee: "+arrayList_employee);
    }

    public void setHeight(){
        getHeight("S", tab_start.getHeight());
        getHeight("P", tab_Privacy.getHeight());
        getHeight("C", tab_add_client.getHeight());
        getHeight("R", tab_recordclients.getHeight());
        getHeight("search", tab_search.getHeight());
        getHeight("Contact", tab_contact_us.getHeight());

    }

    public void setHeightZero(){
        tab_start.setHeight(0);
        tab_Privacy.setHeight(0);
        tab_add_client.setHeight(0);
        tab_search.setHeight(0);
        tab_recordclients.setHeight(0);
        tab_contact_us.setHeight(0);
    }

    private void getHeight(String s, int height) {
        if (height != 0) {
            switch (s) {
                case "S":
                    height_start = height;
                    break;
                case "P":
                    height_Privacy = height;
                    break;
                case "C":
                    height_add_client = height;
                    break;
                case "search":
                    height_search=height;
                    break;
                case "R":
                    height_recordclients=height;
                    break;
                case "Contact":
                    height_contact_us = height ;
                    break;
            }

        }
    }

    public void tabStart(View view) {
        setHeight();
        setHeightZero();
        tab_start.setHeight(height_start);
        // width , eight
    }

    public void tabPrivacy(View view) {
        setHeight();
        setHeightZero();
        tab_Privacy.setHeight(height_Privacy);
        // width , height
    }

    public void tabAdd_client(View view) {
        setHeight();
        setHeightZero();
        tab_add_client.setHeight(height_add_client);
        // width , height

    }

    public void tabContact_us(View view) {

        setHeight();
        setHeightZero();
        tab_contact_us.setHeight(height_contact_us);
        // width , height
    }

    public void tab_recordclient(View view) {

        setHeight();
        setHeightZero();
        tab_recordclients.setHeight(height_recordclients);
        // width , height
    }

    public void tabSearch(View view) {

        setHeight();
        setHeightZero();
        tab_search.setHeight(height_search);
        // width , height
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Black));

        // builder.
        builder.setMessage("هل انت متاكد من انك تريد الخروج من التطبيق ؟").setCancelable(false).setPositiveButton("الخروج من التطبيق", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }).setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}
