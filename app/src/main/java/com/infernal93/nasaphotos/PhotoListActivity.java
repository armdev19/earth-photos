package com.infernal93.nasaphotos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.infernal93.nasaphotos.api.model.PhotoDTO;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class PhotoListActivity extends AppCompatActivity {

    private static final String EXTRA_DATE = "PhotoListActivity.EXTRA_DATE";

    CompositeDisposable disposable = new CompositeDisposable();

    RecyclerView recyclerView;
    Adapter adapter;
    TextView timeText;
    TextView policyPrivacy;

    public static void start(Context caller, String date) {
        Intent intent = new Intent(caller, PhotoListActivity.class);
        intent.putExtra(EXTRA_DATE, date);
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        adapter = new Adapter();

        timeText = findViewById(R.id.top_title);
        timeText.setText(getString(R.string.intro_title_two));
        policyPrivacy = findViewById(R.id.policy_privacy_text);

        policyPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendToPrivacyPolicy();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        App app = (App) getApplication();

        disposable.add(app.getNasaService().getApi().getPhotosForDate(getIntent().getStringExtra(EXTRA_DATE))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BiConsumer<List<PhotoDTO>, Throwable>() {
                    @Override
                    public void accept(List<PhotoDTO> photos, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            Toast.makeText(PhotoListActivity.this, getString(R.string.data_loading_error), Toast.LENGTH_LONG).show();
                        } else {
                            adapter.setPhotos(photos);
                        }
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }

    private static class Adapter extends RecyclerView.Adapter<PhotoItemViewHolder> {

        private ArrayList<PhotoDTO> photos = new ArrayList<>();

        public void setPhotos(List<PhotoDTO> photos) {
            this.photos.clear();
            this.photos.addAll(photos);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new PhotoItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_photo, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoItemViewHolder photoItemViewHolder, int i) {
            photoItemViewHolder.bind(photos.get(i));
        }

        @Override
        public int getItemCount() {
            return photos.size();
        }
    }

    private static class PhotoItemViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        PhotoDTO photo;

        public PhotoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoActivity.start(view.getContext(), photo.getImageUrl());
                }
            });
        }

        public void bind(PhotoDTO photo) {
            text.setText(photo.getDate());
            this.photo = photo;
        }
    }

    private void sendToPrivacyPolicy() {

        Intent privacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://armdev19.github.io/Privacy%20policy.html"));
        startActivity(privacyIntent);
    }
}
