package com.answerdigital.irn.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.answerdigital.irn.dto.Entry;
import com.answerdigital.irn.dto.Patient;
import com.answerdigital.irn.dto.ResponseDTO;
import com.answerdigital.irn.dto.SearchDTO;

import javassist.NotFoundException;

public abstract class RestService<DTO extends ResponseDTO> {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${base.url}")
	private String baseUrl;
	
	@Value("${patient.namespace}")
	private String patientNamespace;
	
	@Value("${response.format}")
	private String responseFormat;
	
	@Value("${search.query}")
	private String searchQuery;
	
	private Class<DTO> clazz;
	
	public RestService(Class<DTO> clazz) {
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	public DTO read(String id) throws NotFoundException {
		return (DTO)read(id, clazz);
	}
	
	@SuppressWarnings("unchecked")
	public List<DTO> readAssociated(String nhsNumber) throws NotFoundException {
		Patient patient = readPatient(nhsNumber);
		String url = MessageFormat.format("{0}{1}{2}{3}{4}", baseUrl, clazz.getSimpleName(), "?patient=", patient.getId(), "&_format=json");	
		SearchDTO searchDto = restTemplate.getForObject(url, SearchDTO.class);
		List<DTO> dtos = new ArrayList<>();
		
		for (Entry entry : searchDto.getEntry()) {
			dtos.add((DTO)readEntity(entry.getFullUrl(), clazz));
		}
		
		return dtos;
		
	}
	
	private Patient readPatient(String id) throws NotFoundException {
		return (Patient)read(id, Patient.class);
	}
	
	private Object readEntity(String url, Class<?> clazz) {
		return restTemplate.getForObject(url, clazz);
	}
	
	private Object read(String id, Class<?> c) throws NotFoundException {
		String url = MessageFormat.format("{0}{1}{2}{3}{4}", baseUrl, c.getSimpleName(), searchQuery, c == Patient.class ? patientNamespace : namespace(), id);
		SearchDTO searchDto = restTemplate.getForObject(url, SearchDTO.class);
		
		if (searchDto.getEntry() != null) {
			return readEntity(searchDto.getEntry()[0].getFullUrl(), c);
		} else {
			throw new NotFoundException("Not Found");
		}
	}
	
	public ResponseDTO create(DTO dto) throws Exception {
		dto.setId(null);
		return merge(dto);
	}
	
	public ResponseDTO update(DTO dto) throws Exception {
		return merge(dto);
	}
	
	private ResponseDTO merge(DTO dto) throws Exception {
		boolean isUpdate = dto.getId() != null;
		String id = dto.getIdentifier()[0].getValue();
		DTO originalDto = read(id);
		
		String url = MessageFormat.format("{0}{1}", baseUrl, clazz.getSimpleName());
		url = isUpdate? 
				MessageFormat.format("{0}{1}{2}{3}", url, "/", originalDto.getId(), responseFormat) : 
				MessageFormat.format("{0}{1}", url, responseFormat);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        ResponseEntity<ResponseDTO> response = null;
        
        try {
	        response = restTemplate.exchange(
	        		url, isUpdate ? HttpMethod.PUT : HttpMethod.POST, new HttpEntity<DTO>(dto, headers), ResponseDTO.class);
        } catch (RestClientException e) {
        	throw new Exception();
        }
        
        if (!(response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK)) {
        	throw new Exception();
        }
        
        try {
        	announceEvent(dto);
        } catch (Exception e) {
        	if (response.getBody().getIssue() != null) {
        		String diagnostics = response.getBody().getIssue()[0].getDiagnostics();
        		if (isUpdate) {
        			response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<DTO>(originalDto, headers), ResponseDTO.class);
        		} else {
        			restTemplate.delete(MessageFormat.format("{0}{1}{2}{3}", baseUrl, clazz.getSimpleName(), "/", diagnostics.split("/")[1]));
        		}
        		
        		throw new Exception();
        	}
        }
        
        return response.getBody();
		
	}
	
	private void announceEvent(DTO dto) throws Exception {

	}
	
	protected abstract String namespace();

}
