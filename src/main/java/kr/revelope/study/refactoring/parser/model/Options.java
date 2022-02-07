package kr.revelope.study.refactoring.parser.model;

import java.util.HashMap;
import java.util.Map;

public class Options {
    private final Map<String, Option> shortOptions = new HashMap<>();
    private final Map<String, Option> longOptions = new HashMap<>();

    public Options() {
    }

    public void addOption(Option opt) {
        if (opt.hasLongOptionName()) {
            this.longOptions.put(opt.getLongOptionName(), opt);
        }

        this.shortOptions.put(opt.getOptionName(), opt);
    }

    public Option getOption(String option) {
        return this.shortOptions.containsKey(option) ? this.shortOptions.get(option) : this.longOptions.get(option);
    }

    @Override
    public String toString() {
        return "Options{" +
                "shortOptions=" + shortOptions +
                ", longOptions=" + longOptions +
                '}';
    }
}
