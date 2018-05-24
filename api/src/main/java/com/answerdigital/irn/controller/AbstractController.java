package com.answerdigital.irn.controller;

import java.util.List;

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
	public List<DTO> getByNHSNumber(@RequestParam String nhsNumber) {
		return restService.readAssociated(nhsNumber);
	}

	@GetMapping
	public DTO read(@RequestParam String id) throws NotFoundException {
		DTO dto = restService.read(id);
		if (dto == null) {
			throw new NotFoundException("Not Found");
		}
		
		return dto;
	}
	
	@PostMapping
	public ResponseDTO create(@RequestBody DTO dto) throws Exception {
		return restService.create(dto);
	}
	
	@PutMapping
	public ResponseDTO update(@RequestBody DTO dto) throws Exception {
		return restService.update(dto);
	}
}
