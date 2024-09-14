public class Employee {
private Integer id;
private String firstName;
private String lastName;
private long salary;
private Integer managerId;

public Employee(Integer id,String firstName,String lastName,long salary,Integer managerId) {
	this.id=id;
	this.firstName=firstName;
	this.lastName=lastName;
	this.salary=salary;
	this.managerId=managerId;
}

public Integer getId()
{
	return id;
}
public void setId(Integer id)
{
	this.id=id;
}
public String getFirstName()
{
	return firstName;
}
public void setFirstName(String firstName)
{
	this.firstName=firstName;
}

public String getLastName()
{
	return lastName;
}
public void setLastName(String lastName)
{
	this.lastName=lastName;
}
public void setSalary(long salary)
{
	this.salary=salary;
}
public long getSalary()
{
	return salary;
}
public void setManagerId(Integer managerId)
{
	this.managerId=managerId;
}
public Integer getManagerId()
{
	return managerId;
}
}