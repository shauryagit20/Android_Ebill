package com.example.ebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b =  (Button) findViewById(R.id.Button_Label);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Sign_up_option_page.class);
                startActivity(i);
            }
        });
        Button SignIn = (Button) findViewById(R.id.Sign_In_Button);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Username_View =  (EditText) findViewById(R.id.Username_Entry);
                EditText Password_View = (EditText) findViewById(R.id.Password_Entry);
                String Password =  Password_View.getText().toString();
                String Username_String =  Username_View.getText().toString();
                Connection com =  Connect.getConnection();
                while (com == null)
                {
                    Log.d("SQL", "onClick: NULL");
                }
                String q = "Select Username from Accounts ";
                if (Username_View.getText().toString().length() > 0 && Password_View.getText().toString().length() > 0 )
                {
                    try {
                        if(uid_exists(Username_String,q,com)!=null){
                            if(check_password(Password, Username_String, com))
                            {
                                Intent i =  new Intent(MainActivity.this,QRCodedisplay.class);
                                i.putExtra("Name",fetch_company(Username_String,com));
                                Toast.makeText(getApplicationContext(),"Comnected",Toast.LENGTH_LONG).show();
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Credentials do nt match",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Credentials do nt match",Toast.LENGTH_LONG).show();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter the username as well as the password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private static String uid_exists (String unique_id, String q ,  Connection con) throws SQLException {
        Statement st =  con.createStatement();
        ResultSet set  =  st.executeQuery(q);
        String id = null;
        boolean value_exists =  false;

        while(set.next())
        {
            String uid =  set.getString(1);
            if (uid.equalsIgnoreCase(unique_id))
            {
                return uid;
            }
        }
        return null;
    }
    private static String fetch_company (String uid, Connection Con) throws SQLException
    {
        String q =  "Select Petrol_Pump_Name where Username  =  ? ";
        PreparedStatement pstmt =  Con.prepareStatement(q);
        pstmt.setString(1,uid);
        ResultSet set =  pstmt.executeQuery();
        while (set.next())
        {
            String Comapny_Name =  set.getString(1);
            return Comapny_Name;
        }

        return "";
    }

    private static boolean check_password(String Password, String uid ,  Connection con) throws SQLException {
        Log.d("check pass", "check_password:In connect password ");
        String q  =  "Select Password where Username = ?";
        PreparedStatement pstmt =  con.prepareStatement(q);
        pstmt.setString(1,uid);
        ResultSet set =  pstmt.executeQuery();
        while (set.next())
        {
            String Password_Found =  set.getString(1);
            if (Password_Found.equals(Password)) {
                return true;
            }
        }
       return false;
    }
}