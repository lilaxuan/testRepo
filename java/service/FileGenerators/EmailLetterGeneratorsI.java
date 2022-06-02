package service.FileGenerators;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * EmailLetterGeneratorsI is the interface to generate email or letter files based on the
 * given info in the csv table.
 */
public interface EmailLetterGeneratorsI {
    void readInCSV(String csvFilePath) throws IOException;
    void readInTemplate(String templatePath) throws IOException;
    void getAllPlaceHolders();
    String generateFileName(String outputDir, List<String> eachRow, Map<String, Integer> headerInfoMap);
    void generateOneFileForOneRow(String outputDir, List<String> eachRow) throws IOException;
    void generateAllFiles(String outputDir) throws IOException;
}