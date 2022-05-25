package com.gapli.gapli.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gapli.gapli.Adapter.CityAdapter;
import com.gapli.gapli.Model.CityModel;
import com.gapli.gapli.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    // ürünlerimizi yerleştirecegimiz listemiz
    private RecyclerView cityList;
    //Şehirleri listemize yerleştirmek için bize gereken adaptor sınıfımız
    private CityAdapter city_adapter;
    // veritabanından çektigimiz verileri tutacagımız listemiz
    private final ArrayList<CityModel> citys = new ArrayList<>();
    private DatabaseReference reference;

    private ImageView profile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
      
        init();
        loadcity();

    }

    private void init() {
        // arayüzdeki kompanente ulaşmak için id sini kullanıyoruz
        cityList = findViewById(R.id.cityList);
        profile = findViewById(R.id.profile);

        cityList.setHasFixedSize(true);
        // grit viev görümünü verebilmek için grid Layout manager ı Recycler View imize ekliyoruz
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        cityList.setLayoutManager(llm);


        // Firebase database e baglanmak için gerekli alandan referansımızı alıyoruz
        reference = FirebaseDatabase.getInstance().getReference("City");

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: profil ekranına geçiş bu kısımdan yapılacak
                Intent intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
            }
        });
    }




    private void loadcity() {
        // bu aldıgımız referansa bir sürekli dinleyici atıyoruz
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // listenerr her çalıştıgında listenin üstüne eklemesin diye her seferinde clear ediyoruz
                citys.clear();
                // veritabanından gelen katagorileri tek tek almak için for llop a sokuyoruz
                for (DataSnapshot snap : snapshot.getChildren()) {
                    // gelen veriyi cityModel tipine çevirip alıyoruz
                    CityModel city = snap.getValue(CityModel.class);
                    Log.d("gelen",snap.getValue().toString());
                    // ve liste mize ekliyoruz
                    citys.add(city);
                }
                // Recycler viev e veri göndermemiz için yarattıgımız adaptorumuzden bir nesne türetiyoruz
                city_adapter = new CityAdapter(getApplicationContext(), citys, Home.this);
                // ardından bu nesneyi Recycler viev e veriyoruzz
                cityList.setAdapter(city_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}