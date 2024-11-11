insert into Namespace (namespaceid, name, dictionary, description) values (1, 'String', null, 'Free text') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, description) values (2, 'Integer', null, 'Whole numbers only') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, description) values (3, 'Double', null, 'Floating-point numbers, which are numbers with a decimal point that can be expressed as a fraction') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (4, 'NCBI Taxonomy', 'https://www.ncbi.nlm.nih.gov/taxonomy', FALSE, TRUE, 'species.txt.gz') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary) values (5, 'Paper', null) ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (6, 'UBERON', 'http://purl.obolibrary.org/obo/uberon.owl', TRUE, TRUE, 'uberon-base.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (7, 'Cellosaurus', 'https://www.cellosaurus.org/', FALSE, TRUE, 'cellline.txt.gz') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (8, 'Disease', 'http://purl.obolibrary.org/obo/doid.owl', TRUE, TRUE, 'doid-base.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, description, hasUri, hasId, fileIdentifier) values (9, 'Boolean', null, 'For true/false or yes/no values', FALSE, FALSE, 'boolean.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (10, 'Glycan Function', 'https://www.glygen.org/dictionary/glycan_function', FALSE, FALSE, 'function.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (11, 'Experimental technique', 'https://www.glygen.org/dictionary/experimental_technique', FALSE, FALSE, 'experiment.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary, hasUri, hasId, fileIdentifier) values (12, 'Human Phenotype', 'https://hpo.jax.org/app/', TRUE, TRUE, 'hp.txt') ON CONFLICT DO NOTHING; 
insert into Namespace (namespaceid, name, dictionary) values (13, 'BCO contributor', null) ON CONFLICT DO NOTHING; 
insert into Category (categoryid, name, description) values(1, 'GlyGen Glycomics Data', 'Metadata required for the data export into the GlyGen Glycomics Data format.')  ON CONFLICT DO NOTHING; 
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (2, 'Evidence', 'PMID (PubMed Identifier) is a unique numerical identifier assigned to an article in PubMed. DOI (Digital Object Identifier) is an alphanumeric string assigned to online content, such as articles, book chapters, or data sets.', 'https://www.glygen.org/datatype/2', TRUE, 5) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (3, 'Species', 'A species is the broadest classification and refers to a group of organisms that can interbreed and produce fertile offspring. The Taxonomy ID is a unique numerical identifier assigned by the NCBI to each species. To retrieve the Taxonomy ID, search the NCBI Taxonomy Browser database for the organism’s common name or scientific name. For example, searching for the common name “Humans” or the scientific name “Homo sapiens” will retrieve the taxonomy ID 9606.', 'https://www.glygen.org/datatype/3', FALSE, 4) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (4, 'Strain', 'A strain is a subtype of a species that have accumulated enough genetic differences to exhibit distinct characteristics, common in model organisms.  Search the UniProt website for the species’ Tax ID to select the strain. For instance, C57BL/6 is a commonly used inbred strain of Mus musculus (house mouse) in research.', 'https://www.glygen.org/datatype/4', FALSE, 1) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (5, 'Tissue', 'An Uberon ID is a unique identifier assigned to tissues. This ontology encompasses a wide range of anatomical structures across different species. For instance, the Uberon ID for the human heart is UBERON:0000948.', 'https://www.glygen.org/datatype/5', FALSE, 6) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (6, 'Cell line ID', 'Cellosaurus accession numbers are unique primary identifiers used to unambiguously reference specific cell lines. These numbers follow the format “CVCL_” followed by four alphanumeric characters (e.g., CVCL_0E45).', 'https://www.glygen.org/datatype/6', FALSE, 7) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (7, 'Disease', 'The Disease Ontology ID (DOID) is a unique identifier for human diseases, providing standardized descriptions for the biomedical community. You can search for common disease names and select the corresponding DOID. For example, the DOID for Alzheimer’s disease is DOID:10652.', 'https://www.glygen.org/datatype/7', FALSE, 8) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (8, 'Glycan dictionary term ID', '', 'https://www.glygen.org/datatype/8', FALSE, 1) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (9, 'has_abundance', 'Are there numbers associated with the amount present in a sample', 'https://www.glygen.org/datatype/9', FALSE, 9) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (10, 'has_expression', 'yes or no', 'https://www.glygen.org/datatype/10', FALSE, 9) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (11, 'Functional annotation/Keyword', '', 'https://www.glygen.org/datatype/11', TRUE, 10) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (12, 'Experimental technique', '', 'https://www.glygen.org/datatype/12', TRUE, 11) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (13, 'Variant (Fly, yeast, mouse)', 'A variant refers to a version of a species or strain that has undergone mutations. These mutations can be minor or significant but do not necessarily change the organism’s behavior or properties drastically. For example, a mutation in the gene responsible for fur color might result in a variant with black fur instead of the typical brown fur in mice.', 'https://www.glygen.org/datatype/13', FALSE, 1) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (14, 'Organismal/cellular Phenotype', 'The Human Phenotype Ontology (HPO) offers a standardized vocabulary to describe phenotypic abnormalities observed in human diseases. One such phenotype is Polydactyly (HP:0001166), which refers to the presence of extra fingers or toes.', 'https://www.glygen.org/datatype/14', FALSE, 12) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (15, 'Molecular Phenotype', 'Gene name', 'https://www.glygen.org/datatype/15', FALSE, 1) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (16, 'Contributor', 'If you are curating the paper use curatedBy, if you are using a software/tool/code to add information to the dataset use createdWith. For final dataset to be integrated GW will use createdBy. If the initial dataset is shared to you by a researcher use contributedBy or authoredBy whichever is applicable as per this. There is no need to add corresponding author or other author name list if curating the paper by yourself.', 'https://www.glygen.org/datatype/16', FALSE, 13) ON CONFLICT DO NOTHING;
insert into Datatype (datatypeid, name, description, uri, multiple, namespaceid) values (17, 'Comment', 'Additional comments', 'https://www.glygen.org/datatype/17', FALSE, 1) ON CONFLICT DO NOTHING;
insert into datatype_category (datatypeid, categoryid) values (2, 1) ON CONFLICT DO NOTHING;
insert into datatype_category (datatypeid, categoryid, mandatory) values (3, 1, TRUE) ON CONFLICT DO NOTHING;
insert into datatype_category (datatypeid, categoryid) values (4, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (5, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (6, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (7, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (8, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid, mandatory) values (9, 1, TRUE) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid, mandatory) values (10, 1, TRUE) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (11, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid, mandatory) values (12, 1, TRUE) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (13, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (14, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (15, 1) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid, mandatory) values (16, 1, TRUE) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (17, 1) on conflict do nothing;

insert into Category (categoryid, name, description) values(2, 'GlyGen Glycoproteomics Data', 'Metadata required for the data export into the GlyGen Glycoproteomics Data format.')  ON CONFLICT DO NOTHING; 
insert into datatype_category (datatypeid, categoryid) values (2, 2) ON CONFLICT DO NOTHING;
insert into datatype_category (datatypeid, categoryid, mandatory) values (3, 2, TRUE) ON CONFLICT DO NOTHING;
insert into datatype_category (datatypeid, categoryid) values (4, 2) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (5, 2) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (6, 2) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (7, 2) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (11, 2) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid, mandatory) values (12, 2, TRUE) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid, mandatory) values (16, 2, TRUE) on conflict do nothing;
insert into datatype_category (datatypeid, categoryid) values (17, 2) on conflict do nothing;

insert into tablemakertemplate (templateid, name, description) values (1, 'GlyGen Glycomics', 'Template for submitting glycans to GlyGen') on conflict do nothing;
insert into table_column (columnid, name, glycancolumn, column_order) values (1, 'GlyTouCan ID', 'GLYTOUCANID',  1) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (2, 'Evidence', 2, 'VALUE', 2) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (3, 'Species', 3, 'ID', 3) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (4, 'Strain', 4, 'VALUE', 4) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (5, 'Tissue', 5, 'ID', 5) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (6, 'Cell line ID', 6, 'ID', 5) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (7, 'Disease', 7, 'ID', 7) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (8, 'Glycan dictionary term ID', 8, 'VALUE', 8) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (9, 'has_abundance', 9, 'VALUE', 9) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (10, 'has_expression', 10, 'VALUE', 10) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (11, 'Functional annotation/Keyword', 11, 'VALUE', 11) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (12, 'Experimental technique', 12, 'VALUE', 12) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (13, 'Variant (Fly, yeast, mouse)', 13, 'VALUE', 13) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (14, 'Organismal/cellular Phenotype', 14, 'ID', 14) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (15, 'Molecular Phenotype', 15, 'VALUE', 15) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (16, 'Contributor', 16, 'VALUE', 16) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (17, 'Comment', 17, 'VALUE', 17) ON CONFLICT DO NOTHING;
insert into tablemakertemplate_table_column (columnid, templateid) values (1, 1) ON CONFLICT DO NOTHING;
insert into tablemakertemplate_table_column (columnid, templateid) values (2, 1) ON CONFLICT DO NOTHING;
insert into tablemakertemplate_table_column (columnid, templateid) values (3, 1) ON CONFLICT DO NOTHING;
insert into tablemakertemplate_table_column (columnid, templateid) values (4, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (5, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (6, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (7, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (8, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (9, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (10, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (11, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (12, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (13, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (14, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (15, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (16, 1) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (17, 1) on conflict do nothing;

insert into tablemakertemplate (templateid, name, description) values (2, 'GlyGen Glycoproteomics', 'Template for submitting glycoproteins to GlyGen') on conflict do nothing;
insert into table_column (columnid, name, glycoproteincolumn, column_order) values (21, 'Uniprot ID', 'UNIPROTID',  1) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, glycoproteincolumn, column_order) values (32, 'GlyTouCan ID', 'GLYTOUCANID',  2) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, glycoproteincolumn, column_order) values (33, 'Amino Acid', 'AMINOACID',  3) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, glycoproteincolumn, column_order) values (34, 'Site/Position', 'SITE',  4) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, glycoproteincolumn, column_order) values (35, 'Glycosylation Type', 'GLYCOSYLATIONTYPE',  5) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, glycoproteincolumn, column_order) values (36, 'Glycosylation Subtype', 'GLYCOSYLATIONSUBTYPE',  6) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (22, 'Evidence', 2, 'VALUE', 7) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (23, 'Species', 3, 'ID', 8) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (24, 'Strain', 4, 'VALUE', 9) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (25, 'Tissue', 5, 'ID', 10) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (26, 'Cell line ID', 6, 'ID', 11) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (27, 'Disease', 7, 'ID', 12) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (28, 'Functional annotation/Keyword', 11, 'VALUE', 13) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (29, 'Experimental technique', 12, 'VALUE', 14) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (30, 'Contributor', 16, 'VALUE', 15) ON CONFLICT DO NOTHING;
insert into table_column (columnid, name, datatypeid, valuetype, column_order) values (31, 'Comment', 17, 'VALUE', 16) ON CONFLICT DO NOTHING;
insert into tablemakertemplate_table_column (columnid, templateid) values (21, 2) ON CONFLICT DO NOTHING;
insert into tablemakertemplate_table_column (columnid, templateid) values (22, 2) ON CONFLICT DO NOTHING;
insert into tablemakertemplate_table_column (columnid, templateid) values (23, 2) ON CONFLICT DO NOTHING;
insert into tablemakertemplate_table_column (columnid, templateid) values (24, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (25, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (26, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (27, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (28, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (29, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (30, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (31, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (32, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (33, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (34, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (35, 2) on conflict do nothing;
insert into tablemakertemplate_table_column (columnid, templateid) values (36, 2) on conflict do nothing;

insert into license (id, name, attribution, commercialuse, distribution, url) values (1, 'CC0 1.0', 'The person who associated a work with this deed has dedicated the work to the public domain by waiving all of his or her rights to the work worldwide under copyright law, including all related and neighboring rights, to the extent allowed by law.', TRUE, 'You can copy, modify, distribute and perform the work, even for commercial purposes, all without asking permission.', 'https://creativecommons.org/publicdomain/zero/1.0/') on CONFLICT DO NOTHING;
insert into license (id, name, attribution, commercialuse, distribution, url) values (2, 'CC BY 4.0', 'You must give appropriate credit , provide a link to the license, and indicate if changes were made . You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.', TRUE, 'No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.', 'https://creativecommons.org/licenses/by/4.0/') on CONFLICT DO NOTHING;
insert into license (id, name, attribution, commercialuse, distribution, url) values (3, 'CC BY-SA 4.0', 'You must give appropriate credit , provide a link to the license, and indicate if changes were made . You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.', TRUE, 'ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.', 'https://creativecommons.org/licenses/by-sa/4.0/') on CONFLICT DO NOTHING;
insert into license (id, name, attribution, commercialuse, distribution, url) values (4, 'CC BY-ND 4.0', 'You must give appropriate credit , provide a link to the license, and indicate if changes were made . You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.', TRUE, 'NoDerivatives — If you remix, transform, or build upon the material, you may not distribute the modified material.', 'https://creativecommons.org/licenses/by-nd/4.0/') on CONFLICT DO NOTHING;
insert into license (id, name, attribution, commercialuse, distribution, url) values (5, 'CC BY-NC 4.0', 'You must give appropriate credit , provide a link to the license, and indicate if changes were made . You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.', FALSE, 'No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.', 'https://creativecommons.org/licenses/by-nc/4.0/') on CONFLICT DO NOTHING;
insert into license (id, name, attribution, commercialuse, distribution, url) values (6, 'CC BY-NC-SA 4.0', 'You must give appropriate credit , provide a link to the license, and indicate if changes were made . You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.', FALSE, 'ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.', 'https://creativecommons.org/licenses/by-nc-sa/4.0/') on CONFLICT DO NOTHING;
insert into license (id, name, attribution, commercialuse, distribution, url) values (7, 'CC BY-NC-ND 4.0', 'You must give appropriate credit , provide a link to the license, and indicate if changes were made . You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.', FALSE, 'NoDerivatives — If you remix, transform, or build upon the material, you may not distribute the modified material', 'https://creativecommons.org/licenses/by-nc-nd/4.0/') on CONFLICT DO NOTHING;