package com.projects.android.MyNotes.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.projects.android.MyNotes.R;

public class SignInActivity extends AppCompatActivity {
    EditText l_username,l_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        l_username=(EditText)findViewById(R.id.input_username);
        l_password=(EditText)findViewById(R.id.input_password);
        TextView signUp=(TextView)findViewById(R.id.link_signup);
        TextView forgotPassword=(TextView)findViewById(R.id.link_password);
        Button login=(Button)findViewById(R.id.login);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Intent i2=new Intent(SignInActivity.this,MainActivity.class);
                    startActivity(i2);
                }
            }
        });
    }

    public boolean validate(){
        boolean valid = true;
        String Username=l_username.getText().toString();
        String Password=l_password.getText().toString();


        return valid;

    }
}
