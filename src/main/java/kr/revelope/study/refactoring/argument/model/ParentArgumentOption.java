package kr.revelope.study.refactoring.argument.model;

public class ParentArgumentOption {
    private final Option option;
    private final String value;

    public ParentArgumentOption(Option option, String value) {
        this.option = option;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getOptionName() {
        return this.option.getOptionName();
    }

    @Override
    public String toString() {
        return "BeforeArgumentOption{" +
                "option=" + option +
                ", value='" + value + '\'' +
                '}';
    }
}
