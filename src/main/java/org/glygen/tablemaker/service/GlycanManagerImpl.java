package org.glygen.tablemaker.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.glygen.tablemaker.persistence.BatchUploadEntity;
import org.glygen.tablemaker.persistence.UserEntity;
import org.glygen.tablemaker.persistence.dao.GlycanRepository;
import org.glygen.tablemaker.persistence.dao.GlycanTagRepository;
import org.glygen.tablemaker.persistence.glycan.Glycan;
import org.glygen.tablemaker.persistence.glycan.GlycanInFile;
import org.glygen.tablemaker.persistence.glycan.GlycanTag;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class GlycanManagerImpl implements GlycanManager {

	final private GlycanRepository glycanRepository;
	final private GlycanTagRepository glycanTagRepository;
    
    public GlycanManagerImpl(GlycanTagRepository glycanTagRepository, GlycanRepository glycanRepository) {
		this.glycanRepository = glycanRepository;
		this.glycanTagRepository = glycanTagRepository;
	}
    
	@Override
	public void addTagToGlycans(Collection<Glycan> glycans, String tag, UserEntity user) {
		if (glycans != null) {
			GlycanTag gTag = new GlycanTag();
    		gTag.setLabel(tag);
    		gTag.setUser(user);
    		GlycanTag existing = glycanTagRepository.findByUserAndLabel(user, tag);
    		if (existing == null) {
    			existing = glycanTagRepository.save(gTag);
    		}
        	for (Glycan g: glycans) {
        		g.addTag (existing);
        		glycanRepository.save(g);
        	}
        }
	}
	
	@Override
	public Glycan addUploadToGlycan (Glycan glycan, BatchUploadEntity upload, Boolean isNew, UserEntity user) {
		if (glycan != null) {
			GlycanInFile u = new GlycanInFile();
            u.setUploadFile(upload);
            u.setGlycan(glycan);
            u.setIsNew(isNew);
            glycan.setUploadFiles(new ArrayList<>());
            glycan.getUploadFiles().add(u);
            Glycan added = glycanRepository.save(glycan);
            return added;
        }
		return null;
	}
	
	@Override
	public List<GlycanTag> getTags(UserEntity user) {
		return new ArrayList<>(glycanTagRepository.findAllByUser(user));
	}

	@Override
	public void setGlycanTags(Glycan glycan, List<String> tags, UserEntity user) {
		List<GlycanTag> newTagList = new ArrayList<>();
		for (String tag: tags) {
			GlycanTag gTag = new GlycanTag();
			gTag.setLabel(tag);
			gTag.setUser(user);
			GlycanTag existing = glycanTagRepository.findByUserAndLabel(user, tag);
			if (existing == null) {
				existing = glycanTagRepository.save(gTag);
			}
			newTagList.add(existing);
		}
		glycan.setTags(newTagList);
		glycanRepository.save(glycan);
		
	}
}
