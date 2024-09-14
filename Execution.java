import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Execution {
public static void main(String[] args) throws Exception
{

try(Stream<String> lines = Files.lines(Paths.get("C:\\csv\\Employee.csv"))){
	
	List<Employee> empList = lines.skip(1)
					.map(
					line -> {
						String[] fields = line.split(",");
						return new Employee(Integer.parseInt(fields[0]),fields[1],fields[2],Long.parseLong(fields[3]),Integer.parseInt(fields[4]));
					}
					).collect(Collectors.toList());
	
	Map<Integer, List<Employee>> managerToEmployeesMap = empList.stream()
					.filter(e -> e.getManagerId() != null)
					.collect(Collectors.groupingBy(Employee::getManagerId));
					
	Map<Employee,Long> underpaidManagers = new HashMap<>();
	Map<Employee,Long> overpaidManagers = new HashMap<>();
	
	for(Map.Entry<Integer, List<Employee>> entry : managerToEmployeesMap.entrySet()) {
		
		Integer managerId = entry.getKey();
		List<Employee> employees = entry.getValue();
		
		double averageEmployeeSalary = employees.stream()
					.mapToDouble(Employee::getSalary)
					.average()
					.orElse(0);
					
		Employee manager = empList.stream()
					.filter(e -> e.getId().equals(managerId))
					.findFirst()
					.orElse(null);
					
		if(manager != null && manager.getSalary() < averageEmployeeSalary) {
			underpaidManagers.put(manager, (long) (averageEmployeeSalary - manager.getSalary()));
		}
		
		
		if(manager != null && manager.getSalary() > averageEmployeeSalary) {
			overpaidManagers.put(manager, (long) (manager.getSalary() - averageEmployeeSalary));
		}
				
		
	}
	
	int maxAllowedLength = 4;
	
	Map<Integer, Integer> employeeToManagerMap = empList.stream()
					.filter(e -> e.getManagerId() != null)
					.collect(Collectors.toMap(Employee::getId, Employee::getManagerId));
					
	Map<Employee, Integer> employeesWithLongReportingLine = new HashMap<>();
	
	int length = 0;
	for(Employee employee : empList) {
		
		Integer managerId = employee.getManagerId();
		
		if(managerId != null) {
			managerId = employeeToManagerMap.get(managerId);
			length++;
		}
		
		if(length > maxAllowedLength) {
			employeesWithLongReportingLine.put(employee, length - maxAllowedLength);
		}
	
	}
	
	System.out.println("======Managers earn less than======");
	underpaidManagers.entrySet().forEach(entrySet -> System.out.println(entrySet.getKey().getFirstName()+"---"+entrySet.getValue()));
	
	System.out.println("======Managers earn less than======");
	overpaidManagers.entrySet().forEach(entrySet -> System.out.println(entrySet.getKey().getFirstName()+"---"+entrySet.getValue()));
	
	System.out.println("======Employees reporting long======");
	employeesWithLongReportingLine.entrySet().forEach(entrySet -> System.out.println(entrySet.getKey().getFirstName()+"---"+entrySet.getValue()));
	//
	
}catch(IOException e){
	e.printStackTrace();
}

}
}