package com.example.sqlite.model;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Contact {
    private Long id;
    private String name;
    private String phone;
    private String image;

    public Contact() {
    }

    public Contact(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Contact(Long id, String name, String phone, String image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void call(Context context){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + this.getPhone()));
        context.startActivity(intent);
    }

    public void sms(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("sms:" + this.getPhone()));
        context.startActivity(intent);
    }
}
