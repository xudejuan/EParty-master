package com.example.liu.eparty.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 */

public class FileUtil {

    public static List<String> getSpecificTypeOfFile(Context context) {
        List<String> list = new ArrayList<>();
        Uri fileUri = MediaStore.Files.getContentUri("external");
        String[] projection = new String[]{
                MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE
        };
        StringBuilder selection = new StringBuilder();
        String[] extension = new String[]{".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", "pdf", ".rar", ".zip"};
        for (int i = 0; i < extension.length; i++) {
            if (i != 0) {
                selection.append(" OR ");
            }
            selection.append(MediaStore.Files.FileColumns.DATA + " LIKE '%").append(extension[i]).append("'");
        }
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(fileUri, projection, selection.toString(), null, sortOrder);
        if (cursor == null)
            return null;
        if (cursor.moveToLast()) {
            do {
                String path = cursor.getString(0);
                list.add(path);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return list;
    }

    public static String getSavePath(Context context){
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Android/data/" + context.getPackageName();
    }

    public static String getFileName(String filePath){
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    public static boolean isExist(Context context, String fileName){
        return new File(getSavePath(context) + "/" + fileName).exists();
    }

    private static final String[][] MIME_MapTable =
            {
                    {".doc", "application/msword"},
                    {".docx", "application/msword"},
                    {".pdf", "application/pdf"},
                    {".ppt", "application/vnd.ms-powerpoint"},
                    {".pptx", "application/vnd.ms-powerpoint"},
                    {".rar", "application/x-rar-compressed"},
                    {".xlsx", "application/vnd.ms-excel"},
                    {".xls", "application/vnd.ms-excel"},
                    {".zip", "application/zip"},
            };

    private static String getMIMEType(String filePath) {
        String type = "*/*";
        int dotIndex = filePath.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        String end = filePath.substring(dotIndex, filePath.length()).toLowerCase();
        if (end.equals("")) {
            return type;
        }
        for (String[] aMIME_MapTable : MIME_MapTable) {
            if (end.equals(aMIME_MapTable[0])) {
                type = aMIME_MapTable[1];
            }
        }
        return type;
    }

    public static void show(Activity activity, String filesPath){
        Uri uri = Uri.parse("file://" + filesPath);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = getMIMEType(filesPath);
        intent.setDataAndType(uri, type);
        if (!type.equals("*/*")) {
            try {
                activity.startActivity(intent);
            } catch (Exception e) {
                activity.startActivity(showOpenTypeDialog(filesPath));
            }
        } else {
            activity.startActivity(showOpenTypeDialog(filesPath));
        }
    }

    private static Intent showOpenTypeDialog(String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }
}
