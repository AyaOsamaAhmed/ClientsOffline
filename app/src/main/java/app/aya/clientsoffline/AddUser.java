package app.aya.clientsoffline;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by egypt2 on 26-Dec-18.
 */

public class AddUser extends Activity {

    EditText name ,phone , username ,password ,active ;
    TextView date ;
    Button button_save ;
    String ls_id ,ls_name , ls_phone , ls_username , ls_password  ,  ls_date ,ls_active;
    String databasename;
    DataUsers       dataUeser;

    DatePickerDialog datePickerDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser);
        //-------Database Firebase
        databasename = "Users";                                                      // name clients
         //------------------- Declear
        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        date = (TextView) findViewById(R.id.date);
        active = (EditText)findViewById(R.id.active);
        button_save =(Button)findViewById(R.id.button_save);
        //--------- Set Data
        setData();
        //------ Calc Total
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddUser.this,
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
        //--------- Button Save
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                if( validation_data()){
                     ls_id = "0" ;
                    dataUeser  = new DataUsers(ls_id ,ls_username,ls_name,ls_password,ls_phone,ls_date ,ls_active);

                     Toast.makeText(AddUser.this, "Saved Data Sucsses", Toast.LENGTH_SHORT).show();
                   addData();
                    Toast.makeText(AddUser.this, "you can add new User", Toast.LENGTH_SHORT).show();

                }
            }
        });
        //---------

    }

    private void addData() {
        name.setText("");
        phone.setText("");
        username.setText("");
        password.setText("");
        date.setText("");

    }

    private Boolean validation_data() {
        if (ls_name.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال اسم العميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_phone.isEmpty() || ls_phone.length()< 11 ){
            Toast.makeText(this, "من فضلك... قم بإدخال تليفون العميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_username.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال اسم المستخدم للعميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_phone.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال كلمه المرور الخاص بالعميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_date.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال تاريخ إدخال العميل", Toast.LENGTH_SHORT).show(); return false ;}

        return true ;
    }

    private void setData (){
        ls_name = name.getText().toString();
        ls_phone = phone.getText().toString();
        ls_username  = username.getText().toString();
        ls_password  = password.getText().toString();
        ls_phone = phone.getText().toString();
        ls_date =date.getText().toString();
        ls_active = active.getText().toString();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));

        // builder.
        builder.setMessage("هل انت متاكد من إلغاء انشاء عميل جديد؟").setCancelable(false).setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(AddUser.this ,Login.class);
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


