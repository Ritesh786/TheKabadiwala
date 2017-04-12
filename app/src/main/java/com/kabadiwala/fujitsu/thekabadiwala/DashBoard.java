package com.kabadiwala.fujitsu.thekabadiwala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

public class DashBoard extends AppCompatActivity implements View.OnClickListener {

    Button mlgoutbtn;

    TextView memailtext;
    ImageView mfbimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_dash_board);

        mlgoutbtn = (Button) findViewById(R.id.lgout_btn);
        mlgoutbtn.setOnClickListener(this);

        memailtext = (TextView) findViewById(R.id.email_text);
        mfbimage = (ImageView) findViewById(R.id.fb_image);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");



        memailtext.setText(email);

        Bundle mBundle =intent.getExtras();
 //         Profile profile = mBundle.getParcelable("pic");

        Profile  profile;

        if (mBundle != null) {
           profile = (Profile) mBundle.getParcelable("pic");
        } else {
            profile = Profile.getCurrentProfile();
        }


        Picasso.with(DashBoard.this)
                .load(profile.getProfilePictureUri(400, 400).toString())
                .into(mfbimage);



    }

    @Override
    public void onClick(View v) {

        disconnectFromFacebook();

    }


    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                SharedPreferences pref = DashBoard.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                LoginManager.getInstance().logOut();

                Intent logoutint = new Intent(DashBoard.this,MainActivity.class);
                   startActivity(logoutint);

            }
        }).executeAsync();


    }

}
