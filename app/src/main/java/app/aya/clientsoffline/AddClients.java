package app.aya.clientsoffline;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.HashMap;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by egypt2 on 11-Dec-18.
 */

public class AddClients extends Activity {

    EditText name ,phone , card ,cash ,buy ,buy_details ;
    TextView total ,date ;
    Button button_save ;
    String ls_name , ls_phone , ls_card , ls_cash ="0.0" , ls_buy="0.0" , ls_total="0.0" , ls_date,ls_Remainder ;
    Double ld_buy=0.0 , ld_cash=0.0 , ld_total=0.0 ;
    String ls_username , ls_user_tracks;
    DatePickerDialog datePickerDialog ;
    String ls_name_login ,ls_phone_test  ,ls_buy_details;
    HashMap<String,String> track = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclients);

        //-------Database  local by SharedPreferences
        ls_name_login=getIntent().getStringExtra("name");
        ls_phone_test   =getIntent().getStringExtra("phone");
        //------------------- Declear
        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        card = (EditText)findViewById(R.id.card);
        cash = (EditText)findViewById(R.id.cash);
        buy = (EditText)findViewById(R.id.buy);
        total = (TextView)findViewById(R.id.total);
        date = (TextView) findViewById(R.id.date);
        button_save =(Button)findViewById(R.id.button_save);
        buy_details=(EditText)findViewById(R.id.buy_details);
        //--------- Set Data
        setData();
        //------ Calc Total
        buy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                ls_buy = editable.toString();
                CalcTotal();
            }
        });
        cash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                ls_cash = editable.toString();
                CalcTotal();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddClients.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month+1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //  DialogFragment newFragment = new DataPickerFragment();
                // newFragment.show(getFragmentManager(),"datepicker");
            }
        });
        //-------- Set Data
        CalcTotal();
        //--------- Button Save
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        setData();
       if( validation_data() ){

        saveData();

       //------ go next page
       Intent intent = new Intent(AddClients.this,ClientsList.class);
       intent.putExtra("name", ls_name_login);

           startActivity(intent);
            }

            }
        });
        //---------

    }

    public void createNewEmployee (){
        String  id_employee ;
        int id = 1 ;
        SharedPreferences sh = getSharedPreferences("newemployee", MODE_PRIVATE);

        id = sh.getAll().size();
        id_employee = "employee_"+id;
        ls_username = id + name.getText().toString();
        SharedPreferences.Editor edit = sh.edit();
        edit.putString("username",ls_username);
        edit.putString(id_employee,name.getText().toString());
        edit.apply();
        Log.d(TAG, "createNewEmployee: "+sh.getAll());
    }
    public void saveData (){
       //------- create employee
        createNewEmployee();
        //------ create employee details
        SharedPreferences sh = getSharedPreferences(ls_username, MODE_PRIVATE);

        SharedPreferences.Editor edit = sh.edit();
        edit.putString("username",ls_username);
        edit.putString("name",name.getText().toString());
        edit.putString("phone",phone.getText().toString());
        edit.putString("card",card.getText().toString());
        edit.putString("Date",date.getText().toString());
        edit.putString("remainded",ls_total);
        edit.putString("id","0");

        edit.apply();

        //----Tracking
        track.put("cash",cash.getText().toString());
        track.put("buy",buy.getText().toString());
        track.put("date",date.getText().toString());
        track.put("buy_details",buy_details.getText().toString());
        ls_user_tracks = ls_username+"_tracks";
        SharedPreferences sh_track = getSharedPreferences(ls_user_tracks, MODE_PRIVATE);
        SharedPreferences.Editor edit_track = sh_track.edit();

        edit_track.putString("track0", track.toString());

        edit_track.apply();
        //------ complete
        Toast.makeText(this,"saved",Toast.LENGTH_LONG).show();

    }

    private Boolean validation_data() {
        if (ls_name.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال اسم العميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_phone.isEmpty() || ls_phone.length() < 11){
            Toast.makeText(this, "من فضلك... قم بإدخال تليفون العميل", Toast.LENGTH_SHORT).show(); return false ;}
      //  if (ls_card.isEmpty()) {Toast.makeText(this, "من فضلك... قم بإدخال رقم عضويه العميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_cash.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال المبلغ المدفوع الخاص بالعميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_buy.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال مبلغ المشتريات الخاص بالعميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_date.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال تاريخ إدخال العميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_buy_details.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال تفاصيل مشتريات العميل", Toast.LENGTH_SHORT).show(); return false ;}

        return true ;
    }

    private void setData (){
        ls_name = name.getText().toString();
        ls_phone = phone.getText().toString();
        ls_card  = card.getText().toString();
        ls_cash = cash.getText().toString();
        ls_buy  = buy.getText().toString();
        ls_date =date.getText().toString();
        ls_buy_details=buy_details.getText().toString();
        //------
      /*  Date calendar = Calendar.getInstance().getTime();
        Integer day = calendar.getDate();
        Integer month = calendar.getMonth();
        Integer year = calendar.getYear();

        //united destriputer - -115-
        date.setText(year+"/"+month+"/"+day);
        date.setText(year.toString());*/

    }

    private void  CalcTotal (){
        //-------
        if (ls_buy.isEmpty()) ls_buy="0.0";
        if(ls_cash.isEmpty()) ls_cash="0.0";
        //--------
        ld_buy = Double.parseDouble(ls_buy) + 0.0 ;
        ld_cash = Double.parseDouble(ls_cash)+0.0 ;
        ld_total = ld_buy - ld_cash ;
        ls_total = ld_total.toString() ;
        ls_Remainder=ls_total;
        //-----
        this.total.setText(ls_total);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));

        // builder.
        builder.setMessage("هل انت متاكد من إلغاء انشاء عميل جديد؟").setCancelable(false).setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(AddClients.this ,ClientsList.class);
                intent.putExtra("name", ls_name_login);
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
