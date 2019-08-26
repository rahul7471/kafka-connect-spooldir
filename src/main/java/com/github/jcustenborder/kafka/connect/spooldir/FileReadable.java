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
import java.io.InputStream;

public interface FileReadable extends Closeable {
  
  /*
   * The input file can be in any format, a file, path to a file or
   * path to an external service like HDFS or SFTP etc.
   * */

  /**
   * Convert a given file into input stream and return it and meanwhile set it into a global variable
   * @return
   * @throws IOException
   */
  InputStream openStream() throws IOException;
   
  /**
   * close all streams and connections
   */
  void close() throws IOException;
  
  /**
   * return last modified status of a file
   * @return
   * @throws IOException
   */
  long lastModified() throws IOException;
  
  /**
   * return name of given file
   * @return
   * @throws IOException
   */
  String getName() throws IOException;
  
  /**
   * getter for inputstream variable, should always be called after openStream and 
   * OpenStream should wor as setter for global variable.
   * @return
   */
  InputStream getInputStream();
   
}
