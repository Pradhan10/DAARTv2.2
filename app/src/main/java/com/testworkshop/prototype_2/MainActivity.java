package com.testworkshop.prototype_2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.testworkshop.prototype_2.utilities.NetworkUtils;
import com.testworkshop.prototype_2.utilities.SolrAdapter;
import com.testworkshop.prototype_2.utilities.SolrData;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    SolrData[] arr;
    RecyclerView mrecyclerViewList;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check if logged in from twitter, if not goto login
        setContentView(R.layout.activity_main);
        mrecyclerViewList = findViewById(R.id.recyclerView_list);
        mrecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        initData();
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        makeSolrSearchQuery();
        mrecyclerViewList.setAdapter(new SolrAdapter(arr));
        //TODO : 10 STEPS
        /*
        * 1 : Do personality analysis by hitting Watson URL and store result
        * 2 : Form solr query based on Watson result, hit solr endpoint
        * 3 : Display query results in android
        * */
    }


    private void makeSolrSearchQuery(){
        // TODO :4 Form query here using SQLite
        URL solrSearchUrl = NetworkUtils.buildUrl("id", "SOLR1000");
        Log.d("URL", solrSearchUrl.toString());
        new SolrQueryTask().execute(solrSearchUrl);

    }


    private void showJsonDataView(String solrSearchResult) {
        // TODO : 8 Show data in cardView

        // Then, make sure the JSON data is visible
        Toast.makeText(getBaseContext(), solrSearchResult, Toast.LENGTH_SHORT).show();
    }


    private void showErrorMessage() {
        // TODO : 6 Guide to common result page of all types of deals

        // TODO : 7 Tell user, dataLoad failed. Showing result from SQLite.
        Toast.makeText(getBaseContext(), "Couldn't load data, Deals from SQLite", Toast.LENGTH_SHORT).show();
    }

    private void initData() {

        arr = new SolrData[10];
        arr[0] = new SolrData("Hotel Jivan Jyoti", "9th Mile, Kalimpong", "Rishi Road-Highway 12", "Kalimpong", "1 Star hotel");
        arr[1] = new SolrData("OYO Hotel sagar", "New Kalimati Road, Kasidih, Sakchi", "Jamshedpur", "Jamshedpur", "1 Star hotel");
        arr[2] = new SolrData("OYO 2578 Hotel Nirmal Haveli", "Opposite Nagarpalika, Near Pushkarna Bera", "Postal Colony", "Jaisalmer", "3 Star hotel");
        arr[3] = new SolrData("Grand Kakinada by GRT Hotels ", "11-3-11 Veterinary Hospital road,Ramarao peta", "Cinema Road", "Kakinada", "3 Star hotel");
        arr[4] = new SolrData("Hotel Gorakh Haveli", "Near Fort First Gate, Dhibbapara", "Near Jaisalmer Golden Fort", "Jaisalmer", "3 Star hotel");
        arr[5] = new SolrData("Mohan Niwas", "High Court Colony, Jodhpur. Rajasthan", "Ratanada Petrol Pump", "Jodhpur", "1 Star hotel");
        arr[6] = new SolrData("Hotel Navrang Kalka ", "SCO 44 - 45, New Grain Market, Kalka", "Near Kalka Railway Station", "Kalka", "2 Star hotel");
        arr[7] = new SolrData("The Gopinivas Grand ", "2-18 A6, Near Seashore", " Kanyakumari", "Kanyakumari", "4 Star hotel");
        arr[8] = new SolrData("Polo Inn Guest House", "90, 1st Polo, Mandar Road,", "Paota", "Jodhpur", "2 Star hotel");
        arr[9] = new SolrData("Hotel The Vaishnodevi", "Opp: Petrol Pump Vaishno Devi, Katra", "Reasi Road", "Katra", "3 Star hotel");
    }

    public class SolrQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String solrSearchResults = null;
            try {
                solrSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return solrSearchResults;
        }

        @Override
        protected void onPostExecute(String solrSearchResults) {
            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (solrSearchResults != null && !solrSearchResults.equals("")) {
                // TODO (17) Call showJsonDataView if we have valid, non-null results
//                showJsonDataView();
//                mSearchResultsTextView.setText(githubSearchResults);
                showJsonDataView(solrSearchResults);
                Log.d("Result from URL", solrSearchResults);
            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                showErrorMessage();
                Log.d("Result from URL", "ERROR");
            }
        }
    }


}
