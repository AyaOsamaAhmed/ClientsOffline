package app.aya.clientsoffline;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aya on 11/1/2016.
 */
public class ListViewAdapterClientTracks extends BaseAdapter {

    // Declare Variables

    Activity context;
    String ls_username ,ls_client_id , phone , card;
    List<DataPaid> list_clientTracks ;
    Integer list_position = 0 ;

    public ListViewAdapterClientTracks(Activity context,
                                       List<DataPaid> list_clientTracks, String username , String ls_client_id , String phone , String card ) {

        this.context = context;
        ls_username = username;
        this.ls_client_id = ls_client_id ;
        this.list_clientTracks = list_clientTracks;
        this.phone = phone;
        this.card = card ;
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
        final DataPaid dataPaid = list_clientTracks.get(position);
        //----- set Text
        last_paid.setText(dataPaid.getUser_cash());
        last_buy.setText(dataPaid.getUser_buy());
        last_date.setText(dataPaid.getUser_date());

        listViewClient.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
              //  Toast.makeText(context, "long click--"+position, Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        // Locate the ImageView in listview_item.xml
//        imglink = (ImageView) itemView.findViewById(R.id.image);

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
       // imageLoader.DisplayImage("http://52.41.120.12:8080/Restaurant/uploadedFiles/"+resultp.get(MenuParent.IMG), imglink);

        //
        //convertview.setOnClickListener(new CustomOnClickListener(callback, position));
        //
        img_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent intent = new Intent( context ,ClientsPaid.class);

                intent.putExtra("details",true);
                intent.putExtra("ID",dataPaid.getPaid_id());
                intent.putExtra("clientid",ls_client_id);
                intent.putExtra("username",ls_username);
                intent.putExtra("clientname",dataPaid.getClient_Name());
                intent.putExtra("buy",dataPaid.getUser_buy());
                intent.putExtra("paid",dataPaid.getUser_cash());
                intent.putExtra("phone",phone);
                intent.putExtra("card",card);
                intent.putExtra("buy_details",dataPaid.getPaid_details());
                intent.putExtra("date",dataPaid.getUser_date());
                context.startActivity(intent);

            }
        });

        return listViewClient;

    }
}


