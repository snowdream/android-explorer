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

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

public class HDExplorerActivity extends ListActivity {

	//boolean
	private boolean misFullScreen = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(misFullScreen){
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
					WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		}

		setContentView(R.layout.main);
		
		registerForContextMenu(getListView());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		
	}

	@Override
	protected void onDestroy() {
		
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
	}

	@Override
	protected void onPause() {
		
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	@Override
	protected void onRestart() {
		
		// TODO Auto-generated method stub
		super.onRestart();
		
	}

	@Override
	protected void onResume() {
		
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	@Override
	protected void onStart() {
		
		// TODO Auto-generated method stub
		super.onStart();
		
	}

	@Override
	protected void onStop() {
		
		// TODO Auto-generated method stub
		super.onStop();
		
	}


}