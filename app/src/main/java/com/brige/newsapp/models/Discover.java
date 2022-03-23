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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public boolean isIs_external_image() {
        return is_external_image;
    }

    public void setIs_external_image(boolean is_external_image) {
        this.is_external_image = is_external_image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
