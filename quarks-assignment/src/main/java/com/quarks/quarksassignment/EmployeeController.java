package com.quarks.quarksassignment;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeServiceImpl employeeService;
	
	@GetMapping("uploadExcel")
	public ModelAndView uploadExcel() {
		ModelAndView mav = new ModelAndView("uploadExcel");
		return mav;
	}
	
	@GetMapping("/data")
	public ModelAndView getExcelData(@RequestParam("name") Optional<String> name, 
			@RequestParam("email") Optional<String> email,
				@RequestParam("phone") Optional<String> phone) {
		List<Employee> employeeList = employeeService.getEmployees();
		List<Employee> filteredEmployeeList = new ArrayList<>();
		
		for(Employee emp : employeeList) {
			if(name.isPresent() && !name.get().isEmpty() && !name.get().equals(emp.getName())) {
				continue;
			}
			
			if(email.isPresent() && !email.get().isEmpty() && !email.get().equals(emp.getEmail())) {
				continue;
			}
			
			if(phone.isPresent() && !phone.get().isEmpty() && !phone.get().equals(emp.getPhone())) {
				continue;
			}
			
			filteredEmployeeList.add(emp);
		}
		
		return showExcelData(filteredEmployeeList);
	}
	
	private ModelAndView showExcelData(List<Employee> employeeList) {
		ModelAndView mav = new ModelAndView("displayExcel");
		mav.addObject("employees",employeeList);
		return mav;
	}
	
	@PostMapping("uploadExcel")
	public ModelAndView uploadExcel(@RequestParam("file") MultipartFile excelFile) throws IOException{
		employeeService.deleteAll(); // empty database with each upload
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		ArrayList<Employee> employeeList = new ArrayList<>();

		/* Load data into database */
		for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			Employee employee = new Employee();
			XSSFRow row = sheet.getRow(i);
			employee.setName(row.getCell(0).getStringCellValue());
			employee.setEmail(row.getCell(1).getStringCellValue());
			employee.setAddress(row.getCell(2).getStringCellValue());
			int phone = (int)sheet.getRow(1).getCell(3).getNumericCellValue();
			employee.setPhone(Integer.toString(phone));
			employeeService.addEmployee(employee);
			employeeList.add(employee);
		}
		return showExcelData(employeeList);
	}
	
	@PostMapping("items/{id}/delete")
	public void deleteRow(@PathVariable long id, HttpServletResponse httpResponse) throws Exception {
		employeeService.deleteRow(id);
		httpResponse.sendRedirect("/data");
	}
	
}
