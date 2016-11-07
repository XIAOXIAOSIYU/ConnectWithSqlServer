package com.jackzhao.www.connectwithsqlserver;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionClass {

    String DATABASE_IP = "192.168.0.12";
    String DATABASE_CONNECT_CLASSES = "net.sourceforge.jtds.jdbc.Driver";
    String DATABASE_NAME = "AndroidLogin";
    String DATABASE_USERNAME = "sa";
    String DATABASE_PASSWORD = "20080218";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection _conn = null;
        String _connURL = null;

        try {

            Class.forName(DATABASE_CONNECT_CLASSES);
            _connURL = "jdbc:jtds:sqlserver://" + DATABASE_IP + ";"
                    + "databaseName=" + DATABASE_NAME + ";user=" + DATABASE_USERNAME + ";"
                    + "password=" + DATABASE_PASSWORD + ";";

            _conn = DriverManager.getConnection(_connURL);

        } catch (SQLException sex) {
            Log.e("ERROR1:", sex.getMessage());
        } catch (ClassNotFoundException cnfex) {
            Log.e("ERROR2:", cnfex.getMessage());
        } catch (Exception ex) {
            Log.e("ERROR3:", ex.getMessage());
        }

        return _conn;
    }

}
