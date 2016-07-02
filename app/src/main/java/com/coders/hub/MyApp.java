package com.coders.hub;

import android.app.Application;

import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.adobeinternal.analytics.AdobeAnalyticsManager;
import com.adobe.creativesdk.foundation.adobeinternal.analytics.AdobeAnalyticsMode;
import com.adobe.creativesdk.foundation.internal.auth.AdobeAuthIMSEnvironment;
import com.aviary.android.feather.sdk.IAviaryClientCredentials;

/**
 * Created by Mohsin on 02-07-2016.
 */
public class MyApp extends Application implements IAviaryClientCredentials {

    private static String CREATIVE_SDK_SAMPLE_CLIENT_ID;
    private static String CREATIVE_SDK_SAMPLE_CLIENT_SECRET;

    @Override
    public void onCreate() {
        super.onCreate();
        CREATIVE_SDK_SAMPLE_CLIENT_ID=getString(R.string.creative_sdk_client_id);
        CREATIVE_SDK_SAMPLE_CLIENT_SECRET=getString(R.string.creative_sdk_secret_id);
        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext(), AdobeAuthIMSEnvironment.AdobeAuthIMSEnvironmentProductionUS);
        AdobeAnalyticsManager.getInstance().enableAnalyticsReporting(AdobeAnalyticsMode.ADOBE_ANALYTICS_MODE_TEST);
    }

    @Override
    public String getBillingKey() {
        return "";
    }

    @Override
    public String getClientID() {
        return CREATIVE_SDK_SAMPLE_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_SAMPLE_CLIENT_SECRET;
    }
}
