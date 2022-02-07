package kr.revelope.study.refactoring.parser.model;

import kr.revelope.study.refactoring.model.Column;

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

    public List<String> getStringArgumentValues(String key) {
        return new ArrayList<>(this.stringArgument.get(key));
    }

    public List<Column> getColumnArgumentValues(String key) {
        return new ArrayList<>(this.columnArgument.get(key));
    }

    public void addColumnValue(String key, Column value) {
        if (!this.columnArgument.containsKey(key)) {
            this.columnArgument.put(key, new ArrayList<>());
        }

        this.columnArgument.get(key).add(value);
    }

    public void addStringValue(String key, String value) {
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
