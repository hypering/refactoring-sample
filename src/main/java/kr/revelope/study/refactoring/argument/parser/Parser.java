package kr.revelope.study.refactoring.argument.parser;

import kr.revelope.study.refactoring.argument.model.Argument;
import kr.revelope.study.refactoring.argument.model.Option;
import kr.revelope.study.refactoring.argument.model.Options;
import kr.revelope.study.refactoring.argument.model.ParentArgumentOption;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class Parser {
    private final Options options;
    private final String[] arguments;

    public Parser(Options options, String[] arguments) {
        this.options = options;
        this.arguments = arguments;
    }

    public Argument parse() {
        Argument args = new Argument();

        Iterator<String> iterator = Arrays.stream(arguments).iterator();
        ParentArgumentOption parentArgumentOption = null;
        while (iterator.hasNext()) {
            String command = iterator.next();
            if (isInvalidCommand(command)) {
                throw new IllegalArgumentException("Invalid Argument input");
            }

            Option option = Optional.ofNullable(getOption(command))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Argument input"));

            // TODO : 아직 arg가 없는 option이 없어서 일단 continue
            if (!option.isHasArg()) {
                continue;
            }

            String optionValue = Optional.ofNullable(getOptionValue(iterator))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Argument input"));

            args.addOptionValue(parentArgumentOption, option, optionValue);

            if (option.getParentArgumentType() == null) {
                parentArgumentOption = new ParentArgumentOption(option, optionValue);
            }
        }

        return args;
    }

    private boolean isInvalidCommand(String command) {
        return (!StringUtils.startsWith(command, "-") && !StringUtils.startsWith(command, "--"));
    }

    private Option getOption(String command) {
        if (StringUtils.startsWith(command, "--")) {
            return this.options.getOption(command.substring(2));
        } else {
            return this.options.getOption(command.substring(1));
        }
    }

    private String getOptionValue(Iterator<String> iterator) {
        if (!iterator.hasNext()) {
            return null;
        }

        return iterator.next();
    }
}
