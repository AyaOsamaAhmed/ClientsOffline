package app.aya.clientsoffline;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by aya on 11/1/2016.
 */
public class ListViewAdapterClients extends BaseAdapter {

    // Declare Variables

    Activity context;
    String ls_username ;
    Integer list_position = 0 ;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> pos_username = new HashMap<String, String>();


    public ListViewAdapterClients(Activity context,
                                  ArrayList<HashMap<String, String>> arraylist ,String username) {

        this.context = context;
        ls_username = username;
        data = arraylist;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "listview:length" + data.size());

        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup viewGroup) {
        // Declare Variables
        TextView Client_name ;
        Button button_details ;


        LayoutInflater inflater = context.getLayoutInflater();
        View listViewClient = inflater.inflate(R.layout.clientslist_inside, null, true);


        // Locate the TextViews
        Client_name = (TextView) listViewClient.findViewById(R.id.Client_name);
        button_details = (Button) listViewClient.findViewById(R.id.button_details);

        final HashMap<String, String> dataClients = data.get(position);
        //
        ls_username = dataClients.get("name"+position);
        pos_username.put(position+"",ls_username);
        Client_name.setText(dataClients.get("name"+position));
        //--------------------
        button_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = pos_username.get(position+"");
               Intent intent = new Intent( context ,ClientsDetails.class);
                intent.putExtra("username",name);
                intent.putExtra("position",position+"");
                context.startActivity(intent);
            }
        });

        return listViewClient;

    }
}


