package kr.revelope.study.refactoring.types;

public enum ArgumentType {
    FILE_NAME("f", "file-path", "file name", true),
    COLUMN_NAME("c", "column-name", "column name", true);

    private final String optionName;
    private final String longOptionName;
    private final String description;
    private final boolean isUse;

    ArgumentType(String optionName, String longOptionName, String description, boolean isUse) {
        this.optionName = optionName;
        this.longOptionName = longOptionName;
        this.description = description;
        this.isUse = isUse;
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

    public boolean isUse() {
        return isUse;
    }
}
