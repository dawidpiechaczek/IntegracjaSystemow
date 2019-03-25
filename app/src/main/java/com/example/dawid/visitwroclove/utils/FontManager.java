package com.example.dawid.visitwroclove.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class FontManager {
    private static Hashtable<String, Typeface> cachedIcons = new Hashtable<>();
    private static final String path = "fonts/ionicons.ttf";

    public static Typeface getIcons(Context context) {
        Typeface icons = cachedIcons.get(path);
        if (icons == null) {
            icons = Typeface.createFromAsset(context.getAssets(), path);
            cachedIcons.put(path, icons);
        }
        return icons;
    }
}
