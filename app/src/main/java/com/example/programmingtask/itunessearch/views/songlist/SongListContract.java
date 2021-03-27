package com.example.programmingtask.itunessearch.views.songlist;

import com.example.programmingtask.itunessearch.api.model.Track;

import java.util.List;



class SongListContract {

    interface View {
        void displayMessage(String message);

        void setLoadingIndicator(boolean isLoading);

        void displayTracks(List<Track> dataTracks);
    }

    interface Presenter {
        void getTracks(String term);
    }
}