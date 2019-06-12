package app.aya.clientsoffline;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by aya on 11/1/2016.
 */
public class ListViewAdapterClientTracks extends BaseAdapter {

    // Declare Variables

    Activity context;
    String ls_username ,ls_client_id , ls_phone , ls_card ,ls_remaind , ls_position;
    ArrayList<HashMap<String, String>>  list_clientTracks ;
    Integer list_position = 0 ;

    public ListViewAdapterClientTracks(Activity context,
                                       ArrayList<HashMap<String, String>>   list_clientTracks, String username,String position ,String phone , String card , String remaind ) {

        this.context = context;
        ls_username = username;
        ls_card=card;
        ls_phone=phone;
        ls_remaind = remaind;
        ls_position=position;
        this.list_clientTracks = list_clientTracks;
       // resultp = list_clients.get(0);

    }

    @Override
    public int getCount() {
        return list_clientTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return list_clientTracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup viewGroup) {
        // Declare Variables
        TextView last_paid , last_buy , last_date ;
        ImageButton img_details ;
        //---- customer inside
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewClient = inflater.inflate(R.layout.customer_detail_inside, null, true);
        // Locate the TextViews
        last_paid = (TextView) listViewClient.findViewById(R.id.last_paid);
        last_buy = (TextView) listViewClient.findViewById(R.id.last_buy);
        last_date = (TextView) listViewClient.findViewById(R.id.last_date);
        img_details = (ImageButton) listViewClient.findViewById(R.id.img_details);
        //------- position
        final HashMap<String, String> dataPaid = list_clientTracks.get(position);
        //----- set Text
        String x = dataPaid.get(" cash");
        Log.d(TAG, "getView: cash:"+x);
        last_paid.setText(dataPaid.get(" cash"));
        last_buy.setText(dataPaid.get(" buy"));
        last_date.setText(dataPaid.get("{date"));

        listViewClient.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
              //  Toast.makeText(context, "long click--"+position, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        img_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent intent = new Intent( context ,ClientsPaid.class);

                intent.putExtra("details",true);
                intent.putExtra("username",ls_username);
                intent.putExtra("clientname",ls_username);
                intent.putExtra("phone",ls_phone);
                intent.putExtra("card",ls_card);
                intent.putExtra("remaind",ls_remaind);
                intent.putExtra("position",ls_position);
                intent.putExtra("paid",dataPaid.get(" cash"));
                intent.putExtra("buy",dataPaid.get(" buy"));
                intent.putExtra("date",dataPaid.get("{date"));
                intent.putExtra("buy_details",dataPaid.get(" buy_details"));
                context.startActivity(intent);

            }
        });

        return listViewClient;

    }
}


