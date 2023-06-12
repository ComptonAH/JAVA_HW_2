import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        Задание
//
//        Дана строка sql-запроса "select * from students where ". Сформируйте часть WHERE этого запроса, используя StringBuilder. Данные для фильтрации приведены ниже в виде json-строки.
//                Если значение null, то параметр не должен попадать в запрос.
//        Параметры для фильтрации: {"name":"Ivanov", "country":"Russia", "city":"Moscow", "age":"null"}
//        В итоге должно получится select * from students where name=Ivanov, country=Russia, city=Moscow, age=null


        String sql = "select * from students where";
        String filter = "{\"name\":\"Ivanov\", \"country\":\"Russia\", \"city\":\"Moscow\", \"age\":\"null\"}";
        filter = filter.replace("\"", "").replace("{", " ").replace("}", "").replace(":", "=");
        if (filter.contains("null")) {
            filter = filter.substring(0, filter.lastIndexOf(", age"));
        }
        StringBuilder stringBuilder = new StringBuilder(sql + filter);
        System.out.println(stringBuilder);


//        Дана json-строка (можно сохранить в файл и читать из файла)
//        [{"фамилия":"Иванов","оценка":"5","предмет":"Математика"},{"фамилия":"Петрова","оценка":"4","предмет":"Информатика"},{"фамилия":"Краснов","оценка":"5","предмет":"Физика"}]
//        Написать метод(ы), который распарсит json и, используя StringBuilder, создаст строки вида: Студент [фамилия] получил [оценка] по предмету [предмет].
//        Пример вывода:
//        Студент Иванов получил 5 по предмету Математика.
//        Студент Петрова получил 4 по предмету Информатика.
//        Студент Краснов получил 5 по предмету Физика.
//
        String s = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"}," +
                "{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"}," +
                "{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";

        s = s.replace("[", "").replace("]", "").replace("}", " ");

        String s1 = "Студент [фамилия] получил [оценка] по предмету [предмет]";

        String[] students = s.split(" ,");
        String[][] data_students = new String[3][3];

        for (int i = 0; i < students.length; i++) {
            StringBuilder stringBuilder = new StringBuilder(s1);
            students[i] = students[i].replace("{", "").replace("\"", "").replace("}", "");
            for (int j = 0; j < students.length; j++) {
                if (j == 0) {
                    data_students[i][j] = students[i].substring(students[i].indexOf("я:") + 2, students[i].indexOf(",о"));
                    if (i == 1) {
                        stringBuilder = sb_stud_replace_gender_MTF(stringBuilder, i, j, data_students);
                    }
                    stringBuilder = sb_stud_replace_surname(stringBuilder, i, j, data_students);
                } else if (j == 1) {
                    data_students[i][j] = students[i].substring(students[i].indexOf("а:") + 2, students[i].indexOf(",п"));
                    stringBuilder = sb_stud_replace_mark(stringBuilder, i, j, data_students);
                } else if (j == 2) {
                    data_students[i][j] = students[i].substring(students[i].indexOf("т:") + 2);
                    stringBuilder = sb_stud_replace_subject(stringBuilder, i, j, data_students);
                    System.out.println(stringBuilder);
                }
            }
        }
        System.out.println();
        System.out.println();

//        *Сравнить время выполнения замены символа "а" на "А" любой строки содержащей >1000 символов средствами String и StringBuilder.

//          Comparing using "String"
        var cur_time = System.currentTimeMillis();
        String symbols = "qwertyuiopasdfghjklzxcvbnm";
        int size = 10000;
        String random = new Random().ints(size, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        random = random.replace('a', 'A');
        System.out.println("Time need to change all 'a' using \"String\" is " + (System.currentTimeMillis() - cur_time) + " seconds");


        System.out.println();


//        Comparing using "StringBuilder"
        var cur_time_2 = System.currentTimeMillis();
        StringBuilder symbols_2 = new StringBuilder();
        for (int i = symbols_2.length(); i < 10000; i++) {
            symbols_2 = symbols_2.append(symbols);
        }
        symbols_2 = new StringBuilder(symbols_2.toString().replace('a', 'A'));
        System.out.println("Time need to change all 'a' using \"StringBuilder\" is " + (System.currentTimeMillis() - cur_time_2) + " seconds");
    }

    public static StringBuilder sb_stud_replace_surname(StringBuilder sb, int index_i, int index_j, String[][] data_students) {
        return sb = sb.replace(sb.indexOf("[фамилия]"), sb.indexOf("я] по") + 2, data_students[index_i][index_j]);
    }

    public static StringBuilder sb_stud_replace_mark(StringBuilder sb, int index_i, int index_j, String[][] data_students) {
        return sb = sb.replace(sb.indexOf("[оценка]"), sb.indexOf("а] по") + 2, data_students[index_i][index_j]);
    }

    public static StringBuilder sb_stud_replace_subject(StringBuilder sb, int index_i, int index_j, String[][] data_students) {
        return sb = sb.replace(sb.indexOf("[предмет]"), sb.indexOf("т]") + 2, data_students[index_i][index_j].toLowerCase());
    }

    public static StringBuilder sb_stud_replace_gender_MTF(StringBuilder sb, int index_i, int index_j, String[][] data_students) {
        return sb = sb.replace(sb.indexOf("Студент"), sb.indexOf("нт [") + 2, "Студентка")
                .replace(sb.indexOf("получил"), sb.indexOf("ил [") + 2, "получила");
    }
}







