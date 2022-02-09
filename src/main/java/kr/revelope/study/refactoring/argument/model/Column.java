package kr.revelope.study.refactoring.argument.model;

public class Column {
    private final String columnName;
    private Integer value;

    public Column(String columnName, Integer value) {
        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", value=" + value +
                '}';
    }
}
