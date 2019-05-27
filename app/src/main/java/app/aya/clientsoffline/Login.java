package app.aya.clientsoffline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by egypt2 on 10-Dec-18.
 */

public class Login extends Activity {

    EditText username, password;
    String ls_username, ls_password;
    Button login ;
    TextView start, tab_start , tab_Privacy ,Privacy , Login, tab_login ,add_client , tab_add_client , tab_contact_us , tab_recordclients,tab_search;

    List<DataUsers> list_datausers;
    private String databasename;
    private int height_start , height_Privacy , height_Login, height_add_client , height_contact_us , height_recordclients , height_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.button_login);

        list_datausers = new ArrayList<>();

        //---------
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                //  Toast.makeText(Login.this,ls_username, Toast.LENGTH_SHORT).show();
                if (ls_username.isEmpty()) {
                    Toast.makeText(Login.this, "من فضلك قم بإدخال اسم المستخدم الخاص بك", Toast.LENGTH_SHORT).show();
                } else if (ls_password.isEmpty()) {
                    Toast.makeText(Login.this, "من فضلك قم بإدخال كلمه المرور الخاص بك", Toast.LENGTH_SHORT).show();
                } else if (ls_username.equals("SecretLogin")) {
                    Intent intent = new Intent(Login.this, AddUser.class);
                    startActivity(intent);

                } else if (checkUsers(ls_username, ls_password)) {
                    //        Toast.makeText(Login.this,ls_username, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ClientsList.class);
                    intent.putExtra("username", ls_username);
                    startActivity(intent);
                }
            }
        });
        //-------------------

        createDeclearLayout();
        // https://www.youtube.com/watch?v=5ETJWUuH1Ag
                /*Intent intent = new Intent(getApplicationContext(), ClientsList.class);
                intent.putExtra("username", "test");

                startActivity(intent);*/
        //---------------

    }

    private void createDeclearLayout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

        View listViewClient = getLayoutInflater().inflate(R.layout.layout_phone, null);
        start = (TextView) listViewClient.findViewById(R.id.start);
        tab_start = (TextView) listViewClient.findViewById(R.id.tab_start);

        Privacy = (TextView) listViewClient.findViewById(R.id.Privacy);
        tab_Privacy = (TextView) listViewClient.findViewById(R.id.tab_Privacy);

        Login = (TextView)listViewClient.findViewById(R.id.login);
        tab_login = (TextView)listViewClient.findViewById(R.id.tab_login);

        add_client = (TextView) listViewClient.findViewById(R.id.add_client);
        tab_add_client = (TextView) listViewClient.findViewById(R.id.tab_add_client);

        tab_search = (TextView)listViewClient.findViewById(R.id.tab_search);

        tab_recordclients=(TextView)listViewClient.findViewById(R.id.tab_record_client);

        tab_contact_us=(TextView)listViewClient.findViewById(R.id.tab_contact_us);
        builder.setNegativeButton("إغلاق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }

        });
        builder.setView(listViewClient);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //----------- set height
        setHeight();

        //------------

    }

    private void setData() {
        ls_username = username.getText().toString();
        ls_password = password.getText().toString();
    }


    private Boolean checkUsers(String user, String pass) {

        for (int i = 0; i < list_datausers.size(); i++) {
            DataUsers datausers = list_datausers.get(i);
            if (user.equals(datausers.getUser_Name())) {
                if (pass.equals(datausers.getPassword())) {
                    return true;
                } else {
                    Toast.makeText(this, "كلمه المرور غير صحيحه ", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

        }
        Toast.makeText(this, "اسم المستخدم غير صحيح او غير مسجل .. ارجو التسجيل", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("هل انت متاكد انك تريد الخروج").setCancelable(false).setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        }).setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public void setHeight(){
        getHeight("S", tab_start.getHeight());
        getHeight("P", tab_Privacy.getHeight());
        getHeight("L",tab_login.getHeight());
        getHeight("C", tab_add_client.getHeight());
        getHeight("R", tab_recordclients.getHeight());
        getHeight("search", tab_search.getHeight());
        getHeight("Contact", tab_contact_us.getHeight());

    }

    public void setHeightZero(){
        tab_start.setHeight(0);
        tab_Privacy.setHeight(0);
        tab_login.setHeight(0);
        tab_add_client.setHeight(0);
        tab_search.setHeight(0);
        tab_recordclients.setHeight(0);
        tab_contact_us.setHeight(0);
    }

    private void getHeight(String s, int height) {
        if (height != 0) {
            switch (s) {
                case "S":
                    height_start = height;
                    break;
                case "P":
                    height_Privacy = height;
                    break;
                case "L":
                    height_Login = height;
                    break;
                case "C":
                    height_add_client = height;
                    break;
                case "search":
                    height_search=height;
                    break;
                case "R":
                    height_recordclients=height;
                    break;
                case "Contact":
                    height_contact_us = height ;
                    break;
            }

        }
    }

    public void tabStart(View view) {
             setHeight();
        setHeightZero();
            tab_start.setHeight(height_start);
            // width , eight
    }

    public void tabPrivacy(View view) {
        setHeight();
        setHeightZero();
            tab_Privacy.setHeight(height_Privacy);
            // width , height
    }

    public void tabAdd_client(View view) {
        setHeight();
        setHeightZero();
            tab_add_client.setHeight(height_add_client);
            // width , height

    }

    public void tabContact_us(View view) {

        setHeight();
        setHeightZero();
            tab_contact_us.setHeight(height_contact_us);
            // width , height
    }

    public void tab_recordclient(View view) {

        setHeight();
        setHeightZero();
            tab_recordclients.setHeight(height_recordclients);
            // width , height
    }

    public void tabSearch(View view) {

        setHeight();
        setHeightZero();
            tab_search.setHeight(height_search);
            // width , height
    }

    public void tablogin(View view) {
        setHeight();
        setHeightZero();
        tab_login.setHeight(height_Login);
        // width , height
    }


}
