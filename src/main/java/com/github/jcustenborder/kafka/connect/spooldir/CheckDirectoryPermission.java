/**
 * Copyright © 2016 Jeremy Custenborder (jcustenborder@gmail.com)
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
package com.github.jcustenborder.kafka.connect.spooldir;

/**
 * This interface is used to check if directory path provided is 
 * accessible for read, write, delete etc.
 */
public interface CheckDirectoryPermission {
  
  /**
   * To check if directory provided exists and is writable by the user 
   * running kafka Connect.
   * @param key : vale of file path key from configuration.
   *              for example - input.path, finished.path, error.path etc
   * @param path : It is the Path to the directory to be checked.
   */
  void checkIfDirectoryIsAccessible(String key, String path);

}