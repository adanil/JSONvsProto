package org.example;

import java.util.Arrays;

class AlbumModel {
    String title;
    String[] artist;
    int release_year;
    String[] song_title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getArtist() {
        return artist;
    }

    public void setArtist(String[] artist) {
        this.artist = artist;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String[] getSong_title() {
        return song_title;
    }

    public void setSong_title(String[] song_title) {
        this.song_title = song_title;
    }

    @Override
    public String toString() {
        return "AlbumModel{" +
                "title='" + title + '\'' +
                ", artist=" + Arrays.toString(artist) +
                ", release_year=" + release_year +
                ", song_title=" + Arrays.toString(song_title) +
                '}';
    }
}