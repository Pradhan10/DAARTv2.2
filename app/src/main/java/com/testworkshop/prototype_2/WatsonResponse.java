package com.testworkshop.prototype_2;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.AutofillService;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WatsonResponse extends AppCompatActivity {


    public static double[] mPersonalityScore;
    ArrayList<String> mPersonalityTraits;
    Button mShowDeals;
    int[] traitArray = new int[5];
    int traitSum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watson_response);
        mShowDeals = findViewById(R.id.button_showDeals);
        mPersonalityTraits = new ArrayList();
        mPersonalityScore = new double[5];
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            mPersonalityTraits = intent.getStringArrayListExtra(Intent.EXTRA_TEXT);
            mPersonalityScore = intent.getDoubleArrayExtra("scores");

        }
        ListView listView = findViewById(R.id.listview);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        for (int j = 0; j < mPersonalityScore.length; j++) {

            if (mPersonalityScore[j] > 0.6) {
                traitArray[j] = 1;
            }
            traitSum += traitArray[j];
        }
        mShowDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int startRange = determineDeal();
                Intent mainIntent = new Intent(WatsonResponse.this, MainActivity.class);
                mainIntent.putExtra(Intent.EXTRA_TEXT, startRange);
                startActivity(mainIntent);
                finish();

            }
        });

    }

    private int determineDeal() {

        if (traitSum >= 5) {
            return 0;
        } else if (traitSum == 4) {
            return 10;
        } else if (traitSum == 3) {
            return 20;
        } else if (traitSum == 2) {
            return 30;
        } else if (traitSum == 1) {
            return 40;
        } else {
            return 50;
        }
    }

    class CustomAdapter extends BaseAdapter {


        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return mPersonalityTraits.size();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return null;
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_listview_layout, null);

            TextView textView_name = convertView.findViewById(R.id.tv_name);
            TextView textView_behaviour = convertView.findViewById(R.id.tv_behaviour);
            textView_behaviour.setText(String.valueOf(mPersonalityScore[position]));
            textView_name.setText(mPersonalityTraits.get(position).toString());
            return convertView;
        }

        /**
         * Gets a string representation of the adapter data that can help
         * {@link AutofillService} autofill the view backed by the adapter.
         * <p>
         * <p>
         * It should only be set (i.e., non-{@code null} if the values do not represent PII
         * (Personally Identifiable Information - sensitive data such as email addresses,
         * credit card numbers, passwords, etc...). For
         * example, it's ok to return a list of month names, but not a list of usernames. A good rule of
         * thumb is that if the adapter data comes from static resources, such data is not PII - see
         * {@link ViewStructure#setDataIsSensitive(boolean)} for more info.
         *
         * @return {@code null} by default, unless implementations override it.
         */
        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }
    }


}
