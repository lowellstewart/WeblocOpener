/*
 * (C) Copyright 2019.  Eugene Zrazhevsky and others.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Contributors:
 * Eugene Zrazhevsky <eugene.zrazhevsky@gmail.com>
 */

package com.github.benchdoos.weblocopener.preferences;

import com.github.benchdoos.weblocopener.core.constants.ApplicationConstants;
import com.github.benchdoos.weblocopener.core.constants.SettingsConstants;
import com.github.benchdoos.weblocopener.service.gui.darkMode.DarkModeAnalyzer;

import java.util.prefs.Preferences;

/**
 * Created by Eugene Zrazhevsky on 19.11.2016.
 */
public class PreferencesManager {
    public static final String KEY_AUTO_UPDATE = "auto_update_enabled";
    private static final String KEY_OPEN_FOR_QR = "open_folder_for_qr";
    private static final String KEY_BROWSER = "browser";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String DEV_MODE_KEY = "dev_mode";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_DARK_MODE_DOWN_TIME = "dark_mode_down_time";
    private static final String KEY_DARK_MODE_UPPER_TIME = "dark_mode_upper_time";
    private static final Preferences PREFERENCES = Preferences.userRoot().node(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME.toLowerCase());

    public static String getBrowserValue() {
        final String value = PREFERENCES.get(KEY_BROWSER, SettingsConstants.BROWSER_DEFAULT_VALUE);
        if (value.isEmpty()) {
            return SettingsConstants.BROWSER_DEFAULT_VALUE;
        }
        return value;
    }

    public static void setBrowserValue(String callPath) {
        if (!callPath.isEmpty()) {
            PREFERENCES.put(KEY_BROWSER, callPath);
        }
    }

    private static String getDarkMode() {
        final String s = PREFERENCES.get(KEY_DARK_MODE, SettingsConstants.DARK_MODE_DEFAULT_VALUE.toString());
        if (s.equalsIgnoreCase(DARK_MODE.ALWAYS.toString())) {
            return DARK_MODE.ALWAYS.toString();
        } else if (s.equalsIgnoreCase(DARK_MODE.DISABLED.toString())) {
            return DARK_MODE.DISABLED.toString();
        } else {
            return s;
        }
    }

    public static void setDarkMode(DARK_MODE mode) {
        PREFERENCES.put(KEY_DARK_MODE, mode.toString().toUpperCase());
    }

    public static boolean isAutoUpdateActive() {
        return PREFERENCES.getBoolean(KEY_AUTO_UPDATE, SettingsConstants.IS_APP_AUTO_UPDATE_DEFAULT_VALUE);
    }

    public static void setAutoUpdateActive(boolean autoUpdateActive) {
        PREFERENCES.putBoolean(KEY_AUTO_UPDATE, autoUpdateActive);
    }

    public static boolean isDarkModeEnabledNow() {
        switch (getDarkMode()) {
            case "ALWAYS":
                return true;
            case "DISABLED":
                return false;
            default: {
                //parse value, if location - get time
                //if time - return time
                //loads api blah blah
                return DarkModeAnalyzer.isDarkModeEnabledByNotDefaultData(getDarkMode());
//                return SettingsConstants.DARK_MODE_DEFAULT_VALUE == DARK_MODE.ALWAYS;
            }
        }
    }

    public static boolean isDevMode() {
        return PREFERENCES.getBoolean(DEV_MODE_KEY, false);
    }

    public static boolean isNotificationsShown() {
        return PREFERENCES.getBoolean(KEY_NOTIFICATIONS, SettingsConstants.SHOW_NOTIFICATIONS_TO_USER);
    }

    public static void setNotificationsShown(boolean showNotifications) {
        PREFERENCES.putBoolean(KEY_NOTIFICATIONS, showNotifications);
    }

    public static boolean openFolderForQrCode() {
        return PREFERENCES.getBoolean(KEY_OPEN_FOR_QR, SettingsConstants.OPEN_FOLDER_FOR_QR_CODE);
    }

    public static void setOpenFolderForQrCode(boolean openFolderForQrCode) {
        PREFERENCES.putBoolean(KEY_OPEN_FOR_QR, openFolderForQrCode);
    }

    public enum DARK_MODE {ALWAYS, DISABLED}
}

