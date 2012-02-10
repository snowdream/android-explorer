/*
 * Copyright (c) 2011 yang hui <yanghui1986527@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License v2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 021110-1307, USA.
 */

package com.hd.explorer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.view.WindowManager;

public class HDPreferenceActivity extends PreferenceActivity implements
OnPreferenceChangeListener, OnPreferenceClickListener {

	private static final String TAG = "HDSettings";

	//Dialogs ID
	private final int DIALOG_THANKS_TO = 0;

	private CheckBoxPreference check_showhidden;
	String key_showhidden;
	boolean default_value_showhidden;
	
	private PreferenceScreen screen_thanksto;
	String key_thanksto;

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

		key_thanksto = getResources().getString(R.string.preference_thanks_key); 
		screen_thanksto = (PreferenceScreen) findPreference(key_thanksto);
		screen_thanksto.setOnPreferenceClickListener(this);
		
		
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
		Log.i(TAG,"onPreferenceClick");

		if(preference == screen_thanksto){
			showDialog(DIALOG_THANKS_TO);
			return true;
		}
		return false;
	}



	/**
	 * <b>onCreateDialog</b><br/>
	 *  创建对话框<br/>
	 *  
	 * @param   id 对话框id
	 * @return  Dialog，创建的对话框窗口
	 */	
	@Override
	protected Dialog onCreateDialog(int id) {
		Log.i(TAG,"onCreateDialog");
		switch (id) {
		case DIALOG_THANKS_TO:
			AlertDialog.Builder mthankstoDialog = new AlertDialog.Builder(this);
			mthankstoDialog.setIcon(android.R.drawable.ic_dialog_info);
			mthankstoDialog.setTitle(R.string.dialog_thanks_to_title);
			mthankstoDialog.setMessage(R.string.dialog_thanks_to_message);
			mthankstoDialog.setPositiveButton(R.string.button_text_yes, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();		
				}
			});
			return mthankstoDialog.create();
		default:
			break;
		}
		return super.onCreateDialog(id);
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
