package com.projects.android.MyNotes.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.projects.android.MyNotes.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {
    EditText r_name,r_email,r_password,r_reEnterPassword;
    String Name,Email,Password,ReEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button signUp=(Button)findViewById(R.id.signup);
        r_name=(EditText)findViewById(R.id.register_name);
        r_password=(EditText)findViewById(R.id.register_password);
        r_reEnterPassword=(EditText)findViewById(R.id.register_reEnterPassword);
        r_email=(EditText)findViewById(R.id.register_email);
        CircleImageView image=(CircleImageView)findViewById(R.id.register_image);
        TextView login=(TextView)findViewById(R.id.link_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Intent i2=new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(i2);

                }
            }
        });
    }

    public boolean validate(){
        boolean valid=true;
        Name = r_name.getText().toString();
        Email = r_email.getText().toString();
        Password = r_password.getText().toString();
        ReEnterPassword = r_reEnterPassword.getText().toString();

        if (Name.isEmpty() || Name.length() < 3) {
            r_name.setError("At least 3 characters");
            valid = false;
        } else {
            r_name.setError(null);
        }
        if (!isValidEmail(Email)) {
            r_email.setError("Invalid Email");
            valid = false;
        }
        if (Password.isEmpty() || Password.length() < 4 || Password.length() > 15) {
            r_password.setError("Between 4 to 15 alphanumeric characters");
            valid = false;
        } else {
            r_password.setError(null);
        }
        if (ReEnterPassword.isEmpty() || ReEnterPassword.length() < 4 || ReEnterPassword.length() > 10 || !(ReEnterPassword.equals(Password))) {
            r_reEnterPassword.setError("Password Do not match");
            valid = false;
        } else {
            r_reEnterPassword.setError(null);
        }
        return valid;
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
