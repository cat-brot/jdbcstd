package com.atguigu.senior.pojo;

public class Employee {
    private Integer empId;//emp_id = empId 数据库中列名用下划线分隔，属性名用驼峰！
    private String empName;//emp_name = empName
    private Double empSalary;//emp_salary = empSalary
    private Integer empAge;//emp_age = empAge

    //省略get、set、无参、有参、toString方法。


    public Employee() {
    }

    public Employee(Integer empId, String empName, Double empSalary, Integer empAge) {
        this.empId = empId;
        this.empName = empName;
        this.empSalary = empSalary;
        this.empAge = empAge;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(Double empSalary) {
        this.empSalary = empSalary;
    }

    public Integer getEmpAge() {
        return empAge;
    }

    public void setEmpAge(Integer empAge) {
        this.empAge = empAge;
    }

}
