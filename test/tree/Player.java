package tree;

class Player implements tree.IHasKey<String> {
    private String name;
    private int age;

    Player(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + ": " + String.valueOf(age);
    }

    @Override
    public String getKey() {
        return name;
    }

    String getName() {
        return name;
    }
}
