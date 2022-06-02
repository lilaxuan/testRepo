package CSVProcessor;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * CSVProcessor is a class to help to read in the csv file, extract the user's information
 * and save the information in a list of list of string.
 */
public class CSVProcessor {

    private static String DELIMITER = "\"*,*\"";
    private Map<String, Integer> headerInfoMap = new HashMap<>(); // key: headinfo; value:the index of the header.
    private List<List<String>> allInfoList = new ArrayList<>();
    private String csvFilePath;

    /**
     * Constructor
     *
     * @param csvFilePath - the file path of the csv file, expected as a String
     * @throws IOException
     */
    public CSVProcessor(String csvFilePath) throws IOException {
        this.csvFilePath  = csvFilePath;
        this.readInCSV(this.csvFilePath);
    }

    /**
     * Getter
     *
     * @return the file path of the csv file, expected as a String
     */
    public String getCsvFilePath() {
        return csvFilePath;
    }

    /**
     * Getter
     *
     * @return the header info map of the csv file, expected as a Map, which stores the key as
     * the first column name (string), and the index of it(integer).
     */
    public Map<String, Integer> getHeaderInfoMap() {
        return headerInfoMap;
    }

    /**
     * Getter
     *
     * @return the all info list csv file, expected as a list of list of string.
     */
    public List<List<String>> getAllInfoList() {
        return allInfoList;
    }

    /**
     * readInCSV is a method to read in the csv file
     *
     * @param csvFilePath -the file path of the csv file, expected as a String
     * @throws IOException : the IOException is thrown when the file cannot be read in.
     */
    public void readInCSV(String csvFilePath) throws IOException {
        // save the first row in the headerInfoMap
        // save per user for a list of string, and save all the user's information in a list of list of string.
        BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
        String[] firstRowNames = br.readLine().split(DELIMITER); // read in the first row and save it in a list of string
        int index = 0;
        for (int i = 1; i < firstRowNames.length; i++) {
            headerInfoMap.put(firstRowNames[i].replace("\"", ""), index++); // add the header info and their index into the map after removing the unwanted double quotes""
        }


        String line;
        while ((line = br.readLine()) != null) {
            String[] info = line.split(DELIMITER);
            List<String> eachRow = new ArrayList<>();
            for (int i = 1; i < info.length; i++) {
                eachRow.add(info[i].replace("\"", ""));
            }
            allInfoList.add(eachRow);
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
        if (!(o instanceof CSVProcessor)) return false;
        CSVProcessor that = (CSVProcessor) o;
        return Objects.equals(getHeaderInfoMap(), that.getHeaderInfoMap()) && Objects.equals(getAllInfoList(), that.getAllInfoList()) && Objects.equals(getCsvFilePath(), that.getCsvFilePath());
    }

    /**
     * hashCode is a method to get the hashcode of an object
     *
     * @return the hashcode value of an object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getHeaderInfoMap(), getAllInfoList(), getCsvFilePath());
    }

    /**
     * toString is a method to get the string representation of the current object
     *
     * @return the string that contains the info of the current object.
     */
    @Override
    public String toString() {
        return "CSVProcessor{" +
                "headerInfoMap=" + headerInfoMap +
                ", allInfoList=" + allInfoList +
                ", csvFilePath='" + csvFilePath + '\'' +
                '}';
    }
}
