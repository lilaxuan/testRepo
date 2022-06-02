package service.CMDParser;

import java.util.HashSet;
import java.util.Set;


/**
 * service.CMDInputValidator. CMDInputValidator is a class
 * that is used to check all the validness of the arguments from the user input.
 */
public class CMDInputValidator {
    public static final String EMAIL = "--email";
    public static final String EMAIL_TEMPLATE_COMMAND = "--email-template";
    public static final String LETTER = "--letter";
    public static final String LETTER_TEMPLATE_COMMAND = "--letter-template";
    public static final String OUTPUT_COMMAND = "--output-dir";
    public static final String CSV_COMMAND = "--csv-file";
    public static final Integer REQUIRED_COMMAND_STRING_LENGTH = 7;

    /**
     * checkLengthOfCMDInput is a method to check whether the command inputs' length
     * meets the requirement or not.
     *
     * @param args - a list of command lines, expected as a list os string.
     * @return true if the args meets the length requirement.
     */
    public Boolean checkLengthOfCMDInput(String[] args){
        // 传入的args会默认存入一个list中根据空格split开。
        // check the total length of the command lines input 是不是input file和outfile一定有，
        // //然后email和letter的commands必须要至少有一个!
        if (args.length < REQUIRED_COMMAND_STRING_LENGTH){
            System.out.println("The required command length is at least 7!");
            return false;
        }
        System.out.println("Length requirement meets");
        return true;
    }

    /**
     * checkDuplicateCMDInput is a method to check whether there are duplicate commands
     *
     * @param args - a list of command lines, expected as a list os string.
     * @return true if there is no duplicate commands.
     */
    public Boolean checkDuplicateCMDInput(String[] args){
        Set<String> uniqueCMD = new HashSet<String>();
        for (String cmd:args){
            if (uniqueCMD.contains(cmd))
            {
                System.out.println("You have duplicate commands");
                return false;
            }
            else{
                uniqueCMD.add(cmd);
            }
        }
        System.out.println("You don't have duplicate commands");
        return true;
    }

    /**
     * checkOutputInputCMD is a method to check whether the input and output
     * commands both exists, and both of them has a directory followed by.
     *
     * @param args - a list of command lines, expected as a list os string.
     * @return true if both input and output commands are valid.
     */
    public Boolean checkOutputInputCMD(String[] args){
        Boolean inputCMDValid = false;
        Boolean outputCMDValid = false;

        for (int i = 0; i < args.length; i++){
            if (args[i].equals(CSV_COMMAND) && i+1 < args.length){
                System.out.println("The input command and dir is valid");
                inputCMDValid = true;
            }
            if (args[i].equals(OUTPUT_COMMAND) && i+1 < args.length){
                System.out.println("The output command and dir is valid");
                outputCMDValid = true;
            }
        }
        return outputCMDValid && inputCMDValid;

    }

    /**
     * checkEmailGroup is a method to check whether both email template command and the corresponding
     * dir exists.
     *
     * @param args - a list of command lines, expected as a list os string.
     * @return true if both the email template command and the dir to the email template both exist.
     */
    public Boolean checkEmailGroup(String[] args){
        return checkEmailLetterGroup(args, EMAIL, EMAIL_TEMPLATE_COMMAND);
    }

    /**
     * checkLetterGroup is a method to check whether both letter template command and the corresponding
     * dir exists.
     *
     * @param args - a list of command lines, expected as a list os string.
     * @return true if both the letter template command and the dir to the letter template both exist.
     */
    public Boolean checkLetterGroup(String[] args){
        return checkEmailLetterGroup(args, LETTER, LETTER_TEMPLATE_COMMAND);
    }

    /**
     * checkEmailLetterGroup is a method to check whether the email and letter commands are valid
     *
     * @param args - a list of command lines, expected as a list os string.
     * @param cmd - the given command, either is email or letter command
     * @param templateCommand -the given template command, either is email or letter template command, which
     *                        needs a directory to be linked.
     * @return
     */
    private Boolean checkEmailLetterGroup(String[] args, String cmd, String templateCommand) {
        Boolean cmdExist = false;
        Boolean templateExist = false;
        for (int i = 0; i < args.length; i++){
            if (args[i].equals(cmd)){
                cmdExist = true;
                System.out.println("Email/letter command is valid");
            }
            if (args[i].equals(templateCommand) && i+1 < args.length){
                templateExist = true;
                System.out.println("Email/letter template path is valid");
            }
        }
        if (! (cmdExist && templateExist)){
            System.out.println("Error: Email/Letter command must co-exists with the template path.");
        }
        return cmdExist && templateExist;
    }

    /**
     * checkValidCMDInput is a method to check whether the command lines input from the user is valid
     * in general.
     *
     * @param args - a list of command lines, expected as a list os string.
     * @return true if all the command lines are valid.
     */
    // input and output commands are must; email and letter commands are optional (but at least have one)
    public Boolean checkValidCMDInput(String[] args){
        if (!this.checkLengthOfCMDInput(args) || !this.checkDuplicateCMDInput(args) || !this.checkOutputInputCMD(args) || (!this.checkEmailGroup(args) && !this.checkLetterGroup(args))){
            System.out.println("The command lines are not valid in general!");
            return false;
        }
        System.out.println("The command lines are valid in general!");
        return true;
    }
}

