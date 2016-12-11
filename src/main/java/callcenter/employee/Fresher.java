package callcenter.employee;

import callcenter.service.CallCenterService.EmployeeType;

public class Fresher extends Employee {

  public Fresher(String name) {
    super(name);
    setType(EmployeeType.FRESHER);
  }

}
