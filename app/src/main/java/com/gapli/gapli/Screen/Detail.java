package com.gapli.gapli.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gapli.gapli.Adapter.CityAdapter;
import com.gapli.gapli.Adapter.CommentAdaptor;
import com.gapli.gapli.Adapter.SliderAdapter;
import com.gapli.gapli.Model.CityDetailModel;
import com.gapli.gapli.Model.CityModel;
import com.gapli.gapli.Model.Comment;
import com.gapli.gapli.Model.SliderItem;
import com.gapli.gapli.Model.User;
import com.gapli.gapli.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Detail extends AppCompatActivity {
    // yorumları yerleştirecegimiz listemiz
    private RecyclerView commentList;
    // yorumları yerleştirmekte kullanacagımız adaptorumuz
    private CommentAdaptor comment_adapter;
    // görselleri göstermek için slider kütüphanemizi ekledik
    private SliderView sliderView;
    private DatabaseReference reference;
    // il fotograflarını ekrana yansıtmak için kullandıgımız adaptörumuz
    private SliderAdapter imageAdapter;

    // detayları nesnemiz ile yönetmek için nesnemizi tanımlıyoruz
    private  CityDetailModel detailModel;

    // şehir açıklaması ve ismini girecegimiz textleri tanımlıyoruz
    private TextView cityName,cityDes;

    // gezilecek yerleri  için slider kütüphanemizi ekledik
    private SliderView placeToVsitSlider;
    // gezilecek yerleri göstermek için bir adapter a ihtiyacımız daha var
    private SliderAdapter placesToVsitsAdaptor;

    // en üstteki geri iconunu tanımlıyoruz
    private ImageView back;

    // kullanıcının yorum yazacagı edittext i tanımlıyoruz
    private EditText commentText;

    // yorum yap butonumuzu tanımlıyoruz
    private Button commentButton;


    private TextView vsitdes;
    private FirebaseAuth auth;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    private void getUser(){
        FirebaseDatabase.getInstance().getReference("Users")
                .child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void init(){
        sliderView = findViewById(R.id.imageSlider);
        cityName = findViewById(R.id.cityName);
        cityDes = findViewById(R.id.cityDes);
        placeToVsitSlider = findViewById(R.id.placesToVsit);
        commentText = findViewById(R.id.commentText);
        commentButton = findViewById(R.id.commentButton);
        back = findViewById(R.id.back);
        vsitdes =findViewById(R.id.vsitdes);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            getUser();
        }


        // yorumu gösterecegimiz liste
        commentList = findViewById(R.id.commentList);

        commentList.setHasFixedSize(true);
        // grit viev görümünü verebilmek için grid Layout manager ı Recycler View imize ekliyoruz
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        commentList.setLayoutManager(llm);


        // resimleri gösterecegimiz slider a ayarlar yapıyoruz
        imageAdapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(imageAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        // üstteki görsellerin geçiş animasyonu
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(5); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        // gezilecek yerleri gösterecegimiz slider a ayarlar yapıyoruz
        placesToVsitsAdaptor = new SliderAdapter(this);
        placeToVsitSlider.setSliderAdapter(placesToVsitsAdaptor);
        placeToVsitSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        placeToVsitSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        placeToVsitSlider.setIndicatorSelectedColor(Color.WHITE);
        placeToVsitSlider.setIndicatorUnselectedColor(Color.GRAY);

        // ana sayfadan tıklanan şehrin id sini alıyoruz
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        // Firebase database e baglanmak için gerekli alandan referansımızı alıyoruz
        reference = FirebaseDatabase.getInstance().getReference("CityDetail").child(id);

        loadCity();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String com = commentText.getText().toString();
                if(!com.isEmpty() && user!=null){
                    Comment c = new Comment();
                    c.setComment(com);
                    c.setCityId(id);
                    c.setId(reference.child("comments").push().getKey().toString());
                    if(auth.getCurrentUser()!=null) {
                        c.setUserId(auth.getUid());
                        c.setUserName(user.getName());
                    }
                    reference.child("comments").child(c.getId()).setValue(c);
                    commentText.setText("");
                }
            }
        });

        placeToVsitSlider.setCurrentPageListener(new SliderView.OnSliderPageListener() {
            @Override
            public void onSliderPageChanged(int position) {
                vsitdes.setText(detailModel.getPlacesToVsits().get(position).getDescription());
            }
        });

    }

    private void loadCity(){
        // bu aldıgımız referansa bir sürekli dinleyici atıyoruz
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // gelen veriyi cityDetailModel tipine çevirip alıyoruz
                    detailModel = snapshot.getValue(CityDetailModel.class);
                    Log.d("gelen",snapshot.getValue().toString());

                    // gelen verilerimizi methodumuz sayesinde ekrandaki kompanentlere basıyoruz
                    loadData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //veritabanından aldıgımız detay nesnesini ekrandaki yerlerine basıyoruz
    private void loadData(){
        cityName.setText(detailModel.getName());
        imageAdapter.renewImageItems(detailModel.getImages());
        placesToVsitsAdaptor.renewPlaceToVsityItems(detailModel.getPlacesToVsits());
        cityDes.setText(detailModel.getDescription());

        HashMap<String,Comment> comments = new HashMap<>();
        if(detailModel.getComments()!=null) {
            comments =  detailModel.getComments();
        }
        comment_adapter = new CommentAdaptor(getApplicationContext(),comments, Detail.this,new ArrayList<String>(comments.keySet()));
        // ardından bu nesneyi Recycler viev e veriyoruzz
        commentList.setAdapter(comment_adapter);

    }
}