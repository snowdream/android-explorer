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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

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



	//String
	private String sdcard = "/mnt/sdcard";

	//String
	private String mCurrentPath;

	//admob view
	private AdView adView;

	//cut and copy 
	private String mCutOrCopyPath;
	private int mAction = ACTION_NONE;

	private static final int ACTION_NONE = 0;
	private static final int ACTION_CUT = 1;
	private static final int ACTION_COPY = 2;



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

	private void openwith(File f){
		Log.i(TAG,"open");

		if(!f.exists())
			return;

		if(!f.canRead())
			return;

		if(f.isDirectory())
			return;

		if(f.isFile()){
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			String type = Constant.getMIMEType(f);
			intent.setDataAndType(Uri.fromFile(f), type);
			startActivity(intent);    

		}
	}

	/** 复制文件 **/
	public boolean copyFile(File src, File tar) throws Exception {
		if (src.isFile()) {
			InputStream is = new FileInputStream(src);
			OutputStream op = new FileOutputStream(tar);
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(op);
			byte[] bt = new byte[1024 * 8];
			int len = bis.read(bt);
			while (len != -1) {
				bos.write(bt, 0, len);
				len = bis.read(bt);
			}
			bis.close();
			bos.close();
		}
		if (src.isDirectory()) {
			File[] f = src.listFiles();
			tar.mkdir();
			for (int i = 0; i < f.length; i++) {
				copyFile(f[i].getAbsoluteFile(), new File(tar.getAbsoluteFile() + File.separator
						+ f[i].getName()));
			}
		}
		return true;
	}

	/** 移动文件 **/
	public boolean moveFile(File src, File tar) throws Exception {
		if (copyFile(src, tar)) {
			deleteFile(src);
			return true;
		}
		return false;
	}

	/** 删除文件 **/
	public void deleteFile(File f) {

		if (f.isDirectory()) {
			File[] files = f.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; ++i) {
					deleteFile(files[i]);
				}
			}
		}
		f.delete();
	}

	private void copy(File f){
		mAction = ACTION_COPY;
		mCutOrCopyPath = f.getAbsolutePath();
	}

	private void cut(File f){
		mAction = ACTION_CUT;
		mCutOrCopyPath = f.getAbsolutePath();
	}		

	private void paste(){
		switch (mAction) {
		case ACTION_COPY:
			if((mCutOrCopyPath != null) && (mCurrentPath != null))
			{
				File src = new File(mCutOrCopyPath);
				File dest = new File(mCurrentPath);
				
				try {
					copyFile(src, dest);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			break;
		case ACTION_CUT:
			if((mCutOrCopyPath != null) && (mCurrentPath != null))
			{
				File src = new File(mCutOrCopyPath);
				File dest = new File(mCurrentPath);
				
				try {
					moveFile(src, dest);
					deleteItem(src);
					mCutOrCopyPath = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
			break;
		default:
			break;
		}
		
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


	/**
	 * <b>onCreateContextMenu</b><br/>
	 *  长按文件管理器中的文件，弹出上下文菜单
	 *  
	 * @param  menu 上下文菜单
	 * @param  v  正在被创建的菜单视图
	 * @param  menuInfo  上下文菜单信息
	 * @return    无
	 * @exception   无
	 */	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context, menu);
		
		AdapterView.AdapterContextMenuInfo info = null;

		try {
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {
			Log.e(TAG, "bad menuInfo", e);
			return;
		}

		File mselectedFile = madapter.getItem(info.position);
		if(mselectedFile != null)
		{
			menu.setHeaderTitle(mselectedFile.getName());
		}
	}

	/**
	 * <b>onContextItemSelected</b><br/>
	 *  点击上下文菜单选项时的事件处理
	 *  
	 * @param  item 被点击的菜单项
	 * @return    true,表示事件已经处理；false，表示未处理。
	 * @exception   无
	 */	
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		File f = madapter.getItem(info.position);
		if(f == null)
			return false;
		switch (item.getItemId()) {
		case R.id.open:
			open(f);
			return true;
		case R.id.openwith:
			openwith(f);
			return true;
		case R.id.copy:
			copy(f);
			return true;
		case R.id.cut:
			cut(f);
			return true;
		case R.id.paste:
			paste();
			return true;
		case R.id.rename:
			//rename(f);
			return true;
		case R.id.delete:
			deleteFile(f);
			deleteItem(f);
			return true;
		case R.id.attribute:
			//attribute(f);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
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
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
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
		return true;
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
