package kr.revelope.study.refactoring.parser.types;

import org.apache.commons.lang3.StringUtils;

public enum ArgumentType {
    FILE_NAME("f", "file-path", "file name", false, true, true, null),
    COLUMN_NAME("c", "column-name", "column name", true, true, true, null),
    COLUMN_VALUE("v", "column-value", "column value", true, true, true, COLUMN_NAME);

    private final String optionName;
    private final String longOptionName;
    private final String description;
    private final boolean hasMany;
    private final boolean hasArg;
    private final boolean isUse;
    private final ArgumentType parentArgumentType;

    ArgumentType(String optionName, String longOptionName, String description, boolean hasMany, boolean hasArg, boolean isUse, ArgumentType parentArgumentType) {
        this.optionName = optionName;
        this.longOptionName = longOptionName;
        this.description = description;
        this.hasMany = hasMany;
        this.hasArg = hasArg;
        this.isUse = isUse;
        this.parentArgumentType = parentArgumentType;
    }

    public String getOptionName() {
        return optionName;
    }

    public String getLongOptionName() {
        return longOptionName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHasMany() {
        return hasMany;
    }

    public boolean isHasArg() {
        return hasArg;
    }

    public boolean isUse() {
        return isUse;
    }

    public ArgumentType getParentArgumentType() {
        return parentArgumentType;
    }

    public static ArgumentType getArgumentType(String optionName) {
        for (ArgumentType option : ArgumentType.values()) {
            if (StringUtils.equals(option.getOptionName(), optionName)) {
                return option;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "ArgumentType{" +
                "optionName='" + optionName + '\'' +
                ", longOptionName='" + longOptionName + '\'' +
                ", description='" + description + '\'' +
                ", hasMany=" + hasMany +
                ", hasArg=" + hasArg +
                ", isUse=" + isUse +
                ", parentArgumentType=" + parentArgumentType +
                '}';
    }
}
