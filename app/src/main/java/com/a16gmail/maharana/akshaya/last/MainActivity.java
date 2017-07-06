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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etEmail = (EditText)findViewById(R.id.etEmailSignin);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);
        Button btnRegister = (Button)findViewById(R.id.btnRegister);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    progressDialog.setMessage("Registering...");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(user!=null){
                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(MainActivity.this,"Check your Email",Toast.LENGTH_LONG).show();
                                                mAuth.signOut();
                                                Intent i = new Intent(MainActivity.this,SignIn.class);
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });
                                    progressDialog.dismiss();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Already Registered",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Some fields are empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
