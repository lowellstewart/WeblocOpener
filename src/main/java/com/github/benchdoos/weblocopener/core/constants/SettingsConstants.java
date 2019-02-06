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

package com.github.benchdoos.weblocopener.core.constants;

import com.github.benchdoos.weblocopener.preferences.PreferencesManager;

public interface SettingsConstants {
    boolean IS_APP_AUTO_UPDATE_DEFAULT_VALUE = true;
    boolean OPEN_FOLDER_FOR_QR_CODE = true;
    boolean SHOW_NOTIFICATIONS_TO_USER = true;
    String BROWSER_DEFAULT_VALUE = "default";
    PreferencesManager.DARK_MODE DARK_MODE_DEFAULT_VALUE = PreferencesManager.DARK_MODE.DISABLED;
}
