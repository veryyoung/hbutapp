package com.hbut.other;

import java.io.File;

import android.os.Environment;

public class JudgeSDCard {
	/**
	 * 判断是否有内存卡
	 * @return
	 */
	public boolean judgeSdCardIsExist(){
		boolean isExist;
		isExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		return isExist;
	}
	/**
	 * 得到sd卡路径
	 * @return
	 */
	public String getSdPath(){
		File sdDir = null;
		if(judgeSdCardIsExist()){
			sdDir = Environment.getExternalStorageDirectory();
		}
		if(sdDir != null){
			return sdDir.toString();
		}else{
			return null;
		}
	}
	/**
	 * 获得手机内存路径
	 * @return
	 */
	public String getInternalMemoryPath(){
		return Environment.getDataDirectory().toString();
	}

}
