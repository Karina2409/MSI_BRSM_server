package com.youth_system_server.entity;

import java.util.HashMap;
import java.util.Map;

import static com.youth_system_server.entity.FacultyEnum.*;

public class DeanData {
    private static final Map<FacultyEnum, String> facultyDeanMap = new HashMap<>();

    static {
        facultyDeanMap.put(ФКП, "Лихачевскому Д.В.");
        facultyDeanMap.put(ФИТУ, "Шилину Л.Ю.");
        facultyDeanMap.put(ФКСИС, "Логину В.М.");
        facultyDeanMap.put(ИЭФ, "Лаврова О.И.");
        facultyDeanMap.put(ФИБ, "Дроботу С.В.");
        facultyDeanMap.put(ФРЭ, "Гранько С.В.");
        facultyDeanMap.put(ВФ, "Колегаеву В.Г.");
    }

    public static String getDean(FacultyEnum faculty) {
        return facultyDeanMap.get(faculty);
    }
}
