package com.testworkshop.prototype_2.utilities;

import android.provider.BaseColumns;

/**
 * Created by yash on 7/4/18.
 */

public final class WatsonFeedContract {
    // To prevent someone from accidently instantiating WatsonFeedContract class,
    // make constructor private


    private WatsonFeedContract() {
    }

    /*Inner class that defines the table contents*/
    public static class WatsonEntry implements BaseColumns {

        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TEXT_TO_ANALYSE = "text_to_analyse";
        public static final String COLUMN_NAME_PERSON_NAME = "person_name";


    }


}
