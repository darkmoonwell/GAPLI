package com.gapli.gapli.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.gapli.gapli.Adapter.CityAdapter;
import com.gapli.gapli.Model.CityDetailModel;
import com.gapli.gapli.Model.CityModel;
import com.gapli.gapli.Model.PlacesToVsit;
import com.gapli.gapli.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    // ürünlerimizi yerleştirecegimiz listemiz
    private RecyclerView cityList;
    //Şehirleri listemize yerleştirmek için bize gereken adaptor sınıfımız
    private CityAdapter city_adapter;
    // veritabanından çektigimiz verileri tutacagımız listemiz
    private final ArrayList<CityModel> citys = new ArrayList<>();
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       /* CityDetailModel detailModel = new CityDetailModel();
        detailModel.setId("xxxx0");
        detailModel.setName("Adıyaman");
        detailModel.setTitle("Tütün diyarı");
        detailModel.setDescription("Yerleşimin eski adı Hısnımansûr (Mansur'un kalesi) olup, Cumhuriyet döneminde bugünkü adını almıştır. Adının 7. yüzyılda buraya gelen Emevî kumandanlarından Mansûr bin Ca'vene’den geldiği düşünülmektedir. Başka bir rivayete göre bu isim Abbâsî Halifesi Mansur’un adından gelmektedir.");
        List<String> images = new ArrayList<>();
        images.add("https://i2.milimaj.com/i/milliyet/75/0x0/5f63f0b355428516c49fe80f.jpg");
        images.add("https://imgrosetta.mynet.com.tr/file/13011320/13011320-640x380.jpg");
        images.add("https://img.piri.net/mnresize/900/-/resim/imagecrop/2020/04/08/11/30/resized_6c21e-5506313cadiyaman.jpg");
        detailModel.setImages(images);
        List<PlacesToVsit> gez = new ArrayList<>();
        gez.add(new PlacesToVsit("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/08/88/7a/a5/mount-nemrut.jpg?w=2000&h=-1&s=1",
                "Nemrut Dagları",
                "Türkiye’de mutlaka görülmesi gereken yerlerden biri. Adıyaman’dan özel minibüsler ile 2 saatte ulaşım sağlanıyor. Özel araçla çıkılabilir. "
                ));
        detailModel.setPlacesToVsits(gez);
        FirebaseDatabase.getInstance().getReference("CityDetail").child(detailModel.getId()).setValue(detailModel);*/
        init();
        loadcity();
    }

    private void init() {
        // arayüzdeki kompanente ulaşmak için id sini kullanıyoruz
        cityList = findViewById(R.id.cityList);

        cityList.setHasFixedSize(true);
        // grit viev görümünü verebilmek için grid Layout manager ı Recycler View imize ekliyoruz
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        cityList.setLayoutManager(llm);


        // Firebase database e baglanmak için gerekli alandan referansımızı alıyoruz
        reference = FirebaseDatabase.getInstance().getReference("City");
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
                city_adapter = new CityAdapter(getApplicationContext(), citys, home.this);
                // ardından bu nesneyi Recycler viev e veriyoruzz
                cityList.setAdapter(city_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}