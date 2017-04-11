package com.kabadiwala.fujitsu.thekabadiwala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

public class DashBoard extends AppCompatActivity implements View.OnClickListener {

    Button mlgoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_dash_board);

        mlgoutbtn = (Button) findViewById(R.id.lgout_btn);
        mlgoutbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        disconnectFromFacebook();
    }

//    private void logoutFromFacebook(){
//
//        final SharedPreferences pref = DashBoard.this.getPreferences(Context.MODE_PRIVATE);
//        String id = pref.getString("facebook_id", "empty");
//
//        try {
//            if (AccessToken.getCurrentAccessToken() == null) {
//                return; // already logged out
//            }
//             //get fb id from sharedprefrences
//            GraphRequest graphRequest=new GraphRequest(AccessToken.getCurrentAccessToken(), "/ "+id+"/permissions/", null,
//                    HttpMethod.DELETE, new GraphRequest.Callback() {
//                @Override
//                public void onCompleted(GraphResponse graphResponse) {
//                    LoginManager.getInstance().logOut();
//
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.remove("facebook_id");
//                    editor.commit();
//                    finish();
//
//                    Intent logoutint = new Intent(DashBoard.this,MainActivity.class);
//                    startActivity(logoutint);
//                }
//            });
//
//            graphRequest.executeAsync();
//        }catch(Exception ex) {
//            ex.printStackTrace();
//        }
//
//
//    }


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
