package com.kyview.adapters;

import android.app.Activity;
import android.util.Log;

import android.view.ViewGroup;

import com.kyview.AdViewAdRegistry;
import com.kyview.AdViewLayout;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewLayout.ViewAdRunnable;
//import com.kyview.AdViewLayout.ViewAdRunnable;
import com.kyview.AdViewTargeting.RunMode;
import com.kyview.obj.Ration;
import com.kyview.util.AdViewUtil;

import com.baidu.mobads.AdService;
//import com.baidu.mobads.AdType;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
//import com.kyview.obj.Extra;
//import android.graphics.Color;
import android.content.Context;
import org.json.JSONObject;

public class AdBaiduAdapter extends AdViewAdapter implements AdViewListener {
	private boolean isFailed=false;
	private static int networkType() {
		return AdViewUtil.NETWORK_TYPE_BAIDU;
	}

	public static void load(AdViewAdRegistry registry) {
		try {
			if (Class.forName("com.baidu.mobads.AdView") != null) {
				registry.registerClass(networkType(), AdBaiduAdapter.class);
			}
		} catch (ClassNotFoundException e) {
		}
	}

	public AdBaiduAdapter() {
	}

	// private AdView adView;
	// private AdService adService;

	@Override
	public void initAdapter(AdViewLayout adViewLayout, Ration ration) {
		Context mContext = (Context) adViewLayout.activityReference.get();
		AdService.setChannelId("e498eab7");
		if (AdViewTargeting.getRunMode() == RunMode.TEST) {
			AdView.setAppSid(mContext, "debug");
			AdView.setAppSec(mContext, "debug");
		} else {
			AdView.setAppSid(mContext, ration.key);
			AdView.setAppSec(mContext, ration.key2);
		}

	}

	@Override
	public void handle() {
		if (AdViewTargeting.getRunMode() == RunMode.TEST)
			Log.d(AdViewUtil.ADVIEW, "Into Baidu");

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}
		Activity activity = adViewLayout.activityReference.get();
		if (activity == null) {
			return;
		}
		adViewLayout.removeAllViews();
		adViewLayout.activeRation = adViewLayout.nextRation;
		// if((ration.key3).compareTo("1")==0)
		// new AdService(activity,adViewLayout, new ViewGroup.LayoutParams(-1,
		// -2), this);
		// else
		new AdService(activity, adViewLayout,
				new ViewGroup.LayoutParams(-1, -2), this);
	}

	@Override
	public void onAdClick(JSONObject arg0) {
		if (AdViewTargeting.getRunMode() == RunMode.TEST)
			Log.w(AdViewUtil.ADVIEW, "onAdClick");

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}
		adViewLayout.reportClick();
	}

	@Override
	public void onAdFailed(String arg0) {
		isFailed=true;
		if (AdViewTargeting.getRunMode() == RunMode.TEST)
			Log.w(AdViewUtil.ADVIEW, "AdViewListener.onAdFailed, reason="
					+ arg0);

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}
		adViewLayout.adViewManager.resetRollover_pri();
		adViewLayout.rotateThreadedPri();
	}

	@Override
	public void onAdReady(AdView arg0) {
		if (AdViewTargeting.getRunMode() == RunMode.TEST)
			Log.w(AdViewUtil.ADVIEW, "onAdReady");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if(adViewLayout == null) {
		return;
		}
		if(!isFailed){
		adViewLayout.adViewManager.resetRollover();
		adViewLayout.handler.post(new ViewAdRunnable(adViewLayout, arg0));
		adViewLayout.rotateThreadedDelayed();
		isFailed=false;
		}
	}

	@Override
	public void onAdShow(JSONObject arg0) {
		if (AdViewTargeting.getRunMode() == RunMode.TEST)
			Log.w(AdViewUtil.ADVIEW, "onAdShow");
		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}
		adViewLayout.reportBaiduImpression();
	}

	@Override
	public void onAdSwitch() {
		if (AdViewTargeting.getRunMode() == RunMode.TEST)
			Log.w(AdViewUtil.ADVIEW, "onAdSwitch");

	}

	@Override
	public void onVideoClickAd() {
		if (AdViewTargeting.getRunMode() == RunMode.TEST)
			Log.w(AdViewUtil.ADVIEW, "onAdClick");

		AdViewLayout adViewLayout = adViewLayoutReference.get();
		if (adViewLayout == null) {
			return;
		}
		adViewLayout.reportClick();
	}

	@Override
	public void onVideoClickClose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVideoClickReplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVideoError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVideoFinish() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVideoStart() {
		// TODO Auto-generated method stub

	}



}
