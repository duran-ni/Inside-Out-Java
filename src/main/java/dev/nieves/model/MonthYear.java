package dev.nieves.model;

import java.util.Objects;

/**
 * Value Object que representa un mes+año concreto para filtrar momentos.
 * No tiene identidad propia; dos instancias con el mismo mes y año son iguales.
 */
public class MonthYear {

    private final int month;
    private final int year;

    public MonthYear(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MonthYear other = (MonthYear) obj;
        return month == other.month && year == other.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }
}
