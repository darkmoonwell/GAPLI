package com.gapli.gapli.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gapli.gapli.Model.CityModel;
import com.gapli.gapli.Model.User;
import com.gapli.gapli.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView Name,Email;
    private ImageView edit;
    private Button LogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        LogOut = findViewById(R.id.LogOut);
        edit = findViewById(R.id.edit);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference("Users")
                .child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Name.setText(user.getName());
                Email.setText(user.geteMail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(Profile.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setText(Name.getText().toString());
                AlertDialog alertDialog = new AlertDialog.Builder(Profile.this).create();
                alertDialog.setTitle("Kullanıcı Adı");
                alertDialog.setView(input);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Güncelle",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String name = input.getText().toString();
                                if(!name.isEmpty()){
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(currentUser.getUid()).child("name").setValue(name);
                                            dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Kullanıcı adınız güncellendi.",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Lütfen isminizi girin.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "İptal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(Profile.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}