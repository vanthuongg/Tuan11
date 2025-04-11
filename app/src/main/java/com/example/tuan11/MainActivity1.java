package com.example.tuan11;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tuan11.adapter.VideosAdapter;
import com.example.tuan11.config.ApiService;
import com.example.tuan11.model.MessageVideoModel;
import com.example.tuan11.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity1 extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private VideosAdapter adapter;
    private List<VideoModel> videoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        getVideosFromApi();
    }

    private void getVideosFromApi() {
        ApiService.serviceApi.getVideos().enqueue(new Callback<MessageVideoModel>() {
            @Override
            public void onResponse(Call<MessageVideoModel> call, Response<MessageVideoModel> response) {
                if (response.body().isSuccess() && response.body() != null) {
                    videoList = response.body().getResult();
                    adapter = new VideosAdapter(MainActivity1.this, videoList);
                    viewPager2.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MessageVideoModel> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi: " + t.getMessage());
                Toast.makeText(MainActivity1.this, "Không thể tải video", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
