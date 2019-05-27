package app.aya.clientsoffline;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aya on 11/1/2016.
 */
public class ListViewAdapterClients extends BaseAdapter {

    // Declare Variables

    Activity context;
    String ls_username ;
    List<DataClients> list_clients ;
    Integer list_position = 0 ;
    public ListViewAdapterClients(Activity context,
                                  List<DataClients> list_clients, String username ) {

        this.context = context;
        ls_username = username;
        this.list_clients = list_clients;
       // resultp = list_clients.get(0);

    }

    @Override
    public int getCount() {
        return list_clients.size();
    }

    @Override
    public Object getItem(int position) {
        return list_clients.get(position);
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


            final DataClients dataClients = list_clients.get(position);
            //

            Client_name.setText(dataClients.getClient_name());


        // Locate the ImageView in listview_item.xml
//        imglink = (ImageView) itemView.findViewById(R.id.image);

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
       // imageLoader.DisplayImage("http://52.41.120.12:8080/Restaurant/uploadedFiles/"+resultp.get(MenuParent.IMG), imglink);

        //
        //convertview.setOnClickListener(new CustomOnClickListener(callback, position));
        //
        button_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent( context ,ClientsDetails.class);
                //intent.putExtra("username",resultp.get(key));

                intent.putExtra("ID",dataClients.getUser_id());
                intent.putExtra("username",ls_username);
                intent.putExtra("clientname",dataClients.getClient_name());
                intent.putExtra("phone",dataClients.getUser_phone());
                intent.putExtra("card",dataClients.getUser_card());
                context.startActivity(intent);
            }
        });

        return listViewClient;

    }
}


