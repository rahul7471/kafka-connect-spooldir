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

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

class CleanUpPolicy implements FileCleanable {
  private static final Logger log = LoggerFactory.getLogger(CleanUpPolicy.class);
  protected final File inputFile;
  protected final File errorPath;
  protected final File finishedPath;


  protected CleanUpPolicy(File inputFile, File errorPath, File finishedPath) {
    this.inputFile = inputFile;
    this.errorPath = errorPath;
    this.finishedPath = finishedPath;
  }
  
  // TODO : check if this needed.
  public CleanUpPolicy() {
    this.inputFile = null;
    this.errorPath = null;
    this.finishedPath = null;
  }
  
  @Override
  public CleanUpPolicy create(AbstractSourceConnectorConfig config, 
      FileReadable inputReadableFile) throws IOException {
    final CleanUpPolicy result;
    InputFile inputFile = (InputFile) inputReadableFile;
    switch (config.cleanupPolicy) {
      case MOVE:
        result = new Move(inputFile.getInputFile(), config.errorPath, config.finishedPath);
        break;
      case DELETE:
        result = new Delete(inputFile.getInputFile(), config.errorPath, config.finishedPath);
        break;
      case NONE:
        result = new None(inputFile.getInputFile(), config.errorPath, config.finishedPath);
        break;
      default:
        throw new UnsupportedOperationException(
            String.format("%s is not supported", config.cleanupPolicy)
        );
    }

    return result;
  }

  protected void removeFile(File file) {
    log.info("Removing {}", file);
    if (!file.delete()) {
      log.warn("Could not delete {}", file);
    }
  }

  protected void moveToDirectory(File outputDirectory) {
    File outputFile = new File(outputDirectory, this.inputFile.getName());
    try {
      if (this.inputFile.exists()) {
        log.info("Moving {} to {}", this.inputFile, outputFile);
        Files.move(this.inputFile, outputFile);
      }
    } catch (IOException e) {
      log.error("Exception thrown while trying to move {} to {}", this.inputFile, outputFile, e);
    }
  }

  @Override
  public void close() throws IOException {

  }

  @Override
  public void error() {
    log.error(
        "Error during processing, moving {} to {}.",
        this.inputFile,
        this.errorPath
    );
    moveToDirectory(this.errorPath);
  }

  /**
   * Method is used to handle file cleanup when processing the file was successful.
   */
  @Override
  public void success() throws IOException {};

  static class Move extends CleanUpPolicy {
    protected Move(File inputFile, File errorPath, File finishedPath) {
      super(inputFile, errorPath, finishedPath);
    }

    @Override
    public void success() throws IOException {
      moveToDirectory(this.finishedPath);
    }
  }

  static class Delete extends CleanUpPolicy {
    protected Delete(File inputFile, File errorPath, File finishedPath) {
      super(inputFile, errorPath, finishedPath);
    }

    @Override
    public void success() throws IOException {
      removeFile(this.inputFile);
    }
  }

  static class None extends CleanUpPolicy {
    protected None(File inputFile, File errorPath, File finishedPath) {
      super(inputFile, errorPath, finishedPath);
    }

    @Override
    public void success() throws IOException {
      log.trace("Leaving {}", this.inputFile);
    }
  }
}
