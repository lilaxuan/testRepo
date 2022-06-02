package service.FileGenerators;


import CSVProcessor.CSVProcessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EmailLetterGeneratorAbstract is an abstract class that implements the EmailLetterGeneratorsI
 * interface, which implements all the required methods to generate email or letter templates.
 */
public abstract class EmailLetterGeneratorAbstract implements EmailLetterGeneratorsI{
    private static final String REGEX_PLACEHOLDER =  "\\[\\[(.+?)]]";
    private String template;
    private String templatePath;
    private List<String> allPlaceHolders = new ArrayList<>();
    private Map<String, Integer> headerInfoMap = new HashMap<>();
    private List<List<String>> allInfoList = new ArrayList<>();


    /**
     * Constructor
     *
     * @param templatePath - the given template path, expected as a string
     * @param csvFilePath - the given csv file path, expected as a string
     * @throws IOException - IOException will be thrown when the file cannot be read in
     */
    public EmailLetterGeneratorAbstract(String templatePath, String csvFilePath) throws IOException {
        this.templatePath = templatePath;
        this.readInCSV(csvFilePath); // load the headInfo map and the allInfoList
        this.readInTemplate(this.templatePath); // get the template content and save it into the template variable
        this.getAllPlaceHolders(); // update the placeHolders list
    }

    /**
     * Getter
     *
     * @return the template content of the email or letter, expected as a string
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Getter
     *
     * @return the template path of the email or letter, expected as a string
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * Getter
     *
     * @return the head info map for the csv file, expected as a hashmap
     */
    public Map<String, Integer> getHeaderInfoMap() {
        return headerInfoMap;
    }

    /**
     * Getter
     *
     * @return a list of list of string for the csv file, expected as a list
     */
    public List<List<String>> getAllInfoList() {
        return allInfoList;
    }

    /**
     * Getter
     *
     * @return a list of placeholders in the email or in the letter template, expected
     * as a list of strings.
     */
    public List<String> getPlaceHolders() {
        return this.allPlaceHolders;
    }

    /**
     * getAllPlaceHolders is a method to get all the placeholders in the email or in the letter template
     * and update the allPlaceHolders fields in the class.
     *
     */
    public void getAllPlaceHolders(){
        Pattern pattern = Pattern.compile(REGEX_PLACEHOLDER);
        Matcher matcher = pattern.matcher(this.template);
        while (matcher.find()){
            String placeHolder = matcher.group();
            this.allPlaceHolders.add(placeHolder);
        }
    }

    /**
     * readInCSV is a method to read in the csv file
     *
     * @param csvFilePath - the given path of the csv file, expected as a string.
     * @throws IOException - IOException will be thrown when the file cannot be read in
     */
    public void readInCSV(String csvFilePath) throws IOException {
        CSVProcessor csvProcessor = new CSVProcessor(csvFilePath);
        this.headerInfoMap = csvProcessor.getHeaderInfoMap();
        this.allInfoList = csvProcessor.getAllInfoList();
    }

    /**
     * readInTemplate is a method to read in the email or the letter template and
     * save the txt file into a template string.
     *
     * @param templatePath - the given path of the template file, expected as a string.
     * @throws IOException - IOException will be thrown when the file cannot be read in
     */
    public void readInTemplate(String templatePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(templatePath));
        StringBuilder sb = new StringBuilder();
        for (String line : lines){
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        this.template = sb.toString(); // read in the template txt file and save it as a string to this.template.
    }

    /**
     * generateOneFileForOneRow is a method to generate the email or letter for one user after
     * replacing all the placeholders.
     *
     * @param outputDir - the output dir of the generated files, expected as a string
     * @param eachRow - a list of string that contains each user's info.
     * @throws IOException - IOException will be thrown when the file cannot be written.
     */
    public void generateOneFileForOneRow(String outputDir, List<String> eachRow) throws IOException {
        String newFileContent = this.template;
        // Use the Regex pattern to find all the placeholders firstly,
        // Then loop over all the placeHolders and replace the placeHolder one by one in each row of the csv table.
        for (String placeHolder:this.allPlaceHolders){
            String placeHolderName = placeHolder.replace("[", "");
            placeHolderName = placeHolderName.replace("]", "");
            Integer firstColumnIndex = this.headerInfoMap.get(placeHolderName); // use the placeholder name to get its index and use it to retrieve the corresponding user's data
            String userInfoToReplacePlaceHolders = eachRow.get(firstColumnIndex);
            newFileContent = newFileContent.replace(placeHolder, userInfoToReplacePlaceHolders);
        }

        String fileName = this.generateFileName(outputDir, eachRow, this.headerInfoMap);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(newFileContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * generateAllFiles  is a method to generate the emails or letters for all the users after
     * replacing all the placeholders.
     *
     * @param outputDir - the output dir of the generated files, expected as a string
     * @throws IOException - IOException will be thrown when the file cannot be written.
     */
    public void generateAllFiles(String outputDir) throws IOException {
        System.out.print("the header info map: " + this.headerInfoMap);
        for (List<String> eachRow : this.allInfoList){
            this.generateOneFileForOneRow(outputDir, eachRow);
        }
    }

    /**
     * equals is a method to determine whether an object has equal fields to current object
     *
     * @param o - another object to be compared to current object
     * @return true if these two objects have the same fields in general.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailLetterGeneratorAbstract)) return false;
        EmailLetterGeneratorAbstract that = (EmailLetterGeneratorAbstract) o;
        return Objects.equals(getTemplate(), that.getTemplate()) && Objects.equals(allPlaceHolders, that.allPlaceHolders) && Objects.equals(getHeaderInfoMap(), that.getHeaderInfoMap()) && Objects.equals(getAllInfoList(), that.getAllInfoList());
    }

    /**
     * hashCode is a method to get the hashcode of an object
     *
     * @return the hashcode value of an object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTemplate(), allPlaceHolders, getHeaderInfoMap(), getAllInfoList());
    }

    /**
     * toString is a method to get the string representation of the current object
     *
     * @return the string that contains the info of the current object.
     */
    @Override
    public String toString() {
        return "EmailLetterGeneratorAbstract{" +
                "template='" + template + '\'' +
                ", allPlaceHolders=" + allPlaceHolders +
                ", headerInfoMap=" + headerInfoMap +
                ", allInfoList=" + allInfoList +
                '}';
    }
}

