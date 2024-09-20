package org.glygen.tablemaker.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.glygen.tablemaker.exception.DuplicateException;
import org.glygen.tablemaker.persistence.UserEntity;
import org.glygen.tablemaker.persistence.dao.CollectionRepository;
import org.glygen.tablemaker.persistence.dao.DatasetRepository;
import org.glygen.tablemaker.persistence.dao.DatasetSpecification;
import org.glygen.tablemaker.persistence.dao.DatatypeCategoryRepository;
import org.glygen.tablemaker.persistence.dao.PublicationRepository;
import org.glygen.tablemaker.persistence.dao.TemplateRepository;
import org.glygen.tablemaker.persistence.dao.UserRepository;
import org.glygen.tablemaker.persistence.dataset.DatabaseResource;
import org.glygen.tablemaker.persistence.dataset.Dataset;
import org.glygen.tablemaker.persistence.dataset.DatasetMetadata;
import org.glygen.tablemaker.persistence.dataset.DatasetVersion;
import org.glygen.tablemaker.persistence.dataset.Grant;
import org.glygen.tablemaker.persistence.dataset.Publication;
import org.glygen.tablemaker.persistence.glycan.Collection;
import org.glygen.tablemaker.persistence.glycan.Datatype;
import org.glygen.tablemaker.persistence.glycan.DatatypeCategory;
import org.glygen.tablemaker.persistence.glycan.DatatypeInCategory;
import org.glygen.tablemaker.persistence.glycan.GlycanInCollection;
import org.glygen.tablemaker.persistence.glycan.Metadata;
import org.glygen.tablemaker.persistence.table.TableColumn;
import org.glygen.tablemaker.persistence.table.TableMakerTemplate;
import org.glygen.tablemaker.service.DatasetManager;
import org.glygen.tablemaker.view.CollectionView;
import org.glygen.tablemaker.view.DatasetError;
import org.glygen.tablemaker.view.DatasetInputView;
import org.glygen.tablemaker.view.DatasetView;
import org.glygen.tablemaker.view.Filter;
import org.glygen.tablemaker.view.GlygenMetadataRow;
import org.glygen.tablemaker.view.Sorting;
import org.glygen.tablemaker.view.SuccessResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dataset")
public class DatasetController {
	static Logger logger = org.slf4j.LoggerFactory.getLogger(DatasetController.class);
	
	final private DatasetRepository datasetRepository;
	final private UserRepository userRepository;
	final private TemplateRepository templateRepository;
	final private CollectionRepository collectionRepository;
	private final DatatypeCategoryRepository datatypeCategoryRepository;
	private final DatasetManager datasetManager;
	private final PublicationRepository publicationRepository;
	
	@Value("${spring.file.imagedirectory}")
    String imageLocation;
	
	public DatasetController(DatasetRepository datasetRepository, UserRepository userRepository, TemplateRepository templateRepository, CollectionRepository collectionRepository, DatatypeCategoryRepository datatypeCategoryRepository, DatasetManager datasetManager, PublicationRepository publicationRepository) {
		this.datasetRepository = datasetRepository;
		this.userRepository = userRepository;
		this.templateRepository = templateRepository;
		this.collectionRepository = collectionRepository;
		this.datatypeCategoryRepository = datatypeCategoryRepository;
		this.datasetManager = datasetManager;
		this.publicationRepository = publicationRepository;
	}
	
	@Operation(summary = "Get user's public datasets", security = { @SecurityRequirement(name = "bearer-key") })
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
        // get user info
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;
        if (auth != null) { 
            user = userRepository.findByUsernameIgnoreCase(auth.getName());
        }
      
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
        	
