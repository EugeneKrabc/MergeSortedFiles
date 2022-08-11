package CmdArguments;
import java.util.ArrayList;


public final class CmdArguments {
    public CmdArguments(String[] args) throws Exception{
        this.args = args;
        parseCmdArgs();
    }

    public String getOutputFileName() {
        return outputFile;
    }

    public ArrayList<String> getInputFileNames() {
        return inputFiles;
    }

    private enum DataType {
        INTEGERS,
        STRINGS
    }
    private enum SortType {
        ASCENDING,
        DESCENDING
    }

    private String[] args;
    private boolean haveOptionalArgument;
    private SortType sortType = null;
    private DataType dataType = null;
    private String outputFile;
    private ArrayList<String> inputFiles = new ArrayList<>();

    private void parseCmdArgs() throws Exception {
        if (args.length < 3)
            throw new Exception("Wrong number of arguments");

        getDataType(args);
        getSortType(args);

        if (dataType == null) {
            throw new Exception("Wrong arguments: no data type specified( -i or -s)");
        }

        if (sortType == null) {
            sortType = SortType.ASCENDING;
            haveOptionalArgument = false;
        } else {
            haveOptionalArgument = true;
        }

        int outputFileIndex;
        if (haveOptionalArgument)
            outputFileIndex = 2;
        else
            outputFileIndex = 1;
        outputFile = new String(args[outputFileIndex]);

        for (int i = outputFileIndex + 1; i < args.length; i++)
            inputFiles.add(args[i]);
    }

    private void getDataType(String[] args) throws Exception {
        if (args[0].equals(args[1]))
            throw new Exception("Wrong arguments: identical flags are not allowed");
        
        if (args[0].equals("-s") || args[1].equals("-s")) {
            if (dataType == DataType.INTEGERS)
                throw new Exception("Wrong date type: you should pass '-s' or '-i', not both");
            
            dataType = DataType.STRINGS;
        }
        
        if (args[0].equals("-i") || args[1].equals("-i")) {
            if (dataType == DataType.STRINGS)
                throw new Exception("Wrong date type: you should pass '-s' or '-i', not both");
            
            dataType = DataType.INTEGERS;
        }
    }

    private void getSortType(String[] args) throws Exception {
        if (args[0].equals("-a") || args[1].equals("-a")) {
            if (sortType == SortType.DESCENDING)
                throw new Exception("Wrong sort type: you should pass '-a' or '-d', not both");

            sortType = SortType.ASCENDING;
        }

        if (args[0].equals("-d") || args[1].equals("-d")) {
            if (sortType == SortType.ASCENDING)
                throw new Exception("Wrong sort type: you should pass '-a' or '-d', not both");
        
            sortType = SortType.DESCENDING;
        }
    }
}
