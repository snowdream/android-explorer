/**
 * Copyright (C) 2012 yanghui <yanghui1986527@gmail.com>
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hd.explorer.broadcastreceiver;

import java.util.HashSet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hd.explorer.interfaces.IListener;
import com.hd.explorer.interfaces.ISDCardListener;

/**
 * system BroadcastReceiver
 */
public class SystemBroadCastReceiver extends BroadcastReceiver {
	private static HashSet<IListener> listeners = new HashSet<IListener>();
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    if (intent == null) {
	        Log.w("","The intent from the BroadcastReceiver is null.");
            return;
        }
	    
        Object o[] =  listeners.toArray();
        int size = o.length;
        
		String action = intent.getAction();
		if(action.equals(Intent.ACTION_MEDIA_BAD_REMOVAL)) {

		    for (int i = 0; i < size; i++) {
		        Object object = o[i];
		        ISDCardListener listener = null;
		        
		        if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
		        
		        listener = (ISDCardListener)object;
		        listener.onSDCardBadRemoval();
            }
		    
		}else if(action.equals(Intent.ACTION_MEDIA_BUTTON)) {
		    
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardButton();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_BUTTON)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardButton();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_BUTTON)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardButton();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_BUTTON)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardButton();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_BUTTON)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardButton();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_BUTTON)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardButton();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_BUTTON)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardButton();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_CHECKING)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardChecking();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_EJECT)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardEject();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardMounted();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_NOFS)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardNoFS();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_REMOVED)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardRemoved();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_SCANNER_FINISHED)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardScannerFinshed();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardScannerScaning();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_SCANNER_STARTED)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardScannerStarted();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_SHARED)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardShared();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_UNMOUNTABLE)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardUnMountable();
            }
        }else if(action.equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
            
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                ISDCardListener listener = null;
                
                if (object == null || !(object instanceof ISDCardListener)) {
                    continue;
                }
                
                listener = (ISDCardListener)object;
                listener.onSDCardUnMounted();
            }
        }
		
	}
	
	public static void addListener(IListener listener) {
	    listeners.add(listener);
	}

    public static void removeListener(IListener listener) {
        listeners.remove(listener);
    }
}
