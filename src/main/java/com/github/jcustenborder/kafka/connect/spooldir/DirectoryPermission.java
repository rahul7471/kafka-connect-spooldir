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

import com.google.common.base.Preconditions;
import org.apache.kafka.connect.errors.ConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DirectoryPermission implements CheckDirectoryPermission {
  
  private static final Logger log = LoggerFactory.getLogger(DirectoryPermission.class);
  
  @Override
  public void checkIfDirectoryIsAccessible(String key, String path) {

    log.info("Checking if directory {} '{}' exists.", key, path);

    String errorMessage = String.format("Directory for '%s' '%s' does not exist ", key, path);

    File directoryPath = new File(path);

    if (!directoryPath.isDirectory()) {
      throw new ConnectException(
              errorMessage,
              new FileNotFoundException(directoryPath.getAbsolutePath())
      );
    }

    log.info("Checking to ensure {} '{}' is writable ", key, directoryPath);

    errorMessage = String.format("Directory for '%s' '%s' it not writable.", key, directoryPath);

    File temporaryFile = null;

    try {
      temporaryFile = File.createTempFile(".permission", ".testing", directoryPath);
    } catch (IOException ex) {
      throw new ConnectException(errorMessage, ex);
    } finally {
      try {
        if (null != temporaryFile && temporaryFile.exists()) {
          Preconditions.checkState(temporaryFile.delete(), "Unable to delete temp file in %s", directoryPath);
        }
      } catch (Exception ex) {
        log.warn("Exception thrown while deleting {}.", temporaryFile, ex);
      }
    }
  }
  
}