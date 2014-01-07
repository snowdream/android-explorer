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
package com.hd.explorer.interfaces;

public interface ISDCardListener extends IBroadCastListener {

    /**
     * Broadcast Action: External media was removed from SD card slot, but mount point was not unmounted.
     */
    void onSDCardBadRemoval();

    /**
     * Broadcast Action: The "Media Button" was pressed.
     */
    void onSDCardButton();
    
    /**
     * Broadcast Action: External media is present,
     * and being disk-checked The path to the mount point 
     * for the checking media is contained in the Intent.mData field.
     */
    void onSDCardChecking();
    
    /**
     * Broadcast Action: User has expressed the desire to remove the external storage media.
     */
    void onSDCardEject(); 
    
    /**
     * Broadcast Action: External media is present and mounted at its mount point.
     */
    void onSDCardMounted();
    
    /**
     * Broadcast Action: External media is present, but is using an incompatible fs 
     * (or is blank) The path to the mount point for the checking media is contained in the Intent.mData field.
     */
    void onSDCardNoFS();
    
    /**
     * Broadcast Action: External media has been removed.
     */
    void onSDCardRemoved();
    
    /**
     * Broadcast Action: The media scanner has finished scanning a directory.
     */
    void onSDCardScannerFinshed();
    
    /**
     * Broadcast Action: Request the media scanner to scan a file and add it to the media database.
     */
    void onSDCardScannerScaning();
    
    /**
     * Broadcast Action: The media scanner has started scanning a directory.
     */
    void onSDCardScannerStarted(); 
    
    /**
     * Broadcast Action: External media is unmounted because it is being shared via USB mass storage.
     */
    void onSDCardShared();
    
    /**
     * Broadcast Action: External media is present but cannot be mounted.
     */
    void onSDCardUnMountable();
    
    /**
     * Broadcast Action: External media is present, but not mounted at its mount point.
     */
    void onSDCardUnMounted();
}
