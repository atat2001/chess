package toste.proj.vue.model;

public class Friend {

    private String id;
    private String name;

    public Friend(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Friend() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
