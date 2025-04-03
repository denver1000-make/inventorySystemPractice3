package com.denprog.praticeapp3inventorysystem.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    public static final String profileImagesPath = "profilePics";
    public static final String userProfileFolderAppend = "_prof";
    public static Bitmap convertUriToBitmap(Uri uri, Context context) throws FileNotFoundException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(inputStream);
    }

    public static String insertProfilePicture(Bitmap bitmap, Context context, String actionSpecificFolder, String personalFolder, String fileName) throws IOException {
        File actionSpecificFolderObj = new File(context.getFilesDir(), actionSpecificFolder);
        actionSpecificFolderObj.mkdir();
        File personalFolderObj = new File(actionSpecificFolderObj, personalFolder);
        personalFolderObj.mkdir();
        File actualFileObj = new File(personalFolderObj, fileName);
        actualFileObj.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(actualFileObj);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);

        return actionSpecificFolder + File.separator + personalFolder + File.separator + fileName;
    }

    public static String generateRandomStr(int length) {

        String letters = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
        StringBuilder strToReturn = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.ceil(Math.random() * letters.length()));
            strToReturn.append(letters.charAt(randomIndex));
        }
        return strToReturn.toString();
    }

    public static Bitmap getBitmapFromPath(String path, Context context) throws FileNotFoundException {
        File savedProfile = new File(context.getFilesDir(), path);
        return BitmapFactory.decodeStream(new FileInputStream(savedProfile));
    }
}
