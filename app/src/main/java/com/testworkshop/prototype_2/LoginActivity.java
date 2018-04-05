package com.testworkshop.prototype_2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;

//import com.testworkshop.prototype_2.utilities.WatsonProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private static boolean isNetworkAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure Twitter SDK

        setContentView(R.layout.twitterlogin);


//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                isNetworkAvailable = isNetworkAvailable();
//                if (isNetworkAvailable == true){
//
//                    // [END initialize_twitter_login]
//
////                        Intent main = new Intent(LoginActivity.this,MainActivity.class);
////                        startActivity(main);
////                        finish();
//                    //                        isInternetAvailable = isInternetAvailable(this);
//                }else {
//                    Snackbar.make(view,"Network Unavailable, try again later",Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });


        afterLogin();
    }


    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


//    public  boolean isInternetAvailable(Context context) {
//        if (isNetworkAvailable == true) {
//            try {
//                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
//                urlc.setRequestProperty("User-Agent", "Test");
//                urlc.setRequestProperty("Connection", "close");
//                urlc.setConnectTimeout(1500);
//                urlc.connect();
//                return (urlc.getResponseCode() == 200);
//            } catch (IOException e) {
//                Log.e(TAG, "Error checking internet connection", e);
//            }
//        } else {
//            Log.d(TAG, "No network available!");
//        }
//        return false;
//    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.


    }


    public void afterLogin() {
        //Step 1: Retrieve tweets and perform persanility analysis
        //Step 2: Store result in table
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                PersonalityInsights service = new PersonalityInsights("2016-10-19");
                service.setUsernameAndPassword("c36e114f-ec6a-4d9b-86b1-a37f3489cf17", "uGsDvJ44Wtfo");

                // Demo content from Moby Dick by Hermann Melville (Chapter 1)
                String text = "Call me Ishmael. Some years ago-never mind how long precisely-having "
                        + "little or no money in my purse, and nothing particular to interest me on shore, "
                        + "I thought I would sail about a little and see the watery part of the world. "
                        + "It is a way I have of driving off the spleen and regulating the circulation. "
                        + "Whenever I find myself growing grim about the mouth; whenever it is a damp, "
                        + "drizzly November in my soul; whenever I find myself involuntarily pausing before "
                        + "coffin warehouses, and bringing up the rear of every funeral I meet; and especially "
                        + "whenever my hypos get such an upper hand of me, that it requires a strong moral "
                        + "principle to prevent me from deliberately stepping into the street, and methodically "
                        + "knocking people's hats off-then, I account it high time to get to sea as soon as I can. "
                        + "This is my substitute for pistol and ball. With a philosophical flourish Cato throws himself "
                        + "upon his sword; I quietly take to the ship. There is nothing surprising in this. "
                        + "If they but knew it, almost all men in their degree, some time or other, cherish "
                        + "very nearly the same feelings towards the ocean with me. There now is your insular "
                        + "city of the Manhattoes, belted round by wharves as Indian isles by coral reefs-commerce surrounds "
                        + "it with her surf. Right and left, the streets take you waterward.";

                ProfileOptions options = new ProfileOptions.Builder()
                        .text(text)
                        .build();

                ServiceCall call = service.profile(options);
                call.enqueue(new ServiceCallback<Profile>() {
                    @Override
                    public void onResponse(Profile profile) {
                        Log.d("URL", profile.toString());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("URL", e.toString());
                    }
                });

//                Profile profile = service.profile(options).execute();
//                Log.d("URL",profile.toString());

            }
        });
        t.start();
        updateDB();
    }


    public void updateDB() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Toast.makeText(this, "onClick called", Toast.LENGTH_LONG).show();
        switch (id) {

            case R.id.twitter_login:

                break;

            default:
                break;
        }


    }
}
