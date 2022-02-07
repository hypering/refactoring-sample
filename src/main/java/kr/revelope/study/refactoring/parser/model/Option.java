package kr.revelope.study.refactoring.parser.model;

import kr.revelope.study.refactoring.parser.types.ArgumentType;

public class Option {
    private final String optionName; // 이름
    private final String longOptionName; // 긴 이름
    private final String description; // 설명
    private final boolean hasMany; // 원소를 많이 가지고 있는지 여부
    private final boolean hasArg; // 원소를 가져야 하는지 여
    private final ArgumentType parentArgumentType; // 부모 ArgumentType

    public Option(String optionName, String longOptionName, String description, boolean hasMany, boolean hasArg, ArgumentType parentArgumentType) {
        this.optionName = optionName;
        this.longOptionName = longOptionName;
        this.description = description;
        this.hasMany = hasMany;
        this.hasArg = hasArg;
        this.parentArgumentType = parentArgumentType;
    }

    public static Option.Builder builder(String optionName) {
        return new Option.Builder(optionName);
    }

    public boolean hasLongOptionName() {
        return this.longOptionName != null;
    }

    public String getOptionName() {
        return optionName;
    }

    public String getLongOptionName() {
        return longOptionName;
    }

    public boolean isHasMany() {
        return hasMany;
    }

    public boolean isHasArg() {
        return hasArg;
    }

    public ArgumentType getParentArgumentType() {
        return parentArgumentType;
    }

    public static final class Builder {
        private String optionName;
        private String longOptionName;
        private String description;
        private boolean hasMany;
        private boolean hasArg;
        private ArgumentType parentArgumentType;

        private Builder(String optionName) {
            this.optionName = optionName;
        }

        public Option.Builder description(String description) {
            this.description = description;
            return this;
        }

        public Option.Builder longOptionName(String longOptionName) {
            this.longOptionName = longOptionName;
            return this;
        }

        public Option.Builder hasMany(boolean hasMany) {
            this.hasMany = hasMany;
            return this;
        }

        public Option.Builder hasArg(boolean hasArg) {
            this.hasArg = hasArg;
            return this;
        }

        public Option.Builder parentArgumentType(ArgumentType parentArgumentType) {
            this.parentArgumentType = parentArgumentType;
            return this;
        }

        public Option build() {
            return new Option(this.optionName, this.longOptionName, this.description, hasMany, hasArg, parentArgumentType);
        }
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionName='" + optionName + '\'' +
                ", longOptionName='" + longOptionName + '\'' +
                ", description='" + description + '\'' +
                ", hasMany=" + hasMany +
                ", hasArg=" + hasArg +
                ", parentArgumentType=" + parentArgumentType +
                '}';
    }
}
