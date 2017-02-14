package com.vibeosys.lawyerdiary.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Vibeosys software on 11-10-2016.
 */

/**
 * Reading the property constants from the assets directory in the application
 * read the app.properties from the assets directory
 */
public class PropertyFileReader {
    private static PropertyFileReader mPropertyFileReader = null;
    private static Context mContext;
    protected static Properties mProperties;

    /**
     * Get the PropertyFileReader object instance
     * Create the new for 1st instance O.W. return the current object
     *
     * @param context Application context that use to create the instance
     * @return PropertyFileReader instance if mPropertyFileReader is not null create new one
     */
    public static PropertyFileReader getInstance(Context context) {
        if (mPropertyFileReader != null)
            return mPropertyFileReader;

        mContext = context;
        mProperties = getProperties();
        mPropertyFileReader = new PropertyFileReader();
        return mPropertyFileReader;
    }

    /**
     * Get the properties from the assets folder app.properties file
     *
     * @return Properties that read from the app.properties
     */
    protected static Properties getProperties() {
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open("app.properties");
            mProperties = new Properties();
            mProperties.load(inputStream);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return mProperties;
    }

    protected String getEndPointUri() {
        return mProperties.getProperty(PropertyTypeConstants.API_ENDPOINT_URI);
    }
}
