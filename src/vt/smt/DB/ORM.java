package vt.smt.DB;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by semitro on 13.05.17.
 */

public class ORM {
    /**
     * Метод, возвращающий SQL-запрос для замены объекта с указанным id
     * На новый объект
     *
     * @param id - ид объекта в базе данных,
     * @param newObject - Объект, на который следует заменить хранящийся в бд
     *
     */
    public static String getUpdateObjectQuery(int id,Object newObject){
        StringBuilder query = new StringBuilder(
                "update " + newObject.getClass().getSimpleName()+ " \n set ");
        Map<String,String> values = getAllValues(newObject);
        values.forEach((key,value)->{
            query.append(key + " = " + value + ",\n ");
        });
        // Удаляем последную лишнюю запятую
        query.deleteCharAt(query.lastIndexOf(","));
        query.append("where " + getID(newObject) + " = " + id);
        query.append(';');
        return query.toString();
    }
    /**
     * Метод, генерирующий код создания таблицы по заданному обекту
     * @param obj - объект, требующий отображения в бд
     * @return SQL-запрос, создающий требуемую таблицу
     */
    public static String getDDL(Object obj){
        //Строка, в которой постепенно будет формироваться запрос
        StringBuilder query = new StringBuilder("create table ");
        query.append(obj.getClass().getSimpleName() + "(\n ");
        query.append(getID(obj) + "serial primary key,\n ");
            for (Field field : getAllPrimitives(obj.getClass())) {
                query.append(field.getName() + " ");
                query.append(toPostresType(field.getType().getSimpleName()) +",\n " );
            }
        // Удаление последней лишней запятой
        query.deleteCharAt(query.length()-3);
        query.append(");");
       return query.toString();
    }
    /**
     * Метод формирования запроса к БД на вставку данного объекта
     * Имя класса считается названием таблицы
     */
    public static String getInsertQuery(Object obj){
        StringBuilder query = new StringBuilder(
                "insert into " + obj.getClass().getSimpleName() + " " );
        Map<String,String> values = getAllValues(obj);
        query.append(values.keySet().toString() + " values " + values.values() + ';');
        return query.toString().replace('[','(').replace(']',')');
    }
    /**
    *   Метод, считывающий все геттеры, которые класс предоставляет и составляющий из них
     *  Ассоциативную карту (название геттера (или метода is) без префикса get, - значение поля)
     *  Не боится иерархии и содержания непримитивных типов
     *  @param obj Наш подопечный - передаём не Класс, а экземпляр, потому что нуждаемся в данных
     *
    **/
    private static Map<String,String> getAllValues(Object obj) {
       Map<String,String> result = new HashMap<>(); // Здесь конструируется результат работы метода
        for (Method method : obj.getClass().getMethods()) {
            try { // Просто берём все доступные геттеры и вызываем их
                if(method.getName().startsWith("get") && !method.getName().equals("getClass"))
                    result.put(method.getName().substring(3),toPSQLString(method.invoke(obj)));

                if(method.getName().startsWith("is"))
                    result.put(method.getName(),toPSQLString(method.invoke(obj)));

            }catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        // Поля, являющиеся непримитивными типами, тоже следует обработать. Применяется рекурсия
        for (Field field :  obj.getClass().asSubclass(obj.getClass().getSuperclass()).getDeclaredFields()) {
            field.setAccessible(true);
            try { // Примитивный тип или нет определяеся вот таким вот образом
                if (toPostresType(field.getType().getSimpleName()) == null)
                    result.putAll(getAllValues(field.get(obj)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * Преобразование для видения sql (например, булевские типы, закючённые в кавычки
    *
     */
    private static String toPSQLString(Object data){
        if(data.getClass().getSimpleName().equals("Boolean") ||
           data.getClass().getSimpleName().equals("boolean") ||
           data.getClass().getSimpleName().equals("String"))
         return '\'' + data.toString() + '\'';

        if(data.getClass().equals(ZonedDateTime.class))
            return '\'' + data.toString().substring(0,data.toString().indexOf('T')) + '\'';

     return data.toString();
    }
    /**
     * Метод, умеющий считывать все примитивные поля объекта,
     * учитывая иерхархию и композицию непримитивных типов
     * @param obj - Класс исследуемого объекта
    **/
    private static Field[] getAllPrimitives(Class obj){
        LinkedList<Field> fields = new LinkedList<>(); // Для формирования результатов
        for (Field field : obj.getDeclaredFields()) {
            //Если мы знаем, как преобразовать этот тип к типу данных постгрес
            if (toPostresType(field.getType().getSimpleName()) != null)
                fields.add(field); // Просто добавляем поле
            else // Иначе это не примитив, и его нужно обработать таким же способом
                fields.addAll(Arrays.asList(getAllPrimitives(field.getType())));
        }
        // Если у объекта есть родительский класс ( и не Object)
        if(obj.getSuperclass() != Object.class) // Делаем с ним то же самое
            fields.addAll(Arrays.asList(getAllPrimitives(obj.getSuperclass())));

        return fields.toArray(new Field[0]);
    }
    // Таблица преобразования типов данных Java в типы данных Postgres
    private static String toPostresType(String javaType){
        switch (javaType) {
            case "String":
                return "varchar(80)";
            case "int":
                return "integer";
            case "long":
                return "integer";
            case "byte":
                return "smallint";
            case "float":
                return "real";
            case "double":
                return "real";
            case "boolean":
                return "boolean";
        }
        return null;
    }
    private static String getID(Object object){
        return object.getClass().getSimpleName() + "_id";
    }
}