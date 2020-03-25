package com.flip.entity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.core.serializer.Serializer;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt","createdBy","updatedBy"},
        allowGetters = true
)
@Data
public abstract class AuditModel implements Serializer{

	@Temporal(TemporalType.TIMESTAMP)
   // @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
  //  @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;
    
   // @Column(nullable = false, updatable = false)
    private String createdBy;
    
    //@Column(nullable = false)
    private String updatedBy;

//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
    
    @Override
	public void serialize(Object object, OutputStream outputStream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	
}
