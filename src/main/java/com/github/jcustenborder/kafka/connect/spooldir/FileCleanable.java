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

import java.io.Closeable;
import java.io.IOException;

public interface FileCleanable extends Closeable {
  
  /**
   * This method create a policy and further call the method required to finish the task
   * @param config
   * @param inputFile
   * @return
   * @throws IOException
   */
  FileCleanable create(AbstractSourceConnectorConfig config, FileReadable inputFile) throws IOException;
 
  /**
   * This method used to perform action for successful execution of policy
   * @throws IOException
   */
  void success() throws IOException;

  /**
   * Method is used to handle file cleanup when processing the file has error.
   */
  public void error();
 
}
