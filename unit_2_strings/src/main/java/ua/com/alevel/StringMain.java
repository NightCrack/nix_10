package ua.com.alevel;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringMain {

    private static final String NAME_1 = "name1";
    private static final String NAME_2 = "name2";

    public static void main(String[] args) {

        int a = 10;
        int res1 = a * 2;
        int res2 = a << 1;

        System.out.println("res1 = " + res1);
        System.out.println("res2 = " + res2);

        String ss1 = "\n";
        String ss2 = "\n";

        System.out.println("ss1 = " + !ss1.isEmpty());
        System.out.println("ss2 = " + !ss2.isBlank());

        System.out.println("s1 = " + StringUtils.isNotEmpty(ss1));
        System.out.println("s2 = " + StringUtils.isNotBlank(ss2));

        String s1 = "test";
        String s2 = s1;
        s2 = "test2";

        System.out.println("s1 = " + s1);

        Person person1 = new Person();
        person1.setName(NAME_1);

        Person person2 = person1;
        person2.setName(NAME_2);

        System.out.println("person1 = " + person1.getName());

        System.out.println("StringMain.main");
        List<Person> persons = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1_000; i++) {
            int r = random.nextInt(2);
            Person person = new Person();
            person.setName(r == 0 ? NAME_1 : NAME_2);
            persons.add(person);
        }
        long count = persons
                .stream()
                .filter(person -> person.getName().equals(NAME_1))
                .count();
        System.out.println(count);

    }
}
