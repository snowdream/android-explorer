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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.hd.Constant;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * 
 * ClassName:HDExplorerActivity
 * Reason:	 The Main Activity of The Explorer
 *
 * @author   yang hui
 * @version  
 * @since    Ver 1.1
 * @Date	 2011	2011-11-29		00:39:13
 *
 * @see 	 
 */
public class HDExplorerActivity extends ListActivity {

	private static final String TAG = "HDExplorerActivity";
	//Boolean Flags
	private boolean misFullScreen = true;

	//Dialogs ID
	private final int DIALOG_EXIT_APP = 0;

	//the data source
	List<File> mfiles = null; 

	//BaseAdapter 
	HDBaseAdapter madapter = null; 

	//Menu
	private static final int MENU_SETTINGS = Menu.FIRST;
	private static final int MENU_ABOUT = Menu.FIRST+1;

	//String
	private String sdcard = "/mnt/sdcard";

	//String
	private String mCurrentPath;

	//admob view
	private AdView adView;

	/**
	 * 
	 * onCreate: Called when the activity is first created.
	 *
	 * @param    savedInstanceState  The struct to save the data.
	 * @return     
	 * @throws 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG,"onCreate");
		super.onCreate(savedInstanceState);

		if(misFullScreen){
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
					WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		}

		setContentView(R.layout.main);

		registerForContextMenu(getListView());

		// Look up the AdView as a resource and load a request.
		adView = (AdView)this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());

		init();
	}

	/**
	 * 
	 * init: Init the data 
	 *
	 * @param   
	 * @return     
	 * @throws 
	 */
	public void init(){
		Log.i(TAG,"init");
		// adView = new AdView(this, AdSize.BANNER, getString(R.string.MY_AD_UNIT_ID));

		mfiles = new ArrayList<File>();

		madapter = new HDBaseAdapter(this,mfiles);	

		setListAdapter(madapter);

		File sdf = new File(sdcard);

		open(sdf);
	}

	/**
	 * 
	 * addItem: add an object
	 *
	 * @param   item  An object which will be added.
	 * @return      
	 * @throws 
	 */	
	private void addItem(File f)
	{
		Log.i(TAG,"addItem");

		mfiles.add(f);
		madapter.notifyDataSetChanged();
	}

	/**
	 * 
	 * addItem: delete an object
	 *
	 * @param   item  An object which will be deleted.
	 * @return      
	 * @throws 
	 */	
	private void deleteItem(File f)
	{
		Log.i(TAG,"deleteItem");

		mfiles.remove(f);
		madapter.notifyDataSetChanged();
	}

	/**
	 * 
	 * addItem: delete All object
	 *
	 * @param   
	 * @return      
	 * @throws 
	 */	
	private void deleteAllItems()
	{
		Log.i(TAG,"deleteAllItems");

		mfiles.clear();
		madapter.notifyDataSetChanged();
	}

	/**
	 * 
	 * addItem: open a file
	 *
	 * @param   filename  The name of the file which will be opened.
	 * @return      
	 * @throws 
	 */		
	private void open(File f){
		Log.i(TAG,"open");

		if(!f.exists())
			return;

		if(!f.canRead())
			return;

		if(f.isFile()){
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			String type = Constant.getMIMEType(f);
			intent.setDataAndType(Uri.fromFile(f), type);
			startActivity(intent);    

		}else if(f.isDirectory()){
			deleteAllItems();

			mCurrentPath = f.getPath();
			setTitle(mCurrentPath);

			File[] files = f.listFiles();

			// 排序
			Arrays.sort(files, new FileComparator());

			for(File file:files){
				addItem(file);
			}
		}
	}

	private void copy(File f){

	}

	private void cut(File f){

	}		

	private void paste(File f){

	}	

	private void delete(File f){

	}		

	private void showdetails(File f){

	}

	/**
	 * 
	 * onActivityResult: onActivityResult
	 *
	 * @param    requestCode  The value of requestCode
	 * @param    resultCode  The value of resultCode
	 * @param    data  The data from the last Activity
	 * @return     
	 * @throws 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG,"onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * 
	 * onConfigurationChanged: onConfigurationChanged
	 *
	 * @param    newConfig  Configuration
	 * @return     
	 * @throws 
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.i(TAG,"onActivityResult");

		try {
			super.onConfigurationChanged(newConfig);
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * onDestroy: onDestroy
	 *
	 * @param      
	 * @return     
	 * @throws 
	 */
	@Override
	protected void onDestroy() {
		Log.i(TAG,"onDestroy");
		super.onDestroy();

	}


