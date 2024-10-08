package org.glygen.tablemaker.persistence.glycan;

import java.util.ArrayList;
import java.util.List;

import org.glygen.tablemaker.persistence.UserEntity;
import org.glygen.tablemaker.persistence.protein.GlycoproteinInCollection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="collections")
public class Collection {
    Long collectionId;
    String name;
    String description;
    UserEntity user;
    CollectionType type;
    java.util.Collection<Metadata> metadata;
    java.util.Collection<Collection> parents;
    java.util.Collection<Collection> collections;  
    java.util.Collection<GlycanInCollection> glycans;
    java.util.Collection<GlycoproteinInCollection> glycoproteins;
    java.util.Collection<CollectionTag> tags;
    
    /**
     * @return the id
     */
    @Id
    @GeneratedValue
    @Column(name="collectionid")
    public Long getCollectionId() {
        return collectionId;
    }
    /**
     * @param id the id to set
     */
    public void setCollectionId(Long id) {
        this.collectionId = id;
    }
    /**
     * @return the name
     */
    @Column(nullable=false)
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the description
     */
    @Column(length=4000)
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the user
     */
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userid", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
    public UserEntity getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    /**
     * @return the collections
     */
    @ManyToMany(mappedBy="parents")
    public java.util.Collection<Collection> getCollections() {
        return collections;
    }
    /**
     * @param collections the collections to set
     */
    
    public void setCollections(java.util.Collection<Collection> collections) {
        this.collections = collections;
    }
    /**
     * @return the glycans
     */
    @OneToMany(mappedBy = "collection", cascade=CascadeType.ALL, orphanRemoval = true)
    public java.util.Collection<GlycanInCollection> getGlycans() {
        return glycans;
    }
    /**
     * @param glycans the glycans to set
     */
    public void setGlycans(java.util.Collection<GlycanInCollection> glycans) {
        this.glycans = glycans;
    }
    
    @OneToMany(mappedBy = "collection", cascade=CascadeType.ALL, orphanRemoval = true)
    public java.util.Collection<GlycoproteinInCollection> getGlycoproteins() {
		return glycoproteins;
	}
    
    public void setGlycoproteins(java.util.Collection<GlycoproteinInCollection> glycoproteins) {
		this.glycoproteins = glycoproteins;
	}
    
    @ManyToMany
    public java.util.Collection<Collection> getParents() {
		return parents;
	}
    
    public void setParents(java.util.Collection<Collection> parents) {
		this.parents = parents;
	}
    
    @OneToMany(mappedBy = "collection", cascade=CascadeType.ALL, orphanRemoval = true)
	public java.util.Collection<Metadata> getMetadata() {
		return metadata;
	}
	public void setMetadata(List<Metadata> metadata) {
		this.metadata = metadata;
	}
	
	public void addParent (Collection parent) {
		if (this.parents == null) {
			this.parents = new ArrayList<>();
		}
		if (!this.parents.contains(parent)) {
			this.parents.add(parent);
		}
	}
	
	@ManyToMany
	public java.util.Collection<CollectionTag> getTags() {
		return tags;
	}
	public void setTags(java.util.Collection<CollectionTag> tags) {
		this.tags = tags;
	}
	
	@Column(name="type", length=50)
	@Enumerated(EnumType.STRING)
	public CollectionType getType() {
		return type;
	}
	public void setType(CollectionType type) {
		this.type = type;
	}

}
