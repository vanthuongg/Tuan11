package com.example.tuan11;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tuan11.adapter.VideosFireBaseAdapter;
import com.example.tuan11.model.Video1Model;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideosFireBaseAdapter videosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveVideoUrlToFirebase();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager2 = findViewById(R.id.viewPager);
        getVideos();
    }

    private void getVideos() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("videos");

        FirebaseRecyclerOptions<Video1Model> options = new FirebaseRecyclerOptions.Builder<Video1Model>()
                .setQuery(mDatabase, Video1Model.class)
                .build();

        videosAdapter = new VideosFireBaseAdapter(options, this);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(videosAdapter);
    }

    private void saveVideoUrlToFirebase() {
        String videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("videos");
        String newVideoKey = databaseRef.push().getKey();

        Map<String, Object> videoData = new HashMap<>();
        videoData.put("title", "Video Test OK");
        videoData.put("desc", "Video từ URL hợp lệ");
        videoData.put("url", videoUrl);

        databaseRef.child(newVideoKey).setValue(videoData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity.this, "Đã lưu video thành công!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        videosAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videosAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videosAdapter.notifyDataSetChanged();
    }
}
