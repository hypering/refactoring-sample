package kr.revelope.study.refactoring.parser.util;

import kr.revelope.study.refactoring.model.Column;
import kr.revelope.study.refactoring.parser.model.Argument;
import kr.revelope.study.refactoring.parser.model.BeforeArgumentOption;
import kr.revelope.study.refactoring.parser.model.Option;
import kr.revelope.study.refactoring.parser.model.Options;
import kr.revelope.study.refactoring.parser.types.ArgumentType;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Iterator;

public class Parser {
    public static Argument parse(Options options, String[] arguments) {
        Argument args = new Argument();

        Iterator<String> iterator = Arrays.stream(arguments).iterator();
        BeforeArgumentOption beforeArgumentOption = null;
        while (iterator.hasNext()) {
            String cmd = iterator.next();
            if (!StringUtils.startsWith(cmd, "-") && !StringUtils.startsWith(cmd, "--")) {
                throw new IllegalArgumentException("Invalid Argument input");
            }

            Option option;
            if (StringUtils.startsWith(cmd, "--")) {
                option = options.getOption(cmd.substring(2));
            } else {
                option = options.getOption(cmd.substring(1));
            }

            if (option == null) {
                throw new IllegalArgumentException("Invalid Argument input");
            }

            // NOTE : 아직 arg가 없는 arg가 없어서 일단 continue로 진행
            if (!option.isHasArg()) {
                continue;
            }

            if (!iterator.hasNext()) {
                throw new IllegalArgumentException("Invalid Argument input");
            }

            ArgumentType argumentType = ArgumentType.getArgumentType(option.getOptionName());
            String argumentValue = iterator.next();

            // 팩토리로 만들어서 처리 ?
            if (ArgumentType.COLUMN_NAME == argumentType) {
                args.addColumnValue(option.getOptionName(), new Column(argumentValue, null));
            } else if (ArgumentType.COLUMN_VALUE == argumentType) {
                if (beforeArgumentOption == null) {
                    throw new IllegalArgumentException("Invalid Argument input");
                }

                if (ArgumentType.getArgumentType(beforeArgumentOption.getOption().getOptionName()) != option.getParentArgumentType()) {
                    throw new IllegalArgumentException("Invalid Argument input");
                }

                for (Column column : args.getColumnArgumentValues((beforeArgumentOption.getOption().getOptionName()))) {
                    if (!StringUtils.equals(column.getColumnName(), beforeArgumentOption.getValue())) {
                        continue;
                    }

                    column.setValue(Integer.parseInt(argumentValue));
                }
            } else {
                args.addStringValue(option.getOptionName(), argumentValue);
            }

            if (option.getParentArgumentType() == null) {
                beforeArgumentOption = new BeforeArgumentOption(option, argumentValue);
            }
        }

        return args;
    }
}
