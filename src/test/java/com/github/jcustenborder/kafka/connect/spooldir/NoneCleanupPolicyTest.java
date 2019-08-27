package com.github.jcustenborder.kafka.connect.spooldir;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoneCleanupPolicyTest extends AbstractCleanUpPolicyTest<CleanUpPolicy.None> {
  @Override
  protected CleanUpPolicy.None create(File inputFile, File errorPath, File finishedPath) {
    return new CleanUpPolicy.None(inputFile, errorPath, finishedPath);
  }

  @Test
  public void success() throws IOException {
    assertTrue(this.inputFile.exists(), "Input file should exist");
    this.cleanupPolicy.success();
    assertTrue(this.inputFile.exists(), "Input file should exist");
  }
}
