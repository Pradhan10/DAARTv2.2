package com.testworkshop.prototype_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.testworkshop.prototype_2.utilities.HotelInfo;
import com.testworkshop.prototype_2.utilities.NetworkUtils;
import com.testworkshop.prototype_2.utilities.SolrAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "MainActivity";
    RecyclerView mrecyclerViewList;
    private ProgressBar mLoadingIndicator;
    public ArrayList<HotelInfo> showHotels;
    HotelInfo[] arr;
    RequestQueue mRequestQueue;
    private String startRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        resultfromWatson();
        //check if logged in from twitter, if not goto login
        setContentView(R.layout.activity_main);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mrecyclerViewList = findViewById(R.id.recyclerView_list);

        showHotels = new ArrayList<>();
        /*Get solr search vlaue from intent*/
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            startRange = String.valueOf(intent.getIntExtra(Intent.EXTRA_TEXT, 0));
        }
        makeSolrSearchQuery(startRange);


        //TODO : 10 STEPS
        /*
        * 1 : Do personality analysis by hitting Watson URL and store result
        * 2 : Form solr query based on Watson result, hit solr endpoint
        * 3 : Display query results in android
        * */
    }


    private void makeSolrSearchQuery(String startRange) {
        // TODO :4 Form query here using SQLite
        URL solrSearchUrl = NetworkUtils.buildUrl("id", startRange);
        Log.d("URL", solrSearchUrl.toString());

        /*Try volley for fast network resp*/
        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String url = solrSearchUrl.toString();

        // Start the queue
        mRequestQueue.start();


// Request a JSON response from the provided URL.

        /*TODO 12223322 : Continue from here, refer chrominum*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
//                    Log.d("URL res",response.toString());
                    int status = response.optInt("status");
                    if (status == 0) {
                        try {
                            JSONObject hotels = response.getJSONObject("response");
                            JSONArray docs = hotels.getJSONArray("docs");
                            if (docs != null) {

                                for (int k = 0; k < docs.length(); k++) {
                                    JSONObject hotelData = docs.getJSONObject(k);
                                    HotelInfo hotelInfo = new HotelInfo();
                                    hotelInfo.setCity(hotelData.getString("city"));
                                    hotelInfo.setProperty_name(hotelData.getString("property_name"));
                                    hotelInfo.setRoom_type(hotelData.getString("room_type"));

                                    showHotels.add(k, hotelInfo);
                                    Log.d("URL Hotels", hotelInfo.getProperty_name());
                                }
                                mrecyclerViewList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                mrecyclerViewList.setAdapter(new SolrAdapter(showHotels));
                            } else {
                                Log.d("URL Hotels", "docs null");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.d("URL Hotels", "status not zero");
                    }
                } else {
                    Log.d("URL Hotels", "response null");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG", error.toString());
                Log.d("URL error", error.toString());
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Add the request to the RequestQueue.
        mRequestQueue.add(jsonObjectRequest);
        //new SolrQueryTask().execute(solrSearchUrl);

    }


    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    private void showJsonDataView(String solrSearchResult) {
        // TODO : 8 Show data in cardView
        try {
            JSONObject solrSearchResultJSON = new JSONObject(solrSearchResult);
            Log.d("URL jsonResult", solrSearchResultJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Then, make sure the JSON data is visible
        Toast.makeText(getBaseContext(), solrSearchResult, Toast.LENGTH_SHORT).show();
    }


    private void showErrorMessage() {
        // TODO : 6 Guide to common result page of all types of deals

        // TODO : 7 Tell user, dataLoad failed. Showing result from SQLite.
        Toast.makeText(getBaseContext(), "Couldn't load data, Deals from SQLite", Toast.LENGTH_SHORT).show();
    }


//    public class SolrQueryTask extends AsyncTask<URL, Void, String> {
//
//        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(URL... params) {
//            URL searchUrl = params[0];
//            String solrSearchResults = null;
//            try {
//                solrSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return solrSearchResults;
//        }
//
//        @Override
//        protected void onPostExecute(String solrSearchResults) {
//            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
//            if (solrSearchResults != null && !solrSearchResults.equals("")) {
//                // TODO (17) Call showJsonDataView if we have valid, non-null results
//                showJsonDataView(solrSearchResults);
//                Log.i("Result from URL", solrSearchResults);
//            } else {
//                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
//                showErrorMessage();
//                Log.i("Result from URL", "ERROR");
//            }
//        }
//    }


//    private void resultfromWatson() {
//
//        arr = new SolrData[10];
//        arr[0] = new SolrData("Hotel Jivan Jyoti", "9th Mile, Kalimpong", "Rishi Road-Highway 12", "Kalimpong", "1 Star hotel");
//        arr[1] = new SolrData("OYO Hotel sagar", "New Kalimati Road, Kasidih, Sakchi", "Jamshedpur", "Jamshedpur", "1 Star hotel");
//        arr[2] = new SolrData("OYO 2578 Hotel Nirmal Haveli", "Opposite Nagarpalika, Near Pushkarna Bera", "Postal Colony", "Jaisalmer", "3 Star hotel");
//        arr[3] = new SolrData("Grand Kakinada by GRT Hotels ", "11-3-11 Veterinary Hospital road,Ramarao peta", "Cinema Road", "Kakinada", "3 Star hotel");
//        arr[4] = new SolrData("Hotel Gorakh Haveli", "Near Fort First Gate, Dhibbapara", "Near Jaisalmer Golden Fort", "Jaisalmer", "3 Star hotel");
//        arr[5] = new SolrData("Mohan Niwas", "High Court Colony, Jodhpur. Rajasthan", "Ratanada Petrol Pump", "Jodhpur", "1 Star hotel");
//        arr[6] = new SolrData("Hotel Navrang Kalka ", "SCO 44 - 45, New Grain Market, Kalka", "Near Kalka Railway Station", "Kalka", "2 Star hotel");
//        arr[7] = new SolrData("The Gopinivas Grand ", "2-18 A6, Near Seashore", " Kanyakumari", "Kanyakumari", "4 Star hotel");
//        arr[8] = new SolrData("Polo Inn Guest House", "90, 1st Polo, Mandar Road,", "Paota", "Jodhpur", "2 Star hotel");
//        arr[9] = new SolrData("Hotel The Vaishnodevi", "Opp: Petrol Pump Vaishno Devi, Katra", "Reasi Road", "Katra", "3 Star hotel");
//
//    }


}
