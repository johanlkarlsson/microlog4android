package com.google.code.microlog4android.integration.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import android.app.Activity;
import android.os.Bundle;

public class IntegrationTests extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Logger logger = LoggerFactory.getLogger(IntegrationTests.class);
        logger.debug("Integration test 123");
    }
}