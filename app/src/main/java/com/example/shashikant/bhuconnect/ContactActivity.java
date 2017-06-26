package com.example.shashikant.bhuconnect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ImageView telephoneImageView = (ImageView) findViewById(R.id.telephone_directory);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.telephone);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        telephoneImageView.setImageDrawable(roundedBitmapDrawable);

        ImageView emailImageView = (ImageView) findViewById(R.id.email_directory);
        Bitmap bitmapEmail = BitmapFactory.decodeResource(getResources(), R.drawable.email);
        RoundedBitmapDrawable roundedBitmapDrawableEmail = RoundedBitmapDrawableFactory.create(getResources(),bitmapEmail);
        roundedBitmapDrawableEmail.setCircular(true);
        emailImageView.setImageDrawable(roundedBitmapDrawableEmail);

        ImageView websiteImageView = (ImageView) findViewById(R.id.website_link);
        Bitmap bitmapWebsite = BitmapFactory.decodeResource(getResources(), R.drawable.website_link);
        RoundedBitmapDrawable roundedBitmapDrawableWebsite = RoundedBitmapDrawableFactory.create(getResources(),bitmapWebsite);
        roundedBitmapDrawableWebsite.setCircular(true);
        websiteImageView.setImageDrawable(roundedBitmapDrawableWebsite);





    }
}