	/**
	 * 
	 * onNewIntent: onNewIntent
	 *
	 * @param      
	 * @return     
	 * @throws 
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		Log.i(TAG,"onNewIntent");
		super.onNewIntent(intent);

	}

	/**
	 * 
	 * onPause: onPause
	 *
	 * @param      
	 * @return     
	 * @throws 
	 */
	@Override
	protected void onPause() {
		Log.i(TAG,"onPause");
		super.onPause();

	}

	/**
	 * 
	 * onRestart: onRestart
	 *
	 * @param      
	 * @return     
	 * @throws 
	 */
	@Override
	protected void onRestart() {
		Log.i(TAG,"onRestart");
		super.onRestart();

	}

	/**
	 * 
	 * onResume: onResume
	 *
	 * @param      
	 * @return     
	 * @throws 
	 */
	@Override
	protected void onResume() {
		Log.i(TAG,"onResume");
		super.onResume();

	}

	/**
	 * 
	 * onStart: onStart
	 *
	 * @param      
	 * @return     
	 * @throws 
	 */
	@Override
	protected void onStart() {
		Log.i(TAG,"onStart");
		super.onStart();

	}

	/**
	 * 
	 * onStop: onStop
	 *
	 * @param      
	 * @return     
	 * @throws 
	 */
	@Override
	protected void onStop() {
		Log.i(TAG,"onStop");
		super.onStop();

	}

	/**
	 * 
	 * onKeyDown: KeyEvent
	 *
	 * @param      keyCode The value of keyCode
	 * @param      event KeyEvent
	 * @return     boolean   True if the KeyEvent has been dealt with, otherwise false.
	 * @throws 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i(TAG,"onKeyDown");
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(mCurrentPath.equals(sdcard))
			{
				showDialog(DIALOG_EXIT_APP);
			}
			else{
				File f = new File(mCurrentPath);
				open(f.getParentFile());
			}
			return true;
			//break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);

	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		File mselectedFile = madapter.getItem(position);
		if(mselectedFile != null)
			open(mselectedFile);
	}

	/**
	 * 
	 * onCreateDialog: Create All The Dialogs.
	 *
	 * @param      id  The id of the dialog which should be created
	 * @return     Dialog The dialog which has been created.   
	 * @throws 
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Log.i(TAG,"onCreateDialog");
		switch (id) {
		case DIALOG_EXIT_APP:
			AlertDialog.Builder mexitDialog = new AlertDialog.Builder(this);
			mexitDialog.setIcon(android.R.drawable.ic_dialog_alert);
			mexitDialog.setTitle(R.string.dialog_exit_app_title);
			mexitDialog.setMessage(R.string.dialog_exit_app_message);
			mexitDialog.setPositiveButton(R.string.button_text_yes, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			mexitDialog.setNegativeButton(R.string.button_text_no, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			return mexitDialog.create();
		default:
			break;
		}
		return super.onCreateDialog(id);

	}

	/**
	 * 
	 * onPrepareDialog: Update the Dialogs if needed.
	 *
	 * @param      id  The id of the dialog which should be updated.
	 * @param      Dialog The dialog which should be updated.   
	 * @throws 
	 */	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		Log.i(TAG,"onPrepareDialog");
		super.onPrepareDialog(id, dialog);

	}


	/**
	 * 
	 * onCreateOptionsMenu: Create the Menu if needed.
	 *
	 * @param      menu  The Menu which should be created.
	 * @throws 
	 */	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG,"onCreateOptionsMenu");
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 
	 * onPrepareOptionsMenu: Update the OptionsMenu if needed.
	 *
	 * @param      menu  The Menu which should be updated.
	 * @throws 
	 */	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Log.i(TAG,"onPrepareOptionsMenu");
		menu.clear();
		menu.add(0, MENU_SETTINGS, 1, R.string.menu_setting_text).setIcon(android.R.drawable.ic_menu_preferences);
		menu.add(0, MENU_ABOUT, 2, R.string.menu_about_text).setIcon(android.R.drawable.ic_menu_info_details);
		return super.onPrepareOptionsMenu(menu);

	}

	/**
	 * 
	 * onOptionsItemSelected: Update the Dialogs if needed.
	 *
	 * @param      item  The menu which is selected.
	 * @return     true if the event has been dealt with,otherwise false.
	 * @throws 
	 */	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG,"onOptionsItemSelected");

		return super.onOptionsItemSelected(item);

	}

}
