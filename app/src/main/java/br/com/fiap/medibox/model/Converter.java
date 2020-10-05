package br.com.fiap.medibox.model;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.sql.Timestamp;

public class Converter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
