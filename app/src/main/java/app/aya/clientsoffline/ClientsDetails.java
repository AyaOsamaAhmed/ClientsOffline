package app.aya.clientsoffline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsDetails extends Activity {


    TextView new_paid  , last_date ,cost_paid , whatsapp , client_name , client_card;
    String  ls_username ,ls_clientname ,ls_position ,ls_last_date;
    private String ls_phone ,ls_card;
    List<DataPaid> list_dataclients ;
    ListView list_view;
    private String ls_remainded ,ls_result;
    HashMap<String,String> hash_client_track;
    ArrayList<HashMap<String,String>> arrayList_employee_track;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);

        cost_paid = (TextView) findViewById(R.id.cost_paid);
        last_date = (TextView)findViewById(R.id.last_date);
        new_paid = (TextView)findViewById(R.id.new_paid);
        list_view = (ListView)findViewById(R.id.trackslist);
        client_name=(TextView)findViewById(R.id.name);
        whatsapp = (TextView) findViewById(R.id.whatsapp);
        client_card =(TextView)findViewById(R.id.card);
        list_dataclients = new ArrayList<>();

        hash_client_track = new HashMap<String, String>();
        arrayList_employee_track = new ArrayList<HashMap<String, String>>();
        //--------
         ls_username =getIntent().getStringExtra("username");
         ls_position = getIntent().getStringExtra("position");
         retriveData(ls_position+ls_username);
         //-------------
        client_name.setText(ls_clientname);
        whatsapp.setText(ls_phone);
        client_card.setText(ls_card);
        last_date.setText(ls_last_date);
        cost_paid.setText(ls_remainded);
        //---------
        new_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ClientsPaid.class);
                    intent.putExtra("username", ls_username);
                    startActivity(intent);
            }
        });

       registerForContextMenu(list_view);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+20"+ls_phone+"&text= إجمالى المتبقى "+ls_remainded));
                startActivity(intent);
            }
        });
    }

    private void retriveData(String ls_username) {

        SharedPreferences sh = getSharedPreferences(ls_username, MODE_PRIVATE);

         ls_clientname=sh.getString("name","");
         ls_phone= sh.getString("phone","");
        ls_card = sh.getString("card","");
        ls_last_date = sh.getString("Date","");
        ls_remainded = sh.getString("remainded","");
        //----Tracking
        SharedPreferences sh_track = getSharedPreferences(ls_username+"_tracks", MODE_PRIVATE);

       int  len =  sh_track.getAll().size();
        Log.d(TAG, "retrieveemployee: length" + len);
        if (len!=0){
            for(int i = 0 ;i < len;i++ ){
              ls_result = sh_track.getString("track"+i,"");
              //  {buy_details=مشتريات, buy=6805, cash=6009, date=12/6/2019}
                String[] pairs = ls_result.split(",");

                for (int ii=0;ii<pairs.length;ii++) {
                    String pair = pairs[ii];
                    pair = pair.replace("}","");
                    String[] keyValue = pair.split("=");
                    hash_client_track.put(keyValue[0],keyValue[1]);
                }

                arrayList_employee_track.add(hash_client_track);
            }}
        //------ complete
        list_view.setAdapter(new ListViewAdapterClientTracks(ClientsDetails.this,arrayList_employee_track ,ls_username ,ls_position,ls_phone ,ls_card ,ls_remainded));


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menulistview,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                Toast.makeText(this, "سوف يتم حذف هذه العمليه", Toast.LENGTH_SHORT).show();
                return true ;
            default:
                return super.onContextItemSelected(item);
        }


    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ClientsDetails.this ,ClientsList.class);
        intent.putExtra("username", ls_username);

        startActivity(intent);


    }



}
