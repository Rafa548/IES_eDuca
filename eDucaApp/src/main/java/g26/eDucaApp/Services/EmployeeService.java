package g26.eDucaApp.Services;

import g26.eDucaApp.Model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(Long employeeId);

    Employee getEmployeeByEmail(String employeeEmail);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long employeeId);
}