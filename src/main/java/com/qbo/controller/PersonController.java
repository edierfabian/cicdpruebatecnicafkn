package com.qbo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qbo.exception.ResourceNotFoundException;
import com.qbo.model.Person;
import com.qbo.service.PersonService;

@RestController
@RequestMapping("/apiqbo/v1")
public class PersonController {
	
	@Autowired
	PersonService personService;
	
	
	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getAllPersons(){
		
		List<Person> persons=new ArrayList<>();
		personService.getAllPersons().forEach(persons::add);
		if(persons.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
		
	}
	
	@GetMapping("/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable ("id") Integer id) 
			throws ResourceNotFoundException{
		
		Person person= personService
				.findById(id)
				.orElseThrow(()-> 
				new ResourceNotFoundException("Not Found Person Whit id = "+id));
		return new ResponseEntity<>(person,HttpStatus.OK);
		
		
	}
	
	@PostMapping("/persons")
	public ResponseEntity<Person> createPerson(@RequestBody Person person){
		Person _person =personService.save(person);
		return new ResponseEntity<> (_person,HttpStatus.CREATED);
		
	}

	@PutMapping("/person/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable("id") Integer id,
			@RequestBody Person person ) throws ResourceNotFoundException{
				
		Person oldPerson=personService.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Not Found Personwith id= "+id));
		return new ResponseEntity<> (personService.update(oldPerson, person),HttpStatus.OK);
	}
	
	@DeleteMapping("/person/{id}")
	public ResponseEntity<Person> deletePerson(@PathVariable("id") Integer id) 
	throws ResourceNotFoundException{
		
		Person person=personService.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Not found Person with ID"+id));
				
		
		return new ResponseEntity<> (personService.delete(person),HttpStatus.NO_CONTENT);
				
	}
	
	@DeleteMapping("/person")
	public ResponseEntity<Person> deleteAllPerson(){
		
		personService.deleteAll();
				
		return new ResponseEntity<> (HttpStatus.NO_CONTENT);
				
	}
	
}
