import java.util.Arrays;
import java.util.Objects;

class EducationalInstitution {
    private String name;
    private String city;
    private int establishedYear;
    private int studentCount;
    private double rating;

    public EducationalInstitution(String name, String city, int establishedYear, int studentCount, double rating) {
        this.name = name;
        this.city = city;
        this.establishedYear = establishedYear;
        this.studentCount = studentCount;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getEstablishedYear() {
        return establishedYear;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EducationalInstitution that = (EducationalInstitution) o;
        return establishedYear == that.establishedYear &&
                studentCount == that.studentCount &&
                Double.compare(that.rating, rating) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, establishedYear, studentCount, rating);
    }

    @Override
    public String toString() {
        return "EducationalInstitution{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", establishedYear=" + establishedYear +
                ", studentCount=" + studentCount +
                ", rating=" + rating +
                '}';
    }
}

public class Main {

    public static void main(String[] args) {
        EducationalInstitution[] institutions = {
                new EducationalInstitution("University A", "City X", 2000, 15000, 4.5),
                new EducationalInstitution("College B", "City Y", 1995, 7000, 4.0),
                new EducationalInstitution("Institute C", "City Z", 2010, 5000, 4.7),
                new EducationalInstitution("Academy D", "City X", 2005, 12000, 4.3),
                new EducationalInstitution("School E", "City Y", 2000, 3000, 4.2)
        };

        Arrays.sort(institutions, (i1, i2) -> {
            int yearComparison = Integer.compare(i1.getEstablishedYear(), i2.getEstablishedYear());
            if (yearComparison == 0) {
                return Integer.compare(i2.getStudentCount(), i1.getStudentCount());
            }
            return yearComparison;
        });

        System.out.println("Відсортований масив навчальних закладів:");
        for (EducationalInstitution institution : institutions) {
            System.out.println(institution);
        }
        
        EducationalInstitution target = new EducationalInstitution("Academy D", "City X", 2005, 12000, 4.3);
        int index = Arrays.asList(institutions).indexOf(target);

        if (index != -1) {
            System.out.println("\nЗнайдений об'єкт:");
            System.out.println(institutions[index]);
        } else {
            System.out.println("\nОб'єкт не знайдено у масиві.");
        }
    }
}
