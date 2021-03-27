package com.example.programmingtask.itunessearch.views.songdetail;

import com.example.programmingtask.itunessearch.api.model.Track;


public class SongDetailContract {

    interface View {
        void displayMessage(String message);

        void displayTrack(Track track);
    }
}
