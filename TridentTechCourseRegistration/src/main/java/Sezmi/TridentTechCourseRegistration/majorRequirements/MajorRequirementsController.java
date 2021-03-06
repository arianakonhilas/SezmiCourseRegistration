//Jeremy Hunton
//Team Sezmi
//9/28/21
//the MajorController will handle RESTful services, API requests, and CRUD services for Major objects. 
package Sezmi.TridentTechCourseRegistration.majorRequirements;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

@RestController					//tag as Rest controller
public class MajorRequirementsController
{
	//autowire the Major Service layer to inject in to controller
	@Autowired
	private MajorRequirementsService service;

	//the list method maps all of the Majors to localhost:8080/major_requirements
	@GetMapping("/major_requirements")
	public List<MajorRequirements> list()
	{
		return service.listAll();
	}

	//the get method maps the individual major to localhost:8080/majors/{id}
	@GetMapping("/major_requirements/{majorid}")
	public ResponseEntity<MajorRequirements> get(@PathVariable String majorid)
	{
		//try to find the major code
		try 
		{
			MajorRequirements majorRequirements = service.get(majorid);			
			return new ResponseEntity<MajorRequirements>(majorRequirements, HttpStatus.OK);		//return the course found and HTTP status as OK.	 
		}//end try block													
		//catch that we cannot find the product
		catch (NoSuchElementException notFound) 		
		{
			return new ResponseEntity<MajorRequirements>(HttpStatus.NOT_FOUND);		//respond that the product was not found and set the HTTP to not found.
		}//end catch no element found
	}

	//This method is responsible for allowing an admin to add major requirements to the major requirements table.
	@PostMapping("/major_requirements")												//@PostMapping assigns the URL link for the POST annotation to the web/server. 
	public void add(@RequestBody MajorRequirements majorRequirements)
	{
		service.save(majorRequirements); 									//call the API service method to save the product to the server. 
	}//end RESTful API create function. 

	//This method is responsible for allowing an admin to update the information by major id in the major table on the server.
	@PutMapping("/major_requirements/{majorid}")											//@PutMapping allows PUT API requests.
	public ResponseEntity<?> update(@RequestBody MajorRequirements majorRequirements, @PathVariable String majorid)
	{
		try 
		{
			MajorRequirements existMajor = service.get(majorid);		//find the existing course by the id and save the new product over it
			service.save(majorRequirements);
			return new ResponseEntity<>(HttpStatus.OK);
		}//end try

		catch (NoSuchElementException e) 
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}//end PUT method for editing a course.

	//RESTful API for Delete Function. This allows the user to issue a DELETE request to remove a major from the server. 
	@DeleteMapping("/major_requirements/{majorId}")
	public void delete(@PathVariable String majorid)
	{
		service.delete(majorid);				//call the delete function from the service to issue the delete command through the pipeline back to the server
	}
}
