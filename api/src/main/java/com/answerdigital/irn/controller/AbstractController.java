package com.answerdigital.irn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.answerdigital.irn.dto.ResponseDTO;
import com.answerdigital.irn.service.RestService;

import javassist.NotFoundException;

public class AbstractController<DTO extends ResponseDTO> {

	private RestService<DTO> restService;
	
	public AbstractController(RestService<DTO> restService) {
		this.restService = restService;
	}
	
	@GetMapping(path="/associated")
	@PreAuthorize("hasAuthority('FOO_READ')")
	public ResponseEntity<List<DTO>> getByAssociation(@RequestParam String associatedType, @RequestParam String id) {
		List<DTO> dtos = restService.readAssociated(associatedType, id);
		return dtos.isEmpty() ? 
				new ResponseEntity<List<DTO>>(HttpStatus.NOT_FOUND) :
				new ResponseEntity<List<DTO>>(dtos, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('FOO_READ')")
	public ResponseEntity<DTO> read(@RequestParam String id) throws NotFoundException {
		DTO dto = restService.read(id);
		return dto == null ? 
				new ResponseEntity<DTO>(HttpStatus.NOT_FOUND) :
				new ResponseEntity<DTO>(dto, HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('FOO_WRITE')")
	public ResponseDTO create(@RequestBody DTO dto) throws Exception {
		return restService.create(dto);
	}
	
	@PutMapping
	@PreAuthorize("hasAuthority('FOO_WRITE')")
	public ResponseDTO update(@RequestBody DTO dto) throws Exception {
		return restService.update(dto);
	}
}
