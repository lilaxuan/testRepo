package service.CMDParser;

import service.FileGenerators.EmailGenerator;
import service.FileGenerators.LetterGenerator;

import java.io.IOException;

/**
 *CMDMainProcessor is a class that mainly process the user's input command lines, and
 * generate all the files according to the given csv file, the output dir, and the templates.
 */
public class CMDMainProcessor {
    public static final String EMAIL = "--email";
    public static final String EMAIL_TEMPLATE_COMMAND = "--email-template";
    public static final String LETTER = "--letter";
    public static final String LETTER_TEMPLATE_COMMAND = "--letter-template";
    public static final String OUTPUT_COMMAND = "--output-dir";
    public static final String CSV_COMMAND = "--csv-file";
    private String[] args;

    /**
     * Constructor
     *
     * @param args - a list of command line arguments, expected as a list of string
     */
    public CMDMainProcessor(String[] args) {
        this.args = args;
    }

    /**
     * cmdProcessor is the method to process all the command line inputs from
     * the user, and process them accordingly.
     *
     * @return true if either email or letter files can be generated.
     * @throws IOException - IOException will be thrown when the reading file process is problematic.
     */
    public Boolean cmdProcessor() throws IOException {
        String csvFilePath = null;
        String outputDir = null;
        String emailTemplatePath = null;
        String letterTemplatePath = null;
        Boolean generateEmails = false;
        Boolean generateLetters = false;
        Boolean flag = false;

        CMDInputValidator cmdInputValidator = new CMDInputValidator();
        if (cmdInputValidator.checkValidCMDInput(this.args)){
            for (String cmd:args){
                CMDParser cmdParser = new CMDParser(cmd);
                switch (cmd){
                    case EMAIL:
                    case LETTER:
                        break;
                    case EMAIL_TEMPLATE_COMMAND:
                        if (cmdParser.validatePath(cmdParser.getFilePath(args)))
                        {   generateEmails = true;
                            emailTemplatePath = cmdParser.getFilePath(args);
                        }
                        break;
                    case LETTER_TEMPLATE_COMMAND:
                        if (cmdParser.validatePath(cmdParser.getFilePath(args)))
                        {   generateLetters = true;
                            letterTemplatePath = cmdParser.getFilePath(args);
                        }
                        break;
                    case CSV_COMMAND:
                        if (cmdParser.validatePath(cmdParser.getFilePath(args))) {
                            csvFilePath = cmdParser.getFilePath(args);
                        }
                        break;
                    case OUTPUT_COMMAND:
                        if (cmdParser.validatePath(cmdParser.getFilePath(args)))
                            outputDir = cmdParser.getFilePath(args);
                        break;
                    default:
                        System.out.println("Invalid command or it's a directory!");
                }

            }
            flag = true;

        }

        if (generateLetters && letterTemplatePath!=null && outputDir!=null){
            LetterGenerator letterGenerator = new LetterGenerator(letterTemplatePath, csvFilePath);
            letterGenerator.generateAllFiles(outputDir);
        }

        if (generateEmails && emailTemplatePath!=null && outputDir!=null){
            EmailGenerator emailGenerator = new EmailGenerator(emailTemplatePath, csvFilePath);
            emailGenerator.generateAllFiles(outputDir);
        }
        return flag;
    }
}
