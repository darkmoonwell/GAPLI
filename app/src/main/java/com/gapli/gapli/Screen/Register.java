package com.gapli.gapli.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gapli.gapli.Model.User;
import com.gapli.gapli.R;
import com.gapli.gapli.Screen.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private EditText userEmail,UserPassword,UserName;
    private TextView createText;
    private Button RegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.UserEmail);
        createText = findViewById(R.id.createText);
        UserPassword = findViewById(R.id.UserPassword);
        UserName = findViewById(R.id.UserName);
        RegisterBtn = findViewById(R.id.RegisterBtn);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = userEmail.getText().toString();
                String pass = UserPassword.getText().toString();
                String name = UserName.getText().toString();
                if(!mail.isEmpty() && !pass.isEmpty() &&!name.isEmpty()) {
                    userRegister(mail.trim(), pass.trim(), name.trim());
                }else{
                    Toast.makeText(Register.this, "Lütfen boş alanları doldurun.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        createText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }


    private void userRegister(String email,String password , String name){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            User user  = new User();
                            user.seteMail(email);
                            user.setId(fUser.getUid());
                            user.setName(name);
                            user.setPass(password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(user.getId()).setValue(user);

                            Intent intent = new Intent(Register.this, Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Toast.makeText(Register.this, "Kayıt Başarılı \nHoş Geldiniz.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, "Kayıt Hatası",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}