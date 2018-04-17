package com.testworkshop.prototype_2;

import android.content.Context;
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

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Behavior;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;

import java.util.List;

//import com.testworkshop.prototype_2.utilities.WatsonProvider;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static String mWatsonResponse = "Default";
    private static String mWatsonError = "Error";
    private static boolean isNetworkAvailable = false;
    private static boolean responseSuccess = false;
    private ProgressBar mProgress;
    private EditText mAnalyseText;
    private Button mAnalyseButton;
    private List<Behavior> behaviour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitterlogin);
        mProgress = findViewById(R.id.progress_bar);
        mAnalyseText = findViewById(R.id.et_input);


        mAnalyseButton = findViewById(R.id.button_analyse);
        View mParent = findViewById(R.id.parent_id);

        isNetworkAvailable = isNetworkAvailable();
        if (isNetworkAvailable == true) {
            mAnalyseButton.setEnabled(true);
            mAnalyseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = mAnalyseText.getText().toString();
                    if (input.length() >= 200) {
                        WatsonRunnable mRunnable = new WatsonRunnable();
                        mRunnable.run();
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
            Log.d("URL", input);
            ProfileOptions options = new ProfileOptions.Builder()
                    .text(input)
                    .build();

            ServiceCall call = service.profile(options);
            call.enqueue(new ServiceCallback<Profile>() {
                @Override
                public void onResponse(Profile profile) {
                    responseSuccess = true;
                    behaviour = profile.getBehavior();
                    //mWatsonResponse = behaviour.get(0).getName();
                    Log.d("URL onResponse", profile.toString());
                }

                @Override
                public void onFailure(Exception e) {
                    mWatsonError = e.toString();
                    Log.d("URL onFailure", mWatsonError);
                }
            });

        }
    }

//    public class PersonalityAnalysisAsync extends AsyncTask<String,Void,String>{
//
//        /**
//         * Runs on the UI thread before {@link #doInBackground}.
//         *
//         * @see #onPostExecute
//         * @see #doInBackground
//         */
//
//        @Override
//        protected void onPreExecute(){
//            mAnalyseButton.setEnabled(false);
//            mProgress.setVisibility(View.VISIBLE);
//
//        }
//
//        /**
//         * Override this method to perform a computation on a background thread. The
//         * specified parameters are the parameters passed to {@link #execute}
//         * by the caller of this task.
//         * <p>
//         * This method can call {@link #publishProgress} to publish updates
//         * on the UI thread.
//         *
//         * @param strings The parameters of the task.
//         * @return A result, defined by the subclass of this task.
//         * @see #onPreExecute()
//         * @see #onPostExecute
//         * @see #publishProgress
//         */
//        @Override
//        protected String doInBackground(String... strings) {
//
//            return mWatsonResponse;
//
//        }
//
//
//
//        /**
//         * <p>Runs on the UI thread after {@link #doInBackground}. The
//         * specified result is the value returned by {@link #doInBackground}.</p>
//         * <p>
//         * <p>This method won't be invoked if the task was cancelled.</p>
//         *
//         * @param s The result of the operation computed by {@link #doInBackground}.
//         * @see #onPreExecute
//         * @see #doInBackground
//         * @see #onCancelled(Object)
//         */
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            mAnalyseButton.setEnabled(true);
//            mProgress.setVisibility(View.INVISIBLE);
//            Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();
//            if (responseSuccess == true && behaviour.size() > 0 ){
//                Log.d("URL nextActivity",behaviour.get(0).toString());
//            }
//        }
//    }

}


