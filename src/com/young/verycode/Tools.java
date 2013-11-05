package com.young.verycode;




import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Tools {
	public static int pixelConvert(int pixel) {
		int result = 0;

		int r = (pixel >> 16) & 0xff;
		int g = (pixel >> 8) & 0xff;
		int b = (pixel) & 0xff;

		result = 0xff000000;

		int tmp = r * r + g * g + b * b;
		if (tmp > 3 * 128 * 128) {
			result += 0x00ffffff;			
		}

		return result;
	}

	public static Bitmap getImage(String path) {
		Bitmap image = null;
		image = BitmapFactory.decodeFile(path);
		return image;
	}

	public static Bitmap getSingleCode(Bitmap image) {
		return Bitmap.createBitmap(image,6, 5, 36, 12);
	}

	public static Bitmap[] getCheckCodes(Bitmap image) {
		Bitmap checkCode[] = new Bitmap[4];
		int height = image.getHeight();
		int width = image.getWidth();
		int x = 0 * (width / checkCode.length);
		int y = 0;
		int w = width / checkCode.length;
		int h = height;
		checkCode[0] = Bitmap.createBitmap(image,x, y, w, h);
		checkCode[1] = Bitmap.createBitmap(image,1 * (width / checkCode.length), 0,
				width / checkCode.length, height);
		checkCode[2] = Bitmap.createBitmap(image,2 * (width / checkCode.length), 0,
				width / checkCode.length, height);
		checkCode[3] = Bitmap.createBitmap(image,3 * (width / checkCode.length), 0,
				width / checkCode.length, height);
		return checkCode;
	}
}
