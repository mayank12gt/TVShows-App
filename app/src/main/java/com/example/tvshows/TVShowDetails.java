package com.example.tvshows;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tvshows.adapters.ImageSliderAdapter;
import com.example.tvshows.databinding.ActivityTvshowDetailsBinding;
import com.example.tvshows.databinding.EpisodesBtmSheetBinding;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.viewmodel.TVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TVShowDetails extends AppCompatActivity {

    ActivityTvshowDetailsBinding activityTvshowDetailsBinding;
    TVShowDetailsViewModel tvShowDetailsViewModel;
    EpisodesBtmSheetBinding episodesBtmSheetBinding;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvshowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tvshow_details);
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);

        Intent i = getIntent();

        TvShow tvShow = (TvShow) i.getSerializableExtra("tvShow");


        activityTvshowDetailsBinding.setIsLoading(true);
        tvShowDetailsViewModel.getTVShowDetails(String.valueOf(tvShow.getId())).observe(this, TVShowDetailsResponse -> {
            activityTvshowDetailsBinding.setIsLoading(false);
            if (TVShowDetailsResponse != null) {
                if (TVShowDetailsResponse.getTvShowDetails() != null) {
                    loadSliderImages(TVShowDetailsResponse.getTvShowDetails().getPictures());
                    activityTvshowDetailsBinding.setImagePathUrl(TVShowDetailsResponse.getTvShowDetails().getImage_path());
                    activityTvshowDetailsBinding.setMovieName(TVShowDetailsResponse.getTvShowDetails().getName());
                    activityTvshowDetailsBinding.setNetwork(tvShow.getNetwork());
                    activityTvshowDetailsBinding.setTVShowStarted(tvShow.getStartdate());
                    activityTvshowDetailsBinding.setTVShowstatus(tvShow.getStatus());
                    activityTvshowDetailsBinding.setRating(String.format(Locale.getDefault(),
                            "%.1f", Double.parseDouble(TVShowDetailsResponse.getTvShowDetails().getRating())));

                    setupsliderIndicator(TVShowDetailsResponse.getTvShowDetails().getPictures().length);


                    activityTvshowDetailsBinding.setDescription(
                            String.valueOf(HtmlCompat.fromHtml(TVShowDetailsResponse.getTvShowDetails().getDescription(),
                                    HtmlCompat.FROM_HTML_MODE_LEGACY)));
                    activityTvshowDetailsBinding.ReadMore.setVisibility(View.VISIBLE);
                    activityTvshowDetailsBinding.ReadMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (activityTvshowDetailsBinding.ReadMore.getText().equals("Read More")) {
                                activityTvshowDetailsBinding.TVShowDescription.setMaxLines(Integer.MAX_VALUE);
                                activityTvshowDetailsBinding.ReadMore.setText("Read Less");
                            } else {
                                activityTvshowDetailsBinding.TVShowDescription.setMaxLines(6);
                                activityTvshowDetailsBinding.ReadMore.setText("Read More");


                            }
                        }
                    });


                    if (TVShowDetailsResponse.getTvShowDetails().getGenres().length != 0) {
                        activityTvshowDetailsBinding.setGenre(TVShowDetailsResponse.getTvShowDetails().getGenres()[0]);

                    } else {
                        activityTvshowDetailsBinding.setGenre("N/A");
                    }

                    activityTvshowDetailsBinding.setRuntime(TVShowDetailsResponse.getTvShowDetails().getRuntime() + " min");
                    activityTvshowDetailsBinding.divider1.setVisibility(View.VISIBLE);
                    activityTvshowDetailsBinding.infolayout.setVisibility(View.VISIBLE);
                    activityTvshowDetailsBinding.divider2.setVisibility(View.VISIBLE);


                    activityTvshowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
                    activityTvshowDetailsBinding.buttonEpisodes.setVisibility(View.VISIBLE);
                    activityTvshowDetailsBinding.buttonWebsite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(TVShowDetailsResponse.getTvShowDetails().getUrl()));
                            startActivity(intent);
                        }
                    });

                    activityTvshowDetailsBinding.buttonEpisodes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            EpisodesBtmSheet frag = new EpisodesBtmSheet(TVShowDetailsResponse.getTvShowDetails().getEpisodes(),
                                    TVShowDetailsResponse.getTvShowDetails().getName());
                            frag.show(getSupportFragmentManager(), frag.getTag());
                        }
                    });


                    activityTvshowDetailsBinding.addtowatchlist.setVisibility(View.VISIBLE);
                    activityTvshowDetailsBinding.addtowatchlist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    tvShowDetailsViewModel.addToWatchList(tvShow);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(TVShowDetails.this, "TVShow added to your watchlist"
                                                    , Toast.LENGTH_SHORT).show();

                                            activityTvshowDetailsBinding.addtowatchlist.setImageResource(R.drawable.ic_baseline_check_24);
                                        }
                                    });
                                }
                            });


                        }
                    });
                }
            }
        });

        activityTvshowDetailsBinding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void loadSliderImages(String[] pictures) {
        activityTvshowDetailsBinding.imageslider.setOffscreenPageLimit(1);
        activityTvshowDetailsBinding.imageslider.setAdapter(new ImageSliderAdapter(pictures));
        activityTvshowDetailsBinding.imageslider.setVisibility(View.VISIBLE);
        activityTvshowDetailsBinding.imageslider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });


    }

    public void setupsliderIndicator(int count) {
        ImageView[] indicators = new ImageView[count];

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < count; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.image_sliderinactive));
            indicators[i].setLayoutParams(layoutParams);
            activityTvshowDetailsBinding.imagesliderindicator.addView(indicators[i]);

        }
        setCurrentIndicator(0);
    }

    public void setCurrentIndicator(int position) {

        int childcount = activityTvshowDetailsBinding.imagesliderindicator.getChildCount();

        for (int i = 0; i < childcount; i++) {
            ImageView imageView = (ImageView) activityTvshowDetailsBinding.imagesliderindicator.getChildAt(i);
            if (i == position) {

                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.imageslider_active));
            } else {

                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.image_sliderinactive));
            }
        }
    }
}