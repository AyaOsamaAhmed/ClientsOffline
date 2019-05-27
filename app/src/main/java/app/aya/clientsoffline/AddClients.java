package app.aya.clientsoffline;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * Created by egypt2 on 11-Dec-18.
 */

public class AddClients extends Activity {

    EditText name ,phone , card ,cash ,buy ,buy_details ;
    TextView total ,date ;
    Button button_save ;
    String ls_id ,ls_name , ls_phone , ls_card , ls_cash ="0.0" , ls_buy="0.0" , ls_total="0.0" , ls_date,ls_Remainder ;
    Double ld_buy=0.0 , ld_cash=0.0 , ld_total=0.0 ;
    String databasename;
    DataClients         dataClients;
    DataPaid            dataPaid    ;
    DatabaseReference   databaseclients , databasetracks;
    DatabaseReference   databaseReference;
    DatePickerDialog datePickerDialog ;
      String ls_username ,ls_phone_test  ,ls_buy_details;
    int                     client_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclients);
        //-------Database Firebase
        ls_username=getIntent().getStringExtra("username");
        ls_phone_test   =getIntent().getStringExtra("phone");
        databasename = "Clients_" + ls_username;
       // Toast.makeText(this, databasename, Toast.LENGTH_SHORT).show();
        // name clients
        databaseclients = FirebaseDatabase.getInstance().getReference(databasename);
        databaseclients.keepSynced(true);
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
        /*----------- Test Client
        if (ls_username.equals("test")) {
            phone.setText(ls_phone_test);
            phone.setEnabled(false);
            buy_details.setText("تحت الاختبار");
            buy_details.setEnabled(false);
        }
        */

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
        String id = databaseclients.push().getKey();
        ls_id = id ;
        dataClients  = new DataClients(ls_id ,ls_name,ls_card,ls_phone,ls_cash,ls_buy,ls_date,ls_Remainder);
        databaseclients.child(id).setValue(dataClients);
        Toast.makeText(AddClients.this, "Saved Data Sucsses", Toast.LENGTH_SHORT).show();
        //----- Tracks Database
           String Track_id ="1";
           String databasename_Tracks = "Tracks_" + ls_username;

           databasetracks = FirebaseDatabase.getInstance().getReference(databasename_Tracks).child(ls_id);
            databasetracks.keepSynced(true);
           dataPaid  = new DataPaid(Track_id ,ls_name,ls_cash,ls_buy,ls_buy_details,ls_date,ls_Remainder);
           databasetracks.child(Track_id).setValue(dataPaid);

       //------ go next page
       Intent intent = new Intent(AddClients.this,ClientsList.class);
       intent.putExtra("username", ls_username);
           intent.putExtra("phone", ls_phone);
       startActivity(intent);
            }

            }
        });
        //---------

    }

    @Override
    protected void onStart() {
     String databasename = "Clients_" + ls_username;
        // Toast.makeText(this, databasename, Toast.LENGTH_SHORT).show();
        //-------Database Firebase intent.putExtra("username", ls_username);
        databaseReference = FirebaseDatabase.getInstance().getReference(databasename);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataclients : dataSnapshot.getChildren()){
                    DataClients client  = dataclients.getValue(DataClients.class);
                    //    Toast.makeText(ClientsList.this, client.getUser_Name(), Toast.LENGTH_SHORT).show();

                }


                }
                //   ListViewAdapterClients adapter = new ListViewAdapterClients(ClientsList.this, list_dataclients ,ls_username );
                // list_view.setAdapter(adapter);


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onStart();
    }
    private void alartTest(String message) {

        AlertDialog.Builder al = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Material_Wallpaper));
            al.setMessage(message);
        al.setCancelable(false).setPositiveButton("الاتصال على رقمى", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:01001059357"));
                    startActivity(intent);

                }
            }).setCancelable(false)
        .setPositiveButton("محادثه عن طريق الواتس أب", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+2001001059357"));
                    startActivity(intent);


                }
            });
        al.setNegativeButton("الرجوع", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(AddClients.this ,ClientsList.class);
                    intent.putExtra("username", ls_username);
                    startActivity(intent);
                    dialog.cancel();
                }
            });
            al.show();

    }

    public void alart(String message ) {
        AlertDialog al = new AlertDialog.Builder(this).create();
        al.setMessage(message);
        al.setButton("الاتصال على رقمى", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        al.setButton2("MOBILEDATA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AddClients.this.startActivity(new Intent("android.settings.DATA_ROAMING_SETTINGS"));
            }
        });
        al.setButton3("WIFI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AddClients.this.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
            }
        });
        al.show();
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
                intent.putExtra("username", ls_username);
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
