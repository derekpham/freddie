package com.derek.freddie.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public final class Song {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String artist;
    private String genre;
    private int length; // in seconds
    private int year;
    private String url;

    public Song() {}

    public Song(Long id, String name, String artist, String genre, int length, int yearPublished,
                String url) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.length = length;
        this.year = yearPublished;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", length=" + length +
                ", year=" + year +
                ", url='" + url + '\'' +
                '}';
    }
}
