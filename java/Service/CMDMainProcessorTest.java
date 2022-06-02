package Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CMDMainProcessorTest {
  String[] testCMDs1;

  @BeforeEach
  void setUp() {
  }

  @Test
  void testReadCSVFile() throws Exception {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--display"};
    assertTrue(new CMDMainProcessor(testCMDs1).cmdProcessor());
  }

  @Test
  void testFailReadCSVFile() throws Exception {
    testCMDs1 = new String[]{"--csv-file", "--display", "--display"};
    assertFalse(new CMDMainProcessor(testCMDs1).cmdProcessor());
  }

  @Test
  void testDisplay() throws Exception {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--display"};
    assertTrue(new CMDMainProcessor(testCMDs1).cmdProcessor());
  }

  @Test
  void testShowIncomplete() throws Exception {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--show-incomplete"};
    assertTrue(new CMDMainProcessor(testCMDs1).cmdProcessor());
  }

  @Test
  void testSortByDate() throws Exception {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--sort-by-date"};
    assertTrue(new CMDMainProcessor(testCMDs1).cmdProcessor());
  }

  @Test
  void testSortByPriority() throws Exception {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--sort-by-priority"};
    assertTrue(new CMDMainProcessor(testCMDs1).cmdProcessor());
  }

  @Test
  void testFailAddToDo() throws Exception {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--add-todo", "--todo-text newToDo",
                             "--priority 3", "--category school", "--due 11/11/2022"};
    assertFalse(new CMDMainProcessor(testCMDs1).cmdProcessor());

  }

  @Test
  void testCompleteToDo() throws Exception {
    testCMDs1 = new String[]{"--csv-file", "src/todos.csv", "--complete-todo 5", "--complete-todo 1"};
    assertTrue(new CMDMainProcessor(testCMDs1).cmdProcessor());
  }

}