package kr.revelope.study.refactoring.parser;

import kr.revelope.study.refactoring.parser.model.Argument;
import kr.revelope.study.refactoring.parser.model.Option;
import kr.revelope.study.refactoring.parser.model.Options;
import kr.revelope.study.refactoring.parser.types.ArgumentType;
import kr.revelope.study.refactoring.parser.util.Parser;

public class ProgramArgumentParser {
    private static final Options options = new Options();
    private final Argument args;

    static {
        for (ArgumentType argumentType : ArgumentType.values()) {
            if (!argumentType.isUse()) {
                continue;
            }

            Option option = Option.builder(argumentType.getOptionName())
                    .longOptionName(argumentType.getLongOptionName())
                    .description(argumentType.getDescription())
                    .hasMany(argumentType.isHasMany())
                    .hasArg(argumentType.isHasArg())
                    .parentArgumentType(argumentType.getParentArgumentType())
                    .build();

            options.addOption(option);
        }
    }

    public ProgramArgumentParser(String[] args) {
        this.args = makeCommandLine(args);
    }

    private Argument makeCommandLine(String[] args) {
        return Parser.parse(options, args);
    }

    public String getArgumentValue(ArgumentType argumentType) throws IllegalAccessException {
        if (argumentType.isHasMany()) {
            throw new IllegalAccessException("This option has many arguments");
        }

        return this.args.getStringArgumentValues(argumentType.getOptionName()).get(0);
    }

    public <R> R getArgumentValues(ArgumentType argumentType) throws IllegalAccessException {
        if (!argumentType.isHasMany()) {
            throw new IllegalAccessException("This option has many arguments");
        }

        if (argumentType == ArgumentType.COLUMN_NAME) {
            return (R) this.args.getColumnArgumentValues(argumentType.getOptionName());
        } else {
            return (R) this.args.getStringArgumentValues(argumentType.getOptionName());
        }
    }
}
