package com.example.demo.Dtos;

public class DtoC5 {
    private String month_name;
    private Integer year;
    private Integer average_daily_sales;
    private Integer difference_from_previous_month;

    public DtoC5(String month_name, Integer year, Integer average_daily_sales, Integer difference_from_previous_month) {
        this.month_name = month_name;
        this.year = year;
        this.average_daily_sales = average_daily_sales;
        this.difference_from_previous_month = difference_from_previous_month;
    }

    public String getMonth_name() {
        return month_name;
    }
    public Integer getYear() {
        return year;
    }
    public Integer getAverage_daily_sales() {
        return average_daily_sales;
    }
    public Integer getDifference_from_previous_month() {
        return difference_from_previous_month;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public void setAverage_daily_sales(Integer average_daily_sales) {
        this.average_daily_sales = average_daily_sales;
    }
    public void setDifference_from_previous_month(Integer difference_from_previous_month) {
        this.difference_from_previous_month = difference_from_previous_month;
    }
}
