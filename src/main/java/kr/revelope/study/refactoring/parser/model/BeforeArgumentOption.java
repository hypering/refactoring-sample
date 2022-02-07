package kr.revelope.study.refactoring.parser.model;

public class BeforeArgumentOption {
    private final Option option;
    private final String value;

    public BeforeArgumentOption(Option option, String value) {
        this.option = option;
        this.value = value;
    }

    public Option getOption() {
        return option;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BeforeArgumentOption{" +
                "option=" + option +
                ", value='" + value + '\'' +
                '}';
    }
}
