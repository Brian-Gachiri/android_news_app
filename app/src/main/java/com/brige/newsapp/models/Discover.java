package com.brige.newsapp.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Discover {

    @Id
    long id; //This long is a requirement for all ObjectBox models. Serves as the primary(unique) key.

    String image;
    String video_url;
    boolean is_external_image;
    String created_at;

    public Discover() {
    }

    public Discover(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
