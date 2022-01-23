package kr.revelope.study.refactoring.parsers;

import kr.revelope.study.refactoring.types.ArgumentType;
import org.apache.commons.cli.*;

public class ProgramArgumentParser {
    private static final Options options = new Options();
    private final CommandLine commandLine;

    static {
        for (ArgumentType argumentType : ArgumentType.values()) {
            if (!argumentType.isUse()) {
                continue;
            }

            Option option = Option.builder()
                    .option(argumentType.getOptionName())
                    .longOpt(argumentType.getLongOptionName())
                    .required()
                    .hasArg()
                    .desc(argumentType.getDescription())
                    .build();

            options.addOption(option);
        }
    }

    public ProgramArgumentParser(String[] args) {
        this.commandLine = makeCommandLine(args);
    }

    private CommandLine makeCommandLine(String[] args) {
        CommandLineParser lineParser = new DefaultParser();
        CommandLine commandLine;

        try {
            commandLine = lineParser.parse(options, args);
        } catch (ParseException parseException) {
            throw new IllegalArgumentException("Invalid argument has been entered.", parseException);
        }

        return commandLine;
    }

    public String getArgumentValue(String argumentName) {
        return this.commandLine.getOptionValue(argumentName);
    }

    public String[] getArgumentValues(String argumentName) {
        return this.commandLine.getOptionValues(argumentName);
    }
}
