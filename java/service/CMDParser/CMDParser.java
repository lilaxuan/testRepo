package service.CMDParser;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * CMDParser is a method to parser the command lines and get corresponding path of the command lines
 */
public class CMDParser{
    private String cmd;

    /**
     * Constructor
     *
     * @param cmd - the given command, expected as a string.
     */
    public CMDParser(String cmd) {
        this.cmd = cmd;
    }

    /**
     * Getter
     *
     * @return the command that passed.
     */
    public String getCmd() {
        return cmd;
    }


    /**
     * validatePath is a method to validate the path given
     *
     * @param filePath - the given file path, expected as a string
     * @return true if the filePath is valid
     */
    public Boolean validatePath(String filePath){
        try {
            Paths.get(filePath);
        } catch (NullPointerException ex) {
            return false;
        }
        return true;
    }

    /**
     * getFilePath is a method to get the file path of the command.
     *
     * @param commands - a list of command lines, expected as a list of string
     * @return the filePath of some commands if the filepath is a necessary argument
     */
    public String getFilePath(String[] commands) {
        String filePath = null;
        for (int i = 0; i < commands.length; i++){
            if (commands[i] == this.cmd && i+1 < commands.length){
                filePath = commands[i+1];
            }
        }
        return filePath;
    }

}

