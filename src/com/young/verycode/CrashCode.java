package com.young.verycode;

import java.io.IOException;
import java.io.InputStream;

import com.young.activity.App;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CrashCode {


	public static String compare(Bitmap image) throws IOException {
		Bitmap checkCode[] = Tools.getCheckCodes(image);
		StringBuffer code = new StringBuffer();
		AssetManager mngr = App.getContext().getAssets();
		for (int t = 0; t < 4; t++) {
			int[] result = new int[10];
			boolean ckFlg = false;
			int num = -1;
			for (int i = 0; i < 10; i++) {
				num = -1;
				ckFlg = true;
				InputStream is = mngr.open(i + ".bmp");
				Bitmap testImage = BitmapFactory.decodeStream(is);
				if (testImage == null) {
					continue;
				}
				
				for (int y = 0; y < checkCode[t].getHeight(); ++y) {
					for (int x = 0; x < checkCode[t].getWidth(); ++x) {

						int expRGB = Tools.pixelConvert(checkCode[t].getPixel(x, y));
						int cmpRGB = Tools.pixelConvert(testImage.getPixel(x, y));
						if (expRGB == cmpRGB) {
							result[i]++;
						}
					}
				}
				if (result[i] > 90) {
					ckFlg = true;
					num = i;
					break;
				}
			}
			if (ckFlg) {
				code.append(num);
				ckFlg = false;
			} else {
				ckFlg = false;
			}
		}
		return code.toString();

	}
}
