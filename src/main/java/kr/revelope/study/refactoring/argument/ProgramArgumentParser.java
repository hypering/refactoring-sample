package kr.revelope.study.refactoring.argument;

import kr.revelope.study.refactoring.argument.model.Argument;
import kr.revelope.study.refactoring.argument.model.Option;
import kr.revelope.study.refactoring.argument.model.Options;
import kr.revelope.study.refactoring.argument.parser.Parser;
import kr.revelope.study.refactoring.argument.types.ArgumentType;

import java.util.List;

public class ProgramArgumentParser {
    private static final Options options = new Options();
    private final Argument argument;

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
        Parser parser = new Parser(options, args);
        this.argument = parser.parse();
    }

    public <R> R getArgumentValue(ArgumentType argumentType) throws IllegalAccessException {
        if (argumentType.isHasMany()) {
            throw new IllegalAccessException("This option has many arguments");
        }

        return ((List<R>) this.argument.getArgumentValues(argumentType)).get(0);
    }

    public <R> R getArgumentValues(ArgumentType argumentType) throws IllegalAccessException {
        if (!argumentType.isHasMany()) {
            throw new IllegalAccessException("This option has many arguments");
        }

        return this.argument.getArgumentValues(argumentType);
    }
}
