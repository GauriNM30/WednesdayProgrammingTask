package com.example.programmingtask.itunessearch.views.songlist;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import 	androidx.recyclerview.widget.DividerItemDecoration;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.programmingtask.R;
import com.example.programmingtask.itunessearch.api.model.Track;
import androidx.recyclerview.widget.DefaultItemAnimator;
import java.util.ArrayList;
import java.util.List;

public class SongListView extends AppCompatActivity implements SongListContract.View {

    Context context;
    LinearLayout main;
    TextView txtNoSongs;
    ShimmerRecyclerView listTracks;

    private List<Track> dataTracks = new ArrayList<>();
    private SongAdapter adapter;

    SongListPresenter presenter;

    public SongListView() {
        presenter = new SongListPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        context = SongListView.this;

        main = (LinearLayout) findViewById(R.id.song_list_main);
        txtNoSongs  = (TextView) findViewById(R.id.txtNoSongs);
        listTracks = (ShimmerRecyclerView) findViewById(R.id.listSongs);

        adapter = new SongAdapter(context, dataTracks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listTracks.setLayoutManager(mLayoutManager);
        listTracks.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        listTracks.setItemAnimator(new DefaultItemAnimator());
        listTracks.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search for Songs, Artists & More");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void search(final String strTerm) {
        txtNoSongs.setVisibility(View.GONE);
        listTracks.setVisibility(View.VISIBLE);

        dataTracks.clear();
        adapter.notifyDataSetChanged();

        setLoadingIndicator(true);
        listTracks.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getTracks(strTerm);
            }
        }, 2000);
    }

    @Override
    public void displayTracks(List<Track> dataTracks) {
        setLoadingIndicator(false);
        this.dataTracks.clear();
        this.dataTracks.addAll(dataTracks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayMessage(String message) {
        setLoadingIndicator(false);
        Snackbar.make(main, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        if (isLoading) {
            listTracks.showShimmerAdapter();
        } else {
            listTracks.hideShimmerAdapter();
        }
    }
}