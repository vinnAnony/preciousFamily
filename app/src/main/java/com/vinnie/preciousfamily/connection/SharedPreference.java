package com.vinnie.preciousfamily.connection;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by Vinnie on 7/4/2017.
 */
public class SharedPreference {
        SharedPreferences sp;
        SharedPreferences.Editor editor;

        public SharedPreference(Context c){
            sp=c.getSharedPreferences("User", 0);
            editor = sp.edit();
        }

        public void storeData(String id, String email, String name, String image_url){
            editor.putString("id", id);
            editor.putString("email", email);
            editor.putString("name",name);
            editor.putString("image_url",image_url);

            editor.commit();

        }

        public ArrayList<String> getData(){

            String id= sp.getString("id","");
            String email= sp.getString("email","");
            String name= sp.getString("name","");
            String image_url= sp.getString("image_url","");


            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(id);
            arrayList.add(email);
            arrayList.add(name);
            arrayList.add(image_url);


            return arrayList;
        }

        public void loggedIn(boolean status){
            editor.putBoolean("status", status);
            editor.commit();
        }

        public boolean getLoggedInStatus(){
            boolean status = sp.getBoolean("status", false);
            return status;
        }

        public void clearData(){
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();
        }

}
