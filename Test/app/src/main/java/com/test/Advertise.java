package com.test;

/**
 * Created by toshiba on 2/12/2016.
 */
public class Advertise {

    private String title;
    private String id;
    private String uri;

    public Advertise(String id, String title, String uri) {
        this.id = id;
        this.title = title;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
