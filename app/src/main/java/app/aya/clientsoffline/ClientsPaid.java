package app.aya.clientsoffline;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsPaid extends Activity {

    Button button_save ;
    TextView date ;
    LinearLayout main_layout ;
    EditText buy , paid, buy_details ;
    String ls_id_client ,ls_username , ls_user_tracks ,ls_clientname ,ls_old_id;
    String ls_date ,ls_buy , ls_paid , ls_buy_details , ls_total ,ls_phone ,ls_card  , ls_old_remainded, ls_new_remainded ,ls_position;
    ImageView whatsapp;
    Long old_id ;
    Double old_remainded;

    DatePickerDialog datePickerDialog ;
    HashMap<String,String>  track = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paid_details);

        date = (TextView)findViewById(R.id.date);
        buy  = (EditText)findViewById(R.id.buy);
        paid = (EditText)findViewById(R.id.paid);
        buy_details = (EditText)findViewById(R.id.buy_details);
        button_save =(Button)findViewById(R.id.button_save);
        whatsapp  = (ImageView)findViewById(R.id.whatsapp);
        //------ Details Part
        if(getIntent().getBooleanExtra("details",false)){
            ls_id_client = getIntent().getStringExtra("clientid");
            ls_username = getIntent().getStringExtra("username");
            ls_clientname = getIntent().getStringExtra("clientname");
            ls_phone = getIntent().getStringExtra("phone");
            ls_card = getIntent().getStringExtra("card");

            ls_buy  = getIntent().getStringExtra("buy");
            ls_paid = getIntent().getStringExtra("paid");
            ls_date = getIntent().getStringExtra("date");
            ls_buy_details = getIntent().getStringExtra("buy_details");

            //--------
            button_save.setVisibility(View.INVISIBLE);
            date.setEnabled(false);
            buy.setEnabled(false);
            paid.setEnabled(false);
            buy_details.setEnabled(false);
            //--------
            date.setText(ls_date);
            paid.setText(ls_paid);
            buy.setText(ls_buy);
            buy_details.setText(ls_buy_details);
            //---------

        }else {

            //------ receive data
            ls_username = getIntent().getStringExtra("username");
            ls_phone = getIntent().getStringExtra("phone");
            ls_card = getIntent().getStringExtra("card");
            ls_old_id =  getIntent().getStringExtra("id");

              // ----- button clicking
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    datePickerDialog = new DatePickerDialog(ClientsPaid.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    date.setText(day + "/" + (month + 1) + "/" + year);
                                }
                            }, year, month, dayOfMonth);
                    datePickerDialog.show();
                    // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    //  DialogFragment newFragment = new DataPickerFragment();
                    // newFragment.show(getFragmentManager(),"datepicker");
                }
            });

            button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData();
                    if (validation_data() ) {
                        CalcTotal();
                        Long id = GetIDTrack();
                        saveRetrive();
                        Toast.makeText(ClientsPaid.this, "Saved Data Sucsses", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ClientsPaid.this, ClientsDetails.class);
                        intent.putExtra("username", ls_username);
                        intent.putExtra("position",ls_position);
                        startActivity(intent);
                    }
                }


            });
        }

        //---------------
    }

    private void saveRetrive() {

        //----Tracking
        track.put("cash",ls_paid);
        track.put("buy",ls_buy);
        track.put("date",ls_date);
        track.put("buy_details",ls_buy_details);
        ls_user_tracks = ls_username+"_tracks";
        //----- update id
        GetIDTrack();
        //-------- save track
        SharedPreferences sh_track = getSharedPreferences(ls_user_tracks, MODE_PRIVATE);
        SharedPreferences.Editor edit_track = sh_track.edit();

        edit_track.putString("track"+old_id, track.toString());

        edit_track.apply();


        //------ complete
        Toast.makeText(this,"saved",Toast.LENGTH_LONG).show();

    }

    private Long GetIDTrack() {
        Long ls_new_id ;
        if(ls_old_id.equals("")){ls_old_id = "0";}
            old_id = Long.parseLong(ls_old_id) ;
            ls_new_id= old_id + 1 ;

        SharedPreferences sh = getSharedPreferences(ls_username, MODE_PRIVATE);

        SharedPreferences.Editor edit = sh.edit();
        edit.putString("id",ls_new_id.toString());
        edit.commit();
        return ls_new_id ;
    }


    @Override
    protected void onStart() {
        ls_position = getIntent().getStringExtra("position");
        ls_old_remainded = getIntent().getStringExtra("remaind");
        old_remainded = Double.parseDouble(ls_old_remainded);
                //------ Alart remainded
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ClientsPaid.this, android.R.style.Theme_Holo_Light));
                builder.setMessage("المبلغ المتبقى لهذا العميل "+ls_old_remainded).setCancelable(false).setPositiveButton("O.K", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.create().show();

        super.onStart();
    }

    private Boolean validation_data() {
        if (ls_buy_details.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال تفاصيل المشتريات للعميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_paid.isEmpty() || ls_buy.isEmpty() ) {}
        else if(!ls_paid.isEmpty() && !ls_buy.isEmpty()){} else {
            Toast.makeText(this, "من فضلك... قم بإدخال المبلغ المدفوع او مبلغ المشتريات الخاص بالعميل", Toast.LENGTH_SHORT).show(); return false ;}
        if (ls_date.isEmpty()) {
            Toast.makeText(this, "من فضلك... قم بإدخال تاريخ دفع او شراء للعميل", Toast.LENGTH_SHORT).show(); return false ;}

        return true ;
    }

    private void setData (){
        ls_date = date.getText().toString();
        ls_buy = buy.getText().toString();
        ls_buy_details  = buy_details.getText().toString();
        ls_paid = paid.getText().toString();

    }

    private void  CalcTotal (){
        Double ld_buy ,ld_paid ,ld_total ;
        //-------
        if (ls_buy.isEmpty()) ls_buy="0.0";
        if(ls_paid.isEmpty()) ls_paid="0.0";
        //--------
        ld_buy = Double.parseDouble(ls_buy) + 0.0 ;
        ld_paid = Double.parseDouble(ls_paid)+0.0 ;
        ld_total = (ld_buy+old_remainded) - ld_paid ;
        ls_new_remainded = ld_total.toString();

        //-----
       // this.total.setText(ls_total);
    }


    @Override
public void onBackPressed() {
        if (getIntent().getBooleanExtra("details", false)) {
            Intent intent = new Intent(ClientsPaid.this, ClientsDetails.class);
            intent.putExtra("username", ls_username);
            intent.putExtra("position",ls_position);
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));

            // builder.
            builder.setMessage("هل انت متاكد من إلغاء انشاء دفع جديد للعميل ؟").setCancelable(false).setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(ClientsPaid.this, ClientsDetails.class);
                    intent.putExtra("username", ls_username);
                    intent.putExtra("position",ls_position);
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
}
