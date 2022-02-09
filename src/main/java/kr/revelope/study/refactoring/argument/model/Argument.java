package kr.revelope.study.refactoring.argument.model;

import kr.revelope.study.refactoring.argument.types.ArgumentType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Argument {
    private Map<String, List<String>> stringArgument;
    private Map<String, List<Column>> columnArgument;

    public Argument() {
        this.stringArgument = new HashMap<>();
        this.columnArgument = new HashMap<>();
    }

    public void addOptionValue(ParentArgumentOption parentArgumentOption, Option option, String optionValue) {
        ArgumentType argumentType = ArgumentType.getArgumentType(option.getOptionName());
        String optionName = option.getOptionName();

        if (ArgumentType.FILE_NAME == argumentType) {
            this.addStringValue(optionName, optionValue);
            return;
        }

        if (ArgumentType.COLUMN_NAME == argumentType) {
            this.addColumnValue(optionName, new Column(optionValue, null));
            return;
        }

        if (ArgumentType.COLUMN_VALUE == argumentType) {
            if (parentArgumentOption == null) {
                throw new IllegalArgumentException("Invalid Argument input");
            }

            if (ArgumentType.getArgumentType(parentArgumentOption.getOptionName()) != option.getParentArgumentType()) {
                throw new IllegalArgumentException("Invalid Argument input");
            }

            for (Column column : this.columnArgument.get(parentArgumentOption.getOptionName())) {
                if (!StringUtils.equals(column.getColumnName(), parentArgumentOption.getValue())) {
                    continue;
                }

                column.setValue(Integer.parseInt(optionValue));
            }
        }
    }

    public <R> R getArgumentValues(ArgumentType argumentType) {
        if (ArgumentType.FILE_NAME == argumentType) {
            return (R) this.stringArgument.get(argumentType.getOptionName());
        }

        if (ArgumentType.COLUMN_NAME == argumentType) {
            return (R) this.columnArgument.get(argumentType.getOptionName());
        }

        throw new IllegalArgumentException("Unknown argumentType");
    }

    private void addColumnValue(String key, Column value) {
        if (!this.columnArgument.containsKey(key)) {
            this.columnArgument.put(key, new ArrayList<>());
        }

        this.columnArgument.get(key).add(value);
    }

    private void addStringValue(String key, String value) {
        if (!this.stringArgument.containsKey(key)) {
            this.stringArgument.put(key, new ArrayList<>());
        }

        this.stringArgument.get(key).add(value);
    }

    @Override
    public String toString() {
        return "ArgumentConstant{" +
                "stringArgument=" + stringArgument +
                ", columnArgument=" + columnArgument +
                '}';
    }
}