        	spec = Specification.where(spec).and(DatasetSpecification.hasUserWithId(user.getUserId()));
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
        	datasetsInPage = datasetRepository.findAllByUser(user, PageRequest.of(start, size, Sort.by(sortOrders)));
        }
        
        List<DatasetView> datasets = new ArrayList<>();
        for (Dataset d: datasetsInPage.getContent()) {
        	DatasetView dv = createDatasetView (d, null);
        	datasets.add(dv);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("objects", datasets);
        response.put("currentPage", datasetsInPage.getNumber());
        response.put("totalItems", datasetsInPage.getTotalElements());
        response.put("totalPages", datasetsInPage.getTotalPages());
        
        return new ResponseEntity<>(new SuccessResponse(response, "datasets retrieved"), HttpStatus.OK);
    }
	
	@SuppressWarnings("unchecked")
	@Operation(summary = "Get collections for dataset", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/getcollections")
    public ResponseEntity<SuccessResponse> getCollections(
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
        // get user info
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;
        if (auth != null) { 
            user = userRepository.findByUsernameIgnoreCase(auth.getName());
        }
      
        Map<String, Object> response = DataController.getCollections(user, collectionRepository, imageLocation, start, size, filters, globalFilter, sorting);
        List<CollectionView> collections = (List<CollectionView>) response.get("objects");
        
        //populate errors/warnings
        for (CollectionView col: collections) {
        	getErrorsForCollection(col);
        }
        
        return new ResponseEntity<>(new SuccessResponse(response, "collections retrieved"), HttpStatus.OK);
    }
	
	@Operation(summary = "Publish dataset", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/publishdataset")
    public ResponseEntity<SuccessResponse> publishDataset(@Valid @RequestBody DatasetInputView d) throws MethodArgumentNotValidException {
    	// get user info
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;
        if (auth != null) { 
            user = userRepository.findByUsernameIgnoreCase(auth.getName());
        }
        
        if (d.getLicense() == null || d.getCollections() == null || d.getCollections().isEmpty()) {
        	// validation error
        	BeanPropertyBindingResult result = new BeanPropertyBindingResult(d, "dataset input");
        	if (d.getLicense() == null)
        		result.addError(new ObjectError("license", "cannot be null"));
        	if (d.getCollections() == null || d.getCollections().isEmpty()) {
        		result.addError(new ObjectError("collections", "cannot be left empty"));
        	}
        	throw new MethodArgumentNotValidException(null, result);
        }
        
        // save the dataset
        
        // check for errors in the collections
        StringBuffer errorMessage = new StringBuffer();
        for (CollectionView cv: d.getCollections()) {
        	getErrorsForCollection (cv);
        	if (!cv.getErrors().isEmpty()) {
        		errorMessage.append ("Collection" + cv.getName() + " has errors!\n");       		
        	}
        }
        if (!errorMessage.isEmpty()) {
        	throw new IllegalArgumentException (errorMessage.toString().trim());
        }
        
        Dataset newDataset = new Dataset();
        newDataset.setName(d.getName());
        newDataset.setDescription(d.getDescription());
        newDataset.setAssociatedDatasources(d.getAssociatedDatasources());
        newDataset.setAssociatedPapers(d.getAssociatedPapers());
        newDataset.setGrants(d.getGrants());
        newDataset.setUser(user);
        newDataset.setVersions(new ArrayList<>());
        newDataset.setDateCreated(new Date());
        
        DatasetVersion version = new DatasetVersion();
        version.setHead(true);
        version.setVersion("");
        version.setVersionDate(new Date());
        version.setLicense(d.getLicense());
        version.setPublications(d.getPublications());
        
        newDataset.getVersions().add(version);
        
        version.setData(generateData(version, d.getCollections()));
        version.setDataset(newDataset);
        
        Dataset saved = datasetManager.saveDataset (newDataset);
        
        return new ResponseEntity<>(new SuccessResponse(saved, "dataset has been published"), HttpStatus.OK);
	}
	
	List<DatasetMetadata> generateData (DatasetVersion version, List<CollectionView> collections) {
		List<DatasetMetadata> metadata = new ArrayList<>();
		TableMakerTemplate glygenTemplate = templateRepository.findById(1L).get();
		// generate the data
        for (CollectionView cv: collections) {
        	Optional<Collection> collectionHandle = collectionRepository.findById(cv.getCollectionId());
        	Collection collection = collectionHandle.get();
        	for (GlycanInCollection g: collection.getGlycans()) {
        		for (TableColumn col: glygenTemplate.getColumns()) {
        			DatasetMetadata dm = new DatasetMetadata();
            		dm.setDataset(version);
            		dm.setRowId(collection.getCollectionId() + "-" + g.getGlycan().getGlycanId());
        			if (col.getGlycanColumn() != null) {
        				switch (col.getGlycanColumn()) {
        				case GLYTOUCANID:  // check to make sure all glycans have this value
        					dm.setGlycanColumn(col.getGlycanColumn());
        					dm.setValue(g.getGlycan().getGlytoucanID());
        					break;
        				case CARTOON:
        					break;
        				case MASS:
        					break;
        				default:
        					break;
        				}
        			} else {
        				dm.setDatatype(col.getDatatype());
        				for (Metadata m: collection.getMetadata()) {
        					if (m.getType().getDatatypeId() == col.getDatatype().getDatatypeId()) {
        						dm.setValue(m.getValue());
        						dm.setValueId(m.getValueId());
        						dm.setValueUri(m.getValueUri());
        						break;
        					}
        				}
        			}	
        			metadata.add(dm);
        		}
			}
        }
        return metadata;
	}
	
	@Operation(summary = "update dataset", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/updatedataset")
    public ResponseEntity<SuccessResponse> updateDataset(@Valid @RequestBody DatasetInputView d) {
    	if (d.getId() == null) {
    		throw new IllegalArgumentException("Dataset id should be provided for update");
    	}
    	// get user info
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;
        if (auth != null) { 
            user = userRepository.findByUsernameIgnoreCase(auth.getName());
        }
    	Dataset existing = datasetRepository.findByDatasetIdAndUser(d.getId(), user);
    	if (existing == null) {
    		throw new IllegalArgumentException("Dataset (" + d.getId() + ") to be updated cannot be found");
    	}
    	// check if name is a duplicate
    	if (!existing.getName().equalsIgnoreCase(d.getName())) {   // changing the name
	    	List<Dataset> duplicate = datasetRepository.findAllByNameAndUser (d.getName(), user);
	    	if (!duplicate.isEmpty()) {
	    		throw new DuplicateException("Dataset with name: " + d.getName() + " already exists! Pick a different name");
	    	}
    	}
    	existing.setName(d.getName());
    	existing.setDescription(d.getDescription());
    	
    	// update grants/associated databases/associated papers
		if (existing.getAssociatedDatasources() == null) {
			existing.setAssociatedDatasources(new ArrayList<>());
		}
		
		if (d.getAssociatedDatasources() == null || d.getAssociatedDatasources().isEmpty()) {
			existing.getAssociatedDatasources().clear();
		} else {
			// remove resources as necessary
    		List<DatabaseResource> toBeRemoved = new ArrayList<>();
	    	for (DatabaseResource dr: existing.getAssociatedDatasources()) {
	    		boolean found = false;
	    		for (DatabaseResource d2: d.getAssociatedDatasources()) {
	    			if (d2.getId().equals(dr.getId())) {
	    				// keep it
	    				found = true;
	    			}
	    		}
	    		if (!found) {
	    			toBeRemoved.add(dr);
	    		}
	    	}
	    	existing.getAssociatedDatasources().removeAll(toBeRemoved);
		}
		
		if (d.getAssociatedDatasources() != null && !d.getAssociatedDatasources().isEmpty()) {
    		// check if this metadata already exists in the collection
    		for (DatabaseResource m: d.getAssociatedDatasources()) {
    			boolean exists = false;
    			for (DatabaseResource m2: existing.getAssociatedDatasources()) {
        			if (m2.getId() != null && m2.getId().equals(m.getId())) {
        				exists = true;
        				break;
        			}
        		}
    			if (!exists) {
    				existing.getAssociatedDatasources().add(m);
    			}
    		}
		}
		
		if (existing.getAssociatedPapers() == null) {
			existing.setAssociatedPapers(new ArrayList<>());
		}
		
		if (d.getAssociatedPapers() == null || d.getAssociatedPapers().isEmpty()) {
			existing.getAssociatedPapers().clear();
		} else {
			// remove papers as necessary
    		List<Publication> toBeRemoved = new ArrayList<>();
	    	for (Publication dr: existing.getAssociatedPapers()) {
	    		boolean found = false;
	    		for (Publication d2: d.getAssociatedPapers()) {
	    			if (d2.getId().equals(dr.getId())) {
	    				// keep it
	    				found = true;
	    			}
	    		}
	    		if (!found) {
	    			toBeRemoved.add(dr);
	    		}
	    	}
	    	existing.getAssociatedPapers().removeAll(toBeRemoved);
		}
		
		if (d.getAssociatedPapers() != null && !d.getAssociatedPapers().isEmpty()) {
    		// check if this metadata already exists in the collection
    		for (Publication m: d.getAssociatedPapers()) {
    			boolean exists = false;
    			for (Publication m2: existing.getAssociatedPapers()) {
        			if (m2.getId() != null && m2.getId().equals(m.getId())) {
        				exists = true;
        				break;
        			}
        		}
    			if (!exists) {
    				existing.getAssociatedPapers().add(m);
    			}
    		}
		}
		
		if (existing.getGrants() == null) {
			existing.setGrants(new ArrayList<>());
		}
		
		if (d.getGrants() == null || d.getGrants().isEmpty()) {
			existing.getGrants().clear();
		} else {
			// remove grants as necessary
    		List<Grant> toBeRemoved = new ArrayList<>();
	    	for (Grant dr: existing.getGrants()) {
	    		boolean found = false;
	    		for (Grant d2: d.getGrants()) {
	    			if (d2.getId().equals(dr.getId())) {
	    				// keep it
	    				found = true;
	    			}
	    		}
	    		if (!found) {
	    			toBeRemoved.add(dr);
	    		}
	    	}
	    	existing.getGrants().removeAll(toBeRemoved);
		}
		
		if (d.getGrants() != null && !d.getGrants().isEmpty()) {
    		// check if this metadata already exists in the collection
    		for (Grant m: d.getGrants()) {
    			boolean exists = false;
    			for (Grant m2: existing.getGrants()) {
        			if (m2.getId() != null && m2.getId().equals(m.getId())) {
        				exists = true;
        				break;
        			}
        		}
    			if (!exists) {
    				existing.getGrants().add(m);
    			}
    		}
		}
    	
    	if (d.getLicense() != null || d.getCollections() != null) {   // changing the license and/or data, need to create a new version
    		// create a new version
    		int versionNo = existing.getVersions().size();  // there must be a head version already
    		DatasetVersion head = null;
    		for (DatasetVersion v: existing.getVersions()) {
    			if (v.getHead()) {
    				head = v;
    				break;
    			}
    		}
    		DatasetVersion version = new DatasetVersion();
    		version.setHead(true);
    		version.setDataset(existing);
    		version.setVersion("");
    		head.setComment(d.getChangeComment());
    		head.setVersionDate(new Date());
    		head.setVersion(versionNo +"");
    		head.setHead(false);
    		if (d.getLicense() != null) {
    			version.setLicense(d.getLicense());
    		} else {
    			version.setLicense(head.getLicense());
    		}
    		
    		if (d.getCollections() == null) {
    			version.setData(new ArrayList<>());
    			// data is the same, copy data, errors and publications to the new version
    			for (DatasetMetadata m: head.getData()) {
    				DatasetMetadata copy = new DatasetMetadata(m);
    				copy.setDataset(version);
    				version.getData().add(copy);
    			}	
    			version.setPublications(new ArrayList<>());
    			for (Publication p: head.getPublications()) {
    				version.getPublications().add(p);
    			}
    		} else {
    			// create new metadata from the selected collections
    			
    			// check for errors in the collections
    	        StringBuffer errorMessage = new StringBuffer();
    	        for (CollectionView cv: d.getCollections()) {
    	        	getErrorsForCollection (cv);
    	        	if (!cv.getErrors().isEmpty()) {
    	        		errorMessage.append ("Collection" + cv.getName() + " has errors!\n");       		
    	        	}
    	        }
    	        if (!errorMessage.isEmpty()) {
    	        	throw new IllegalArgumentException (errorMessage.toString().trim());
    	        }
    	        
    	        version.setData(generateData(version, d.getCollections()));
    	        
    	        version.setPublications(new ArrayList<>());
    	        for (DatasetMetadata m: version.getData()) {
    	        	// find the publications
    	        	if (m.getDatatype() != null && m.getDatatype().getName().equals("Evidence")) {
    	        		try {
							Publication pub = UtilityController.getPublication(m.getValue(), publicationRepository);
							if (pub != null && !version.getPublications().contains(pub)) {
								version.getPublications().add(pub);
							}
						} catch (Exception e) {
							logger.error("Failed to retrieve the publication", e);
						}
    	        	}
    	        }
    		}
    		existing.getVersions().add(version);
    	}
    	
		Dataset saved = datasetManager.saveDataset(existing);
    	
    	DatasetView dv = createDatasetView(saved, null);
    	return new ResponseEntity<>(new SuccessResponse(dv, "dataset updated"), HttpStatus.OK);
	}
	
	@Operation(summary = "Get dataset by the given id", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/getdataset/{datasetIdentifier}")
    public ResponseEntity<SuccessResponse> getDataset(
    		@Parameter(required=true, description="id of the dataseet to be retrieved") 
    		@PathVariable("datasetIdentifier") String datasetIdentifier) {
    	// get user info
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;
        if (auth != null) { 
            user = userRepository.findByUsernameIgnoreCase(auth.getName());
        }
        
        String identifier = datasetIdentifier;
        String version = "";
        // check if the identifier contains a version
        String[] split = datasetIdentifier.split("-");
        if (split.length > 1) {
        	identifier = split[0];
        	version = split[1];
        }
        Dataset existing = datasetRepository.findByDatasetIdentifierAndUserAndVersions_version(identifier, user, version);
        if (existing == null) {
            throw new IllegalArgumentException ("Could not find the given dataset " + datasetIdentifier + " for the user");
        }
        
        DatasetView dv = createDatasetView (existing, version);
        
        return new ResponseEntity<>(new SuccessResponse(dv, "dataset retrieved"), HttpStatus.OK);
    }
	
    public static DatasetView createDatasetView(Dataset d, String versionString) {
    	boolean head = false;
    	if (versionString == null || versionString.isEmpty()) 
    		head = true;
    	
    	DatasetView dv = new DatasetView();
    	dv.setId(d.getDatasetId());
    	dv.setDatasetIdentifier(d.getDatasetIdentifier());
    	dv.setName(d.getName());
    	dv.setDescription(d.getDescription());
    	dv.setRetracted(d.getRetracted());
    	dv.setDateCreated(d.getDateCreated());
    	dv.setUser(d.getUser());
    	if (d.getAssociatedDatasources() != null) dv.setAssociatedDatasources(new ArrayList<>(d.getAssociatedDatasources()));
    	if (d.getGrants() != null) dv.setGrants(new ArrayList<>(d.getGrants()));
    	if (d.getAssociatedPapers() != null) dv.setAssociatedPapers(new ArrayList<>(d.getAssociatedPapers()));
    	if (d.getIntegratedIn() != null) dv.setIntegratedIn(new ArrayList<>(d.getIntegratedIn()));
    	
    	
    	for (DatasetVersion version : d.getVersions()) {
    		if ((head && version.getHead()) || (!head && version.getVersion().equalsIgnoreCase(versionString))) {
    			dv.setLicense(version.getLicense());
    			dv.setVersion(version.getVersion());
    			dv.setVersionDate(version.getVersionDate());
    			dv.setVersionComment(version.getComment());
    			if (version.getData() != null) {
    				dv.setData(new ArrayList<>());
    				Map<String, List<DatasetMetadata>> rowMap = new HashMap<>();
    				for (DatasetMetadata m: version.getData()) {
    					if (rowMap.get(m.getRowId()) == null) {
    						rowMap.put(m.getRowId(), new ArrayList<>());
    					}
    					rowMap.get(m.getRowId()).add(m);
    				}
    				for (String key: rowMap.keySet()) {
    					GlygenMetadataRow row = new GlygenMetadataRow();
    					row.setRowId(key);
    					row.setColumns(rowMap.get(key));
    					dv.getData().add(row);
    				}
    			}
    			dv.setNoGlycans(dv.getData() != null ? dv.getData().size() : 0);
    			if (version.getPublications() != null) dv.setPublications(new ArrayList<>(version.getPublications()));
    			break;
    		} 
    	}
    	
    	if (d.getVersions() != null) dv.setVersions(new ArrayList<>(d.getVersions()));
		return dv;
	}

	public void getErrorsForCollection(CollectionView cv) {
		List<DatasetError> errorList = new ArrayList<>();
		List<DatasetError> warningList = new ArrayList<>();
		
		Optional<TableMakerTemplate> glygenTemplate = templateRepository.findById(1L);
		if (!glygenTemplate.isPresent()) {
			throw new RuntimeException ("GlyGen template is not found!");
		}
		
		Optional<DatatypeCategory> glygenCatHandle = datatypeCategoryRepository.findById(1L);
		if (!glygenCatHandle.isPresent()) {
			throw new RuntimeException ("GlyGen datatype category is not found!");
		}
		
		Optional<Collection> collectionHandle = collectionRepository.findById(cv.getCollectionId());
		if (!collectionHandle.isPresent()) {
			throw new IllegalArgumentException("Given collection " + cv.getCollectionId() + " cannot be found!");
		}
		
		Collection collection = collectionHandle.get();
		TableMakerTemplate template = glygenTemplate.get();
		DatatypeCategory glygenCategory = glygenCatHandle.get();
		for (TableColumn col: template.getColumns()) {
			if (col.getGlycanColumn() != null) {
				switch (col.getGlycanColumn()) {
				case GLYTOUCANID:  // check to make sure all glycans have this value
					for (GlycanInCollection g: collection.getGlycans()) {
						if (g.getGlycan().getGlytoucanID() == null) {
							// error
							errorList.add(new DatasetError("Glycan " + g.getGlycan().getGlycanId() + " in collection " + col.getName() + " does not have a value for GlytoucanID.", 1));
						}
					}
					break;
				case CARTOON:
					break;
				case MASS:
					break;
				default:
					break;
				}
			} else {
				boolean found = false;
				for (Metadata meta: collection.getMetadata()) {
					if (meta.getType().getDatatypeId() == col.getDatatype().getDatatypeId()) {
						if ((meta.getValue() != null && !meta.getValue().isEmpty())
								|| (meta.getValueId() != null && !meta.getValueId().isEmpty()) 
								|| (meta.getValueUri() != null && !meta.getValueUri().isEmpty())) {
							found = true;
						}
					}
				}
				if (!found) {
					// check if mandatory
					int errorLevel = isMandatory(col.getDatatype(), glygenCategory) ? 1: 0;
					DatasetError err = new DatasetError(collection.getName() + " does not have metadata for \"" + col.getName() + "\" column.", errorLevel);
					if (errorLevel == 0) {
						warningList.add(err);
					}
					else errorList.add (err);
				}
			}	
		}
		cv.setErrors(errorList);
		cv.setWarnings(warningList);
	}

	private boolean isMandatory(Datatype datatype, DatatypeCategory glygenCategory) {
		for (DatatypeInCategory dc : glygenCategory.getDataTypes()) {
			if (dc.getDatatype().getDatatypeId() == datatype.getDatatypeId())
				return dc.getMandatory() == null ? false : dc.getMandatory();
		}
		return false;
	}

}
