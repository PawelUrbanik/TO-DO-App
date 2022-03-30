package pl.pawel.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
abstract class BaseAuditableEntity {

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void preMerge(){
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate()
    {
        updatedOn=LocalDateTime.now();
    }
}
