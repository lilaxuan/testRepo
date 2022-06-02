
import service.CMDParser.CMDMainProcessor;

import java.io.IOException;


/**
 * Main is the class that drives the code which can takes the user's input of command
 * lines, and validate them, then generate all the emails and letters based on the csv
 * file provided.
 */
public class Main {
    // Example input for run configuration:
    // --email --email-template src/email-template.txt --letter --letter-template src/letter-template.txt --output-dir src/GeneratedFiles --csv-file src/insurance-company-members.csv
    public static void main(String[] args) throws IOException {
        System.out.println("Usage:\n" +
                "--email Generate email messages. If this option is provided, then -- email-template must also be provided.\n" +
                "--email-template <path/to/file> A filename for the email template. " +
                "--letter Generate letters. If this option is provided, then --letter- template must also be provided.\n" +
                "--letter-template <path/to/file> A filename for the letter template. " +
                "--output-dir <path/to/folder> The folder to store all generated files. This option is required.\n" +
                "--csv-file <path/to/folder> The CSV file to process. This option is required.");
        CMDMainProcessor cmdMainProcessor = new CMDMainProcessor(args);
        cmdMainProcessor.cmdProcessor();
    }


}



