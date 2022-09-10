package com.company;

import java.util.*;
import java.util.stream.Collectors;

class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }

    }


    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };


        /*  Raw data:
        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia
        **************************************************
        Duplicate filtered, grouped by name, sorted by name and id:
        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args) {
        int sum = 10;
        int[] samples = {3, 4, 2, 7};

        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();
        filteredGroupSortInRAW_DATA();
        System.out.println();
        System.out.println("**************************************************");
        System.out.println("print the pair in parentheses that give the sum - 10");
        System.out.println();
        twoElInSumHash(samples, sum);
        twoElInSumSort(samples, sum);
        System.out.println();
        System.out.println("Task 3 реализация функции нечеткого поиска");

        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени
            Что должно получиться
                Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */



        /*
        Task2
            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */




        // Task3
        //     Реализовать функцию нечеткого поиска

        System.out.println(fuzzySearch("car", "ca6$$#_rtwheel")); // true
        System.out.println(fuzzySearch("cwhl", "cartwheel")); // true
        System.out.println(fuzzySearch("cwhee", "cartwheel")); // true
        System.out.println(fuzzySearch("cartwheel", "cartwheel")); // true
        System.out.println(fuzzySearch("cwheeel", "cartwheel")); // false
        System.out.println(fuzzySearch("lw", "cartwheel")); // false



    }
    // Task 1
    public static Map<String, Long> filteredGroupSortInRAW_DATA() {
        Map<String, Long> sortedMap = Arrays.stream(RAW_DATA) //массив в стрим
                .distinct() //уникальные элементы
                .collect(Collectors.groupingBy(Person::getName, Collectors.counting())); // группируем уникальные элементы и считаем их количество

        sortedMap.entrySet().forEach(entry -> {
            System.out.println("Key: " + entry.getKey() + "\n" + "Value: " + entry.getValue()); //вывод в консоль
        });
        return sortedMap;
    }
    // Task 2 Хеширование
    public static void twoElInSumHash(int[] samples,int sum) {
        HashSet<Integer> set = new HashSet<>();
        for(int el : samples) {
            if(set.contains(sum - el)) {
                System.out.println("[" + (sum - el) + "," + el + "]");
                set.remove(sum-el);
            } else {
                set.add(el);
            }
        }
    }
    // Task 2 Сортировка
    public static void twoElInSumSort(int[] samples, int sum) {
        Arrays.sort(samples);
        int start = 0;
        int finish = samples.length -1;
        while (start < finish) {
            if(samples[start] + samples[finish] == sum){
                System.out.println("[" + samples[start] + "," + samples[finish] + ']' );
                return;
            }
            if(samples[start] + samples[finish] < sum) {
                start++;
            }else {
                finish--;
            }
        }
    }

    /* Task 3
     * Превращаем pattern в регулярное выражение и вставляем модификатор .* через каждый символ.
     * На основе pattern ищем в text нужную последовательность символов.
     */
    public static boolean fuzzySearch(String pattern, String text) {
        return text.matches(pattern.replaceAll("(.{0})", ".*"));
    }

}

