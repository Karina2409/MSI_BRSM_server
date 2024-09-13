package com.youth_system_server.help;

import com.youth_system_server.entity.FacultyEnum;

import java.util.HashMap;
import java.util.Map;

public class FacultyNumber {

    public static FacultyEnum getFacultyNameByNum(Integer facultyNum){
        Map<Integer, FacultyEnum> facultyMap = new HashMap<>();
        facultyMap.put(1, FacultyEnum.ФКП);
        facultyMap.put(2, FacultyEnum.ФИТУ);
        facultyMap.put(3, FacultyEnum.ФРЭ);
        facultyMap.put(4, FacultyEnum.ФКСИС);
        facultyMap.put(5, FacultyEnum.ИЭФ);
        facultyMap.put(6, FacultyEnum.ФИБ);
        facultyMap.put(7, FacultyEnum.ВФ);

        return facultyMap.get(facultyNum);
    }

}
