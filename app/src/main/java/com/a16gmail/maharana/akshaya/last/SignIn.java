package com.a16gmail.maharana.akshaya.last;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final EditText etEmail = (EditText)findViewById(R.id.etEmailSignin);
        final EditText etPassword = (EditText)findViewById(R.id.etPasswordSignIn);
        Button btnSignin = (Button)findViewById(R.id.btnSignin);
        Button btnSignup = (Button)findViewById(R.id.btnSignup);

        final ProgressDialog progressDialog = new ProgressDialog(this);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    progressDialog.setMessage("Signing in...");
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent i = new Intent(SignIn.this,BookNow.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(SignIn.this,"This Email is not registered",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(SignIn.this,"Some fields are empty",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }
}
