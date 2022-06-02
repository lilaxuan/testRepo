package Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CMDInputValidatorTest {
  String[] testCMDs1;
  @BeforeEach
  void setUp() {
  }

  @Test
  void testCheckLengthOfCMDInput() {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--display"};
    assertTrue(new CMDInputValidator(testCMDs1).checkLengthOfCMDInput());

    testCMDs1 = new String[]{"--csv-file", "src/todos.csv"};
    assertFalse(new CMDInputValidator(testCMDs1).checkLengthOfCMDInput());
  }

  @Test
  void testCheckInputCMD() {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--display"};
    assertTrue(new CMDInputValidator(testCMDs1).checkInputCMD());

    testCMDs1 = new String[]{"--csv-file", "src/invalid.csv", "--display"};
    assertFalse(new CMDInputValidator(testCMDs1).checkInputCMD());
  }

  @Test
  void testCompleteToDo() {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--complete-todo 5", "--complete-todo 1"};
    assertTrue(new CMDInputValidator(testCMDs1).checkCompleteToDoCMD());
  }

  @Test
  void testCheckCompletedCMD() {

  }
}