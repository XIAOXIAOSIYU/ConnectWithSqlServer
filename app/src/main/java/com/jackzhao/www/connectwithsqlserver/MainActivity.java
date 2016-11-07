package com.jackzhao.www.connectwithsqlserver;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    TextView lb_username, lb_password;
    EditText txt_username, txt_password;

    ProgressBar progressBar;

    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lb_username = (TextView) findViewById(R.id.lb_username);
        lb_password = (TextView) findViewById(R.id.lb_password);

        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }

    public void doLogin(View view) {

        String username = txt_username.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        CheckLogin login = new CheckLogin();
        login.execute(username, password);


    }

    public class CheckLogin extends AsyncTask<String, String, String> {

        String error_message = "";
        Boolean login_succeed = false;

        public CheckLogin() {
            super();
        }

        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String s) {

            String show_message = s;

            progressBar.setVisibility(View.GONE);

            if (login_succeed) {
                show_message = "Login Successful";
            }

            Toast.makeText(MainActivity.this, show_message, Toast.LENGTH_LONG).show();


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... strings) {

            String username = strings[0];
            String password = strings[1];

            if (username.equals("") || password.equals("")) {
                error_message = "Please enter UserName or Password";
            } else {

                try {
                    ConnectionClass connection = new ConnectionClass();
                    conn = connection.CONN();

                    if (conn.equals(null)) {
                        error_message = "Check your internet access.";
                    } else {
                        String query = "SELECT * FROM login WHERE user_name='" + username + "' and password='" + password + "'";
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {
                            error_message = "Login Succeed";
                            login_succeed = true;
                            conn.close();
                        } else {
                            login_succeed = false;
                            error_message = "Login failed, Check username or password";
                        }
                    }
                } catch (Exception ex) {
                    login_succeed = false;
                    error_message = ex.getMessage();
                }
            }

            return error_message;
        }
    }
}
