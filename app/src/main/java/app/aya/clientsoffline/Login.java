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
    List<DataUsers> list_datausers;

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

         // https://www.youtube.com/watch?v=5ETJWUuH1Ag
                /*Intent intent = new Intent(getApplicationContext(), ClientsList.class);
                intent.putExtra("username", "test");

                startActivity(intent);*/
        //---------------

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



}
