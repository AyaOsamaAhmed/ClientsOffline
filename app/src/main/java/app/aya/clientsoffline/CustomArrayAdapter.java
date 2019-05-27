package app.aya.clientsoffline;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by egypt2 on 03-Jan-19.
 */

public class CustomArrayAdapter  extends ArrayAdapter<String> {
    Context context ;
    ArrayList<String> arrayList_data ;
    LayoutInflater layoutInflater ;
    String ls_username ;
    List<DataClients> list_clients ;
    HashMap<String, Integer> hashmap  ;
      public CustomArrayAdapter(Context context, ArrayList<String> arrayList_data , String ls_username , List<DataClients> list_data , HashMap<String, Integer> hashmap ) {

        super(context, R.layout.clientslist_inside,arrayList_data);
        this.context = context;
        this.arrayList_data = arrayList_data;

        this.layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
          this.ls_username = ls_username ;
          list_clients = new ArrayList<>();
          list_clients = list_data ;
          this.hashmap = hashmap;
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.clientslist_inside,null);
        TextView item = (TextView)convertView.findViewById(R.id.Client_name);
        Button bt =(Button)convertView.findViewById(R.id.button_details);

        item.setText(arrayList_data.get(position));

         bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context,ClientsDetails.class);
               // Toast.makeText(context,hashmap.get(arrayList_data.get(position))+"", Toast.LENGTH_SHORT).show();

                final DataClients dataClients = list_clients.get(hashmap.get(arrayList_data.get(position)));
                intent.putExtra("ID",dataClients.getUser_id());
                intent.putExtra("username",ls_username);
                intent.putExtra("clientname",dataClients.getClient_name());
                intent.putExtra("phone",dataClients.getUser_phone());
                intent.putExtra("card",dataClients.getUser_card());
                context.startActivity(intent);
            }
        });

        return convertView;


    }
}
