/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static java.sql.JDBCType.NULL;
import javax.persistence.*;

/**
 *
 * @author Sergey
 */
@Entity
@Table(name = "Employee1")
public class Employee1 {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "first_name", length = 64, nullable = false)
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "salary")
    private Integer salary;

    public Employee1() {
    }

    public Employee1(String firstName, String lastName, Integer salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
