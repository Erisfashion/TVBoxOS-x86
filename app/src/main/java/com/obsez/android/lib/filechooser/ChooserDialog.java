package com.obsez.android.lib.filechooser;

import android.app.Activity;
import java.io.File;

public class ChooserDialog {
    public ChooserDialog(Activity activity) {}
    public ChooserDialog title(CharSequence title) { return this; }
    public ChooserDialog withStartFile(String path) { return this; }
    public ChooserDialog withFilter(boolean allowDir, boolean allowFiles, String... extensions) { return this; }
    public ChooserDialog into(Result callback) { return this; }
    public ChooserDialog withChosenListener(Result callback) { return this; }
    public ChooserDialog build() { return this; }
    public void show() {}

    public interface Result {
        void onChoosePath(String path, File pathFile);
    }
}
