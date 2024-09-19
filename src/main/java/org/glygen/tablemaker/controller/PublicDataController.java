package org.glygen.tablemaker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glygen.tablemaker.persistence.dao.DatasetRepository;
import org.glygen.tablemaker.persistence.dao.DatasetSpecification;
import org.glygen.tablemaker.persistence.dataset.Dataset;
import org.glygen.tablemaker.view.DatasetView;
import org.glygen.tablemaker.view.Filter;
import org.glygen.tablemaker.view.Sorting;
import org.glygen.tablemaker.view.SuccessResponse;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/public")
public class PublicDataController {
	static Logger logger = org.slf4j.LoggerFactory.getLogger(PublicDataController.class);
	
	final private DatasetRepository datasetRepository;
	
	public PublicDataController(DatasetRepository datasetRepository) {
		this.datasetRepository = datasetRepository;
	}
	
	@Operation(summary = "Get public datasets", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/getdatasets")
    public ResponseEntity<SuccessResponse> getDatasets(
            @RequestParam("start")
            Integer start, 
            @RequestParam("size")
            Integer size,
            @RequestParam("filters")
            String filters,
            @RequestParam("globalFilter")
            String globalFilter,
            @RequestParam("sorting")
            String sorting) {
		// parse filters and sorting
        ObjectMapper mapper = new ObjectMapper();
        List<Filter> filterList = null;
        if (filters != null && !filters.equals("[]")) {
            try {
                filterList = mapper.readValue(filters, 
                    new TypeReference<ArrayList<Filter>>() {});
            } catch (JsonProcessingException e) {
                throw new InternalError("filter parameter is invalid " + filters, e);
            }
        }
        List<Sorting> sortingList = null;
        List<Order> sortOrders = new ArrayList<>();
        if (sorting != null && !sorting.equals("[]")) {
            try {
                sortingList = mapper.readValue(sorting, 
                    new TypeReference<ArrayList<Sorting>>() {});
                for (Sorting s: sortingList) {
                    sortOrders.add(new Order(s.getDesc() ? Direction.DESC: Direction.ASC, s.getId()));
                }
            } catch (JsonProcessingException e) {
                throw new InternalError("sorting parameter is invalid " + sorting, e);
            }
        }
        
        // apply filters
        List<DatasetSpecification> specificationList = new ArrayList<>();
        if (filterList != null) {
	        for (Filter f: filterList) {
	        	DatasetSpecification spec = new DatasetSpecification(f);
	        	specificationList.add(spec);
	        }
        }
        
        if (globalFilter != null && !globalFilter.isBlank() && !globalFilter.equalsIgnoreCase("undefined")) {
        	specificationList.add(new DatasetSpecification(new Filter ("name", globalFilter)));
        	specificationList.add(new DatasetSpecification(new Filter ("datasetIdentifier", globalFilter)));
        }
        
        Specification<Dataset> spec = null;
        if (!specificationList.isEmpty()) {
        	spec = specificationList.get(0);
        	for (int i=1; i < specificationList.size(); i++) {
        		spec = Specification.where(spec).or(specificationList.get(i)); 
        	}
        }
        
        Page<Dataset> datasetsInPage = null;
        if (spec != null) {
        	try {
        		datasetsInPage = datasetRepository.findAll(spec, PageRequest.of(start, size, Sort.by(sortOrders)));
        	} catch (Exception e) {
        		logger.error(e.getMessage(), e);
        		throw e;
        	}
        } else {
        	datasetsInPage = datasetRepository.findAll(PageRequest.of(start, size, Sort.by(sortOrders)));
        }
        
        List<DatasetView> datasets = new ArrayList<>();
        for (Dataset d: datasetsInPage.getContent()) {
        	DatasetView dv = DatasetController.createDatasetView (d, null);
        	datasets.add(dv);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("objects", datasets);
        response.put("currentPage", datasetsInPage.getNumber());
        response.put("totalItems", datasetsInPage.getTotalElements());
        response.put("totalPages", datasetsInPage.getTotalPages());
        
        return new ResponseEntity<>(new SuccessResponse(response, "datasets retrieved"), HttpStatus.OK);
	}
	

}
