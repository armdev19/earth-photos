package com.infernal93.nasaphotos;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.infernal93.nasaphotos.api.model.DateDTO;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    CompositeDisposable disposable = new CompositeDisposable();

    RecyclerView recyclerView;
    Adapter adapter;
    TextView policyPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        policyPrivacy = findViewById(R.id.policy_privacy_text);

        adapter = new Adapter();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        policyPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            sendToPrivacyPolicy();
            }
        });

        App app = (App) getApplication();

        disposable.add(app.getNasaService().getApi().getDatesWithPhoto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<DateDTO>, Throwable>() {
                    @Override
                    public void accept(List<DateDTO> dates, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.data_loading_error), Toast.LENGTH_LONG).show();
                        } else {
                            adapter.setDates(dates);
                        }
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        ArrayList<DateDTO> dates = new ArrayList<>();

        public void setDates(List<DateDTO> dates) {
            this.dates.clear();
            this.dates.addAll(dates);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_date, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.bind(dates.get(i));
        }

        @Override
        public int getItemCount() {
            return dates.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        DateDTO dateDTO;

        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoListActivity.start(view.getContext(), dateDTO.getDate());
                }
            });
        }

        public void bind(DateDTO date) {
            dateDTO = date;
            text.setText(date.getDate());
        }
    }

    private void sendToPrivacyPolicy() {

        Intent privacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://armdev19.github.io/Privacy%20policy.html"));
        startActivity(privacyIntent);
    }
}
