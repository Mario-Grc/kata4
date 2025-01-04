package software.ulpgc.kata4.model;

public class Title {
    private final String name;
    private final int year;
    private final int duration;

    public Title(String name, int year, int duration) {
        this.name = name;
        this.year = year;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Title{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                '}';
    }
}
