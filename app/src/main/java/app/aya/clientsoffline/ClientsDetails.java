package app.aya.clientsoffline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egypt2 on 18-Dec-18.
 */

public class ClientsDetails extends Activity {


    TextView new_paid  , last_date ,cost_paid , whatsapp , client_name , client_card;
    String ls_id ,ls_username ,ls_clientname ;
    private String ls_phone;
    private String ls_card;

    DatabaseReference databaseReference  , databaseclientremainded;
    List<DataPaid> list_dataclients ;
    private String databasename;
    private String ls_id_client ,ls_last_date;
    ListView list_view;
    private String ls_remainded;
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
        //--------
        ls_id_client=getIntent().getStringExtra("ID");
        ls_clientname=getIntent().getStringExtra("clientname");
        ls_username=getIntent().getStringExtra("username");
        ls_phone=getIntent().getStringExtra("phone");
        ls_card = getIntent().getStringExtra("card");
        ls_last_date = getIntent().getStringExtra("Date");
        ls_remainded = getIntent().getStringExtra("remainded");
      //  Toast.makeText(this, ls_phone, Toast.LENGTH_SHORT).show();
        //-------------
        client_name.setText(ls_clientname);
        whatsapp.setText(ls_phone);
        client_card.setText(ls_card);
        //-------Database name
        databasename = "Tracks_" + ls_username;
       // Toast.makeText(this, databasename, Toast.LENGTH_SHORT).show();
        //-------Database Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference(databasename).child(ls_id_client);
        databaseReference.keepSynced(true);
        //---------
        new_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ClientsPaid.class);
                    intent.putExtra("username", ls_username);
                    intent.putExtra("clientname", ls_clientname);
                    intent.putExtra("phone", ls_phone);
                    intent.putExtra("card", ls_card);
                    intent.putExtra("clientid", ls_id_client);
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

    private void alartTest( ) {
        final Button button_call , button_whats , button_cancle;
        final AlertDialog.Builder  builder = new AlertDialog.Builder(ClientsDetails.this);

        final View listViewClient = getLayoutInflater().inflate(R.layout.layout_call,null);
        //-----------
        button_call = (Button) listViewClient.findViewById(R.id.number);
        button_whats = (Button) listViewClient.findViewById(R.id.whats);
        button_cancle= (Button) listViewClient.findViewById(R.id.cancle);

        //-------------------
        builder.setView(listViewClient);
        final AlertDialog alertDialog = builder.create();

        //------------------
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01001059357"));
                startActivity(intent);
            }
        });

        button_whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+2001001059357"));
                startActivity(intent);

            }
        });

        button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        //--------------
        alertDialog.show();
        /*
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
        al.setNegativeButton("إالغاء", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });
        al.show();
*/
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
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_dataclients.clear();
                for(DataSnapshot datapaid : dataSnapshot.getChildren()){
                    DataPaid client  = datapaid.getValue(DataPaid.class);
                    //    Toast.makeText(ClientsList.this, client.getUser_Name(), Toast.LENGTH_SHORT).show();
                    list_dataclients.add(client);

                }
                ListViewAdapterClientTracks adapter = new ListViewAdapterClientTracks(ClientsDetails.this, list_dataclients ,ls_username ,ls_id_client , ls_phone , ls_card);
                list_view.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Long old_id;
                    String ls_old_remainded;
                    old_id = dataSnapshot.getChildrenCount();
                    databaseclientremainded = FirebaseDatabase.getInstance().getReference(databasename).child(ls_id_client).child(old_id.toString());
                    databaseclientremainded.keepSynced(true);
                    //------
                    DataSnapshot remainded =  dataSnapshot.child(old_id.toString());
                    DataPaid  last_remainded =  remainded.getValue(DataPaid.class);
                    ls_remainded =  last_remainded.getRemainder();
                    ls_last_date = last_remainded.getUser_date();
                    //----- Set last_date
                    last_date.setText(ls_last_date);
                    cost_paid.setText(ls_remainded);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        super.onStart();
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ClientsDetails.this ,ClientsList.class);
        intent.putExtra("username", ls_username);
        intent.putExtra("ID", ls_id_client);
        intent.putExtra("clientname", ls_clientname);
        intent.putExtra("ID", ls_id_client);
        intent.putExtra("phone", ls_phone);
        intent.putExtra("card", ls_card);
        startActivity(intent);


    }



}
