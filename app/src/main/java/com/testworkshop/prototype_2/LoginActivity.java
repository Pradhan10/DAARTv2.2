package com.testworkshop.prototype_2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait;

import java.util.ArrayList;
import java.util.List;

//import com.testworkshop.prototype_2.utilities.WatsonProvider;

public class LoginActivity extends AppCompatActivity {


    private static String mWatsonError = "Error";
    private static boolean isNetworkAvailable = false;
    private static boolean responseSuccess = false;
    private ProgressBar mProgress;
    private EditText mAnalyseText;
    private static List<Trait> mPersonality = new ArrayList<>();
    /*Data Structure that will containt imp characteristics*/
    private static ArrayList<String> mPersonalityTraits = new ArrayList<>();
    private static double[] mPersonalityScore = new double[5];
    private Button mAnalyseButton, mshowResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitterlogin);
        mProgress = findViewById(R.id.progress_bar);
        mAnalyseText = findViewById(R.id.et_input);
        mshowResult = findViewById(R.id.button_showAnalysis);

        mAnalyseButton = findViewById(R.id.button_analyse);
        View mParent = findViewById(R.id.parent_id);

        isNetworkAvailable = isNetworkAvailable();
        if (isNetworkAvailable == true) {
            mAnalyseButton.setEnabled(true);
            mAnalyseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProgress.setVisibility(View.VISIBLE);
                    String input = mAnalyseText.getText().toString();
                    if (input.length() >= 200) {
                        WatsonRunnable mRunnable = new WatsonRunnable();
                        mRunnable.run();
                    }
                }
            });
            mshowResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPersonality.isEmpty()) {

                        for (int i = 0; i < mPersonality.size(); i++) {
                            String traitName = mPersonality.get(i).getName();
                            Double traitPercentile = mPersonality.get(i).getPercentile();
                            mPersonalityTraits.add(i, traitName);
                            mPersonalityScore[i] = traitPercentile;
                            Log.d("URL mMajorPer", traitPercentile.toString());
                        }
                        Intent intent = new Intent(LoginActivity.this, WatsonResponse.class);
                        intent.putExtra(Intent.EXTRA_TEXT, mPersonalityTraits);
                        intent.putExtra("scores", mPersonalityScore);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Please perform analysis first", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Snackbar.make(mParent, "Network Unavailable, try again later", Snackbar.LENGTH_LONG).show();
        }


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    @Override
    public void onStart() {
        super.onStart();

    }


    class WatsonRunnable implements Runnable {
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            PersonalityInsights service = new PersonalityInsights("2016-10-19");
            service.setUsernameAndPassword("c36e114f-ec6a-4d9b-86b1-a37f3489cf17", "uGsDvJ44Wtfo");

            String input = mAnalyseText.getText().toString();
            ProfileOptions options = new ProfileOptions.Builder()
                    .text(input)
                    .build();

            ServiceCall call = service.profile(options);
            call.enqueue(new ServiceCallback<Profile>() {
                @Override
                public void onResponse(Profile profile) {
                    responseSuccess = true;
                    mPersonality = profile.getPersonality();
                }

                @Override
                public void onFailure(Exception e) {
                    mWatsonError = e.toString();
                    Log.d("URL onFailure", mWatsonError);
                }
            });

        }
    }

}


