package com.kabadiwala.fujitsu.thekabadiwala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Fujitsu on 11/04/2017.
 */

public class MainActivity extends AppCompatActivity {

    Button fbloginbtn;

    CallbackManager callbackManager;

    ProgressDialog progress;
    private String facebook_id,f_name, m_name, l_name, gender, profile_image, full_name, email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        SharedPreferences pref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);

//                            GraphRequestAsyncTask request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),new GraphRequest.GraphJSONObjectCallback(){
//
//                                @Override
//                                public void onCompleted(JSONObject object, GraphResponse response) {
//
//
//                                    Toast.makeText(getApplicationContext(),object.toString(),Toast.LENGTH_LONG).show();
//
//                                }
//                            }).executeAsync();

                        graphRequest(loginResult.getAccessToken());



                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        setContentView(R.layout.activity_main);

        fbloginbtn = (Button)findViewById(R.id.fblogin_btn);

        progress=new ProgressDialog(MainActivity.this);
        progress.setMessage(getResources().getString(R.string.please_wait_facebooklogin));
        progress.setIndeterminate(false);
        progress.setCancelable(false);

        fbloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email","public_profile", "user_friends"));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void graphRequest(AccessToken token){



        GraphRequest request = GraphRequest.newMeRequest(token,new GraphRequest.GraphJSONObjectCallback(){

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject me = response.getJSONObject();

                if (me != null) {

                    try {

                        Profile profile = Profile.getCurrentProfile();


                        Bundle mBundle = new Bundle();
                               mBundle.putParcelable("pic", profile);


                        String email = object.getString("email");
                        String id = object.optString("id");
                        String name = object.optString("name");


                        Intent loginint = new Intent(MainActivity.this,DashBoard.class);
                       loginint.putExtra("email",email);
                        loginint.putExtras(mBundle);



                        startActivity(loginint);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Toast.makeText(getApplicationContext(),object.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Bundle b = new Bundle();
        b.putString("fields","id,email,first_name,last_name,picture.type(large)");
        request.setParameters(b);
        request.executeAsync();

    }

}

