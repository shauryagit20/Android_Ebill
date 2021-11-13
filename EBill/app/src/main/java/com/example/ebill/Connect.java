package com.example.ebill;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LongDef;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection getConnection()
    {
        String ip =  "192.168.1.14";
        String port =  "1433";
        String uname = "sa";
        String database =  "GloablEbill";
        String pass =  "Tower@11" ;
        Connection conn =  null;

        String driver =  "net.sourceforge.jtds.jdbc.Driver";
        StrictMode.ThreadPolicy policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try
        {
            Log.d("TRY", "getConnection: Trying");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String ConnectionURL= "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="+ database+";user="+uname+";password="+pass+";";
            conn =  DriverManager.getConnection(ConnectionURL);
            return conn;
        } catch (SQLException | ClassNotFoundException se ) {
            Log.d("TRY", "getConnection: Trying");
            Log.d("Connection Failed",  "getConnection: " +  se.getMessage());
        }
        return conn;
    }

}
