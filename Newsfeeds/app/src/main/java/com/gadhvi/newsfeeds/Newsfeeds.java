package com.gadhvi.newsfeeds;

/**
 * Created by gadhvi on 12/6/17.
 */

public class Newsfeeds {
    private String Description,Image,Title;
    public Newsfeeds(){

    }
    public Newsfeeds(String description, String image, String title) {
        Description = description;
        Image = image;
        Title = title;
    }




    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


}
