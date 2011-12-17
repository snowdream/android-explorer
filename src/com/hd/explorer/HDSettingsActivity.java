package com.hd.explorer;


import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.WindowManager;

public class HDSettingsActivity extends PreferenceActivity implements
OnPreferenceChangeListener, OnPreferenceClickListener {
	private CheckBoxPreference check_showhidden;
	String key_showhidden;
	boolean default_value_showhidden;

	/**
	 * <b>onCreate</b><br/>
	 * HDRecorderSettingsActivity activity创建时首先调用该方法
	 * 
	 * @param   savedInstanceState 用于保存数据
	 * @return    无
	 * @exception   无
	 */		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//从xml文件中添加Preference项
		addPreferencesFromResource(R.xml.settings);	

		if(/*misFullScreen*/true){
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
					WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		}
		
		key_showhidden = getResources().getString(R.string.preference_showhidden_key);  
		default_value_showhidden = Boolean.parseBoolean(getResources().getString(R.string.preference_showhidden_default_value));

		check_showhidden = (CheckBoxPreference) findPreference(key_showhidden);
		check_showhidden.setOnPreferenceChangeListener(this);

		SharedPreferences settings = getPreferenceManager().getSharedPreferences();

		Boolean isshowhidden = settings.getBoolean(key_showhidden, default_value_showhidden);
		check_showhidden.setChecked(isshowhidden);
	}

	/**
	 * <b>onDestroy</b><br/>
	 *  onDestroy<br/>
	 *  
	 * @param   无
	 * @return   无
	 */	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * <b>onPreferenceClick</b><br/>
	 *  当Preference项被点击时响应事件<br/>
	 *  
	 * @param   preference Preference
	 * @return  true，表示已经处理；false，表示未处理。
	 */	
	@Override
	public boolean onPreferenceClick(Preference preference) {
		return false;
	}

	/**
	 * <b>onPreferenceChange</b><br/>
	 *  当Preference的值发生改变时，响应事件，保存修改后的值。<br/>
	 *  
	 * @param   preference Preference
	 * @param   newValue 修改后的值
	 * @return  true，表示已经处理；false，表示未处理。
	 */	
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		SharedPreferences settings = getPreferenceManager().getSharedPreferences();
		Editor edit = settings.edit();

		if(preference == check_showhidden){
			edit.putBoolean(key_showhidden,  (Boolean)newValue);
		}
		edit.commit();
		return true;
	}

	/**
	 * <b>onConfigurationChanged</b><br/>
	 *  屏幕旋转时，触发该响应<br/>
	 *  
	 * @param   newConfig 屏幕配置信息
	 * @return   无
	 */	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		try {
			super.onConfigurationChanged(newConfig);
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			}
		} catch (Exception e) {
		}
	}
}
