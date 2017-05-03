package tree;


class Int implements IHasKey<Integer> {
    int value;
    Int(final int value) {
        this.value = value;
    }
    @Override
    public Integer getKey() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
