package kr.revelope.study.refactoring.types;

public enum ArgumentType {
    FILE_NAME("f", "file-path", "file name"),
    COLUMN_NAME("c", "column-name", "column name");

    private final String optionName;
    private final String longOptionName;
    private final String description;

    ArgumentType(String optionName, String longOptionName, String description) {
        this.optionName = optionName;
        this.longOptionName = longOptionName;
        this.description = description;
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
}
