package com.golf.wolf;

import com.activeandroid.ActiveAndroid;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



public class WolfApplication extends android.app.Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
		
		
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}
}
