/*
 * Copyright 2018 Eugeny Zrazhevsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.benchdoos.weblocopener.commons.utils.system;

/**
 * Created by Eugene Zrazhevsky on 03.12.2016.
 */
public class SystemException extends Exception {

    public SystemException() {
        super("[SystemException]");
    }

    public SystemException(String message) {
        super("[SystemException] " + message);
    }

    public SystemException(String message, Throwable cause) {
        super("[SystemException] " + message, cause);
    }
}