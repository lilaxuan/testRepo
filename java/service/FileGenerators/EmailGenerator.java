package service.FileGenerators;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * EmailGenerator is a class that implements the EmailLetterGeneratorsI
 * interface and extends the EmailLetterGeneratorAbstract class, which replace all the placeholders with the users' information and generate
 * corresponding emails.
 */
public class EmailGenerator extends EmailLetterGeneratorAbstract implements EmailLetterGeneratorsI {
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";

    /**
     * Constructor
     *
     * @param templatePath - the given path of the email template, expected as a string
     * @param csvFilePath - the given path of the csv file, expected as a string
     * @throws IOException - IOException will be thrown when the file cannot be read in.
     */
    public EmailGenerator(String templatePath, String csvFilePath) throws IOException {
        super(templatePath, csvFilePath);
    }

    /**
     * generateFileName is a method to generate the file names of the emails
     * based on the user's first name and last name
     *
     * @param outputDir - the output dir of the generated emails where they can be saved, expected as a string
     * @param eachRow - a list of string that contains each user's info.
     * @param headerInfoMap - header info map of the csv file, expected as a Map, which stores the key as
     *        the first column name (string), and the index of it(integer).
     * @return the file name of the generated emails
     */
    @Override
    public String generateFileName(String outputDir, List<String> eachRow, Map<String, Integer> headerInfoMap) {
        //  add a "/" to the outputDir if it doesn't contain one
        if (!outputDir.endsWith(File.separator)){
            outputDir = outputDir + File.separator;
        }
        // save the fileNames in the designated output dir.
        String fileName = outputDir + eachRow.get(headerInfoMap.get(FIRST_NAME)) + "_" + eachRow.get(headerInfoMap.get(LAST_NAME)) + "_email" + ".txt";
        return fileName;
    }
}
