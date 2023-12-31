package org.glygen.tablemaker.persistence.glycan;

import java.util.Date;

import org.glygen.tablemaker.persistence.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="glycan_collection")
public class GlycanInCollection {
    Long id;
    Glycan glycan;
    Collection collection;
    Date dateAdded;
    
    /**
     * @return the id
     */
    @Id
    @GeneratedValue
    @Column(name="glycancollectionid")
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the glycan
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "glycanid")  
    public Glycan getGlycan() {
        return glycan;
    }
    /**
     * @param glycan the glycan to set
     */
    public void setGlycan(Glycan glycan) {
        this.glycan = glycan;
    }
    /**
     * @return the collection
     */
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Collection.class)
    @JoinColumn(name = "collectionid")
    public Collection getCollection() {
        return collection;
    }
    /**
     * @param collection the collection to set
     */
    public void setCollection(Collection collection) {
        this.collection = collection;
    }
    /**
     * @return the dateAdded
     */
    @Column(name = "dateadded")
    @Temporal(TemporalType.DATE)
    public Date getDateAdded() {
        return dateAdded;
    }
    /**
     * @param dateAdded the dateAdded to set
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

}
