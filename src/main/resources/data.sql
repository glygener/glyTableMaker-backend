insert into Namespace (namespaceid, name, dictionary) values (1, 'String', null) ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary) values (2, 'Integer', null) ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary) values (3, 'Double', null) ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (4, 'NCBI Taxonomy', 'https://www.ncbi.nlm.nih.gov/taxonomy', FALSE, TRUE, 'species.txt.gz') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary) values (5, 'Paper', null) ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (6, 'UBERON', 'http://purl.obolibrary.org/obo/uberon.owl', TRUE, TRUE, 'uberon-base.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (7, 'Cellosaurus', 'https://www.cellosaurus.org/', FALSE, TRUE, 'cellline.txt.gz') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (8, 'Disease', 'http://purl.obolibrary.org/obo/doid.owl', TRUE, TRUE, 'doid-base.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary) values (9, 'Boolean', null) ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (10, 'Glycan Function', 'https://www.glygen.org/dictionary/glycan_function', FALSE, FALSE, 'function.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (11, 'Experimental technique', 'https://www.glygen.org/dictionary/experimental_technique', FALSE, FALSE, 'experiment.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (12, 'Human Phenotype', 'https://hpo.jax.org/app/', TRUE, TRUE, 'hp.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary) values (13, 'BCO contributor', null) ON CONFLICT DO NOTHING; 
insert into Category (categoryid, name, description) values(1, 'GlyGen Glycomics Data', 'Metadata required for the data export into the GlyGen Glycomics Data format.')  ON CONFLICT DO NOTHING; 
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (2, 'Evidence', 'PMID or DOI', 'https://www.glygen.org/datatype/2', TRUE, 5) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (3, 'Species', 'From NCBI Taxonomy browser', 'https://www.glygen.org/datatype/3', FALSE, 4) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (4, 'Strain', '(Model organisms fly, yeast, mouse)  Add species number to Uniprotlink and get text name from there', 'https://www.glygen.org/datatype/4', FALSE, 1) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (5, 'Tissue', 'From Uberon', 'https://www.glygen.org/datatype/5', FALSE, 6) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (6, 'Cell line ID', 'From Cellosaurus', 'https://www.glygen.org/datatype/6', FALSE, 7) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (7, 'Disease', 'From Human disease ontology', 'https://www.glygen.org/datatype/7', FALSE, 8) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (8, 'Glycan dictionary term ID', '', 'https://www.glygen.org/datatype/8', FALSE, 1) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (9, 'has_abundance', 'Are there Numbers associated with the amount present in a sample', 'https://www.glygen.org/datatype/9', FALSE, 9) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (10, 'has_expression', 'yes or no', 'https://www.glygen.org/datatype/10', FALSE, 9) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (11, 'Functional annotation/Keyword', '', 'https://www.glygen.org/datatype/11', TRUE, 10) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (12, 'Experimental technique', '', 'https://www.glygen.org/datatype/12', FALSE, 11) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (13, 'Variant (Fly, yeast, mouse)', 'Gene name and position (if known) as text', 'https://www.glygen.org/datatype/13', FALSE, 1) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (14, 'Organismal/cellular Phenotype', 'HPO (human) https://hpo.jax.org/app/', 'https://www.glygen.org/datatype/14', FALSE, 12) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (15, 'Molecular Phenotype', 'Gene name', 'https://www.glygen.org/datatype/15', FALSE, 1) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (16, 'Contributor', 'If you are curating the paper use curatedBy, if you are using a software/tool/code to add information to the dataset use createdWith. For final dataset to be integrated GW will use createdBy. If the initial dataset is shared to you by a researcher use contributedBy or authoredBy whichever is applicable as per this. There is no need to add corresponding author or other author name list if curating the paper by yourself.', 'https://www.glygen.org/datatype/16', FALSE, 13) ON CONFLICT DO NOTHING;
insert into datatype_category (datatypeid, categoryid) values (2, 1) ON CONFLICT DO NOTHING;
insert into datatype_category (datatypeid, categoryid) values (3, 1) ON CONFLICT DO NOTHING;
insert into datatype_category (datatypeid, categoryid) values (4, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (5, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (6, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (7, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (8, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (9, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (10, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (11, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (12, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (13, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (14, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (15, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (16, 1) on conflict do nothing;
