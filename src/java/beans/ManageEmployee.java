package beans;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Sergey
 */
@ManagedBean
@RequestScoped
public class ManageEmployee {

    private int result = 0;

    private static SessionFactory factory = new AnnotationConfiguration().
            configure().
            //addPackage("com.xyz") //add package if used.
            addAnnotatedClass(Employee1.class).
            buildSessionFactory();
    
    ManageEmployee me;

    public void myMain() {
        me = new ManageEmployee();
        factory = new AnnotationConfiguration().
                configure().
                //addPackage("com.xyz") //add package if used.
                addAnnotatedClass(Employee1.class).
                buildSessionFactory();
        addThreeRecords();
        listEmployee();
    }

    /* Add few employee records in database */
    public void addThreeRecords() {
        Integer empID1 = me.addEmployee("Zara1", "Ali", 1000);
        Integer empID2 = me.addEmployee("Daisy1", "Das", 5000);
        Integer empID3 = me.addEmployee("John1", "Paul", 10000);
    }

    /* List down all the employees */

    /* Update employee's records */

    /* Delete an employee from the database */

    /* List down new list of the employees */
    public void addEmployeee() {
        addEmployee("firstName", "lastName", 1000);
    }

    /* Method to CREATE an employee in the database */
    public Integer addEmployee(String fname, String lname, int salary) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try {
            tx = session.beginTransaction();
            Employee1 employee = new Employee1();
            employee.setFirstName(fname);
            employee.setLastName(lname);
            employee.setSalary(salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    public List<Employee1> lookForCriteria() {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Employee1.class);
        criteria = criteria.add(Restrictions.gt("salary", 1000));
        criteria = criteria.addOrder(Order.desc("salary"));
        List l = criteria.list();
        return l;
    }
    
    public void countRow () {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Employee1.class);
            criteria.setProjection(Projections.rowCount());
            List rowCount = criteria.list();
            result = Integer.valueOf(rowCount.get(0).toString());
            tx.commit();
        } catch (HibernateException he) {
            System.out.println("countRow broken");
            tx.rollback();
        } finally {
            session.close();
        }
    }

    public Integer updateRow() {
        Session session = factory.openSession();
        Transaction tx = null;
        int result = 0;
        try {
            tx = session.beginTransaction();
            String hql = "UPDATE Employee1 set salary = :salary "
                    + "WHERE id = :employee_id";
            System.out.println("***" + hql);
            Query query = session.createQuery(hql);
            System.out.println("***11");
            query.setParameter("salary", 0);
            query.setParameter("employee_id", 3);
            result = query.executeUpdate();
            System.out.println("Row affected: " + result);
        } catch (HibernateException he) {
            System.out.println("" + he.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    /* Method to  READ all the employees */
    public List<Employee1> receivelistEmployee() {
        Session session = factory.openSession();
        List<Employee1> employee1s = new LinkedList<>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee1").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
                Employee1 employee = (Employee1) iterator.next();
                employee1s.add(employee);
            }
            tx.commit();
        } catch (HibernateException he) {
            System.out.println("" + he.getMessage());
            tx.rollback();
        } finally {
            session.close();
        }
        return employee1s;
    }

    public void listEmployee() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee1").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
                Employee1 employee = (Employee1) iterator.next();
                System.out.print("Id: " + employee.getId() + ", First Name: "
                        + employee.getFirstName() + ", Last Name: "
                        + employee.getLastName() + ", Salary: " + employee.getSalary());
            }
            tx.commit();
        } catch (HibernateException he) {
            System.out.println("" + he.getMessage());
            tx.rollback();
        } finally {
            session.close();
        }
    }

    /* Method to UPDATE salary for an employee */
    public void updateEmployee(Integer employeeID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Employee1 employee = (Employee1) session.get(Employee1.class, employeeID);
            employee.setSalary(employee.getSalary() + 1);
            session.update(employee);
            tx.commit();
        } catch (HibernateException he) {
            System.out.println("" + he.getMessage());
            tx.rollback();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer employeeID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Employee1 employee = (Employee1) session.get(Employee1.class, employeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException he) {
            System.out.println("" + he.getMessage());
            tx.rollback();
        } finally {
            session.close();
        }
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
