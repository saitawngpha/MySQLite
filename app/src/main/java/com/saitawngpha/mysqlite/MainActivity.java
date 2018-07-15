package com.saitawngpha.mysqlite;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Account> lstUser = new ArrayList<Account>();
    DbHelper dbHelper;
    Button btn;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.buGetData);
        container = (LinearLayout)findViewById(R.id.container);

        dbHelper = new DbHelper(getApplicationContext());
        dbHelper.createDatabase();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Click
                lstUser = dbHelper.getAllUsers();
                for (Account account:lstUser){
                    LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView = inflater.inflate(R.layout.row, null);
                    TextView txtUser = (TextView)addView.findViewById(R.id.txtUserName);
                    TextView txtEmail = (TextView)addView.findViewById(R.id.txtEmail);
                    txtUser.setText(account.getUserName());
                    txtEmail.setText(account.getEmail());
                    container.addView(addView);
                }
            }
        });
    }
}
