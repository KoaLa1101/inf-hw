package StudentSerialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Student implements Serializable {
    private String name, group;
    private char gender;
    private int birthDay, birthMonth, birthYear;

    public Student(String name, String group, char gender, int birthYear, int birthMonth, int birthDay)  {
        this.name = name;
        this.group = group;
        this.gender = gender;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public char getGender() {
        return gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public static void serialize(Collection<Student> students, String way) {
        try (FileOutputStream out = new FileOutputStream(way)) {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.putInt(students.size());
            out.write(buffer.array());

            for (Student student : students) {
                int nSize = student.getName().getBytes().length;
                int gSize = student.getGroup().getBytes().length;
                buffer = ByteBuffer.allocate(8);
                buffer.putInt(nSize);
                buffer.putInt(gSize);
                out.write(buffer.array());
                out.flush();

                buffer = ByteBuffer.allocate(14 + nSize + gSize);  // Korjick suggested the idea
                buffer.put(student.getName().getBytes());
                buffer.put(student.getGroup().getBytes());
                buffer.putChar(student.getGender());
                buffer.putInt(student.getBirthDay());
                buffer.putInt(student.getBirthMonth());
                buffer.putInt(student.getBirthYear());
                out.write(buffer.array());
                out.flush();
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Collection<Student> deserialize(String way) {
        try (FileInputStream in = new FileInputStream(way)) {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            in.read(buffer.array());
            int size = buffer.getInt();
            ArrayList<Student> students = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                buffer = ByteBuffer.allocate(4);
                in.read(buffer.array());
                byte[] name = new byte[buffer.getInt()];
                buffer = ByteBuffer.allocate(4);
                in.read(buffer.array());
                byte[] group = new byte[buffer.getInt()];

                buffer = ByteBuffer.allocate(14);
                in.read(name);
                in.read(group);
                in.read(buffer.array());
                students.add(new Student( new String(name), new String(group), buffer.getChar(), buffer.getInt(), buffer.getInt(), buffer.getInt()));
            }
            return students;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n"
                + "Group: " + group + "\n"
                + "gender: " + gender + "\n"
                + "Birthday: " + birthDay + "." + birthMonth + "." + birthYear + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getGender() == student.getGender() &&
                getBirthDay() == student.getBirthDay() &&
                getBirthMonth() == student.getBirthMonth() &&
                getBirthYear() == student.getBirthYear() &&
                getName().equals(student.getName()) &&
                getGroup().equals(student.getGroup());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGroup(), getGender(), getBirthDay(), getBirthMonth(), getBirthYear());
    }
}