package com.brige.newsapp;

import com.brige.newsapp.models.MyObjectBox;

import io.objectbox.BoxStore;


/**
 * Created by: Brian Gachiri on March 22, 2022
 * This class is used to Initialize the local Box storage for this App
 * It contains two methods, one called when the Application launches to 'init' the Box
 * The other method used across the App to access and use the Box (In activities, Fragments etc).
 * Setup and Usage examples can be found on the link: https://github.com/objectbox/objectbox-java
 */
public class ObjectBox {
    
    private static BoxStore boxStore;

    static void init(App context){
        boxStore = MyObjectBox.builder()
               .androidContext(context.getApplicationContext())
               .build();
    }

    public static BoxStore get(){
        return boxStore;
    }
}
