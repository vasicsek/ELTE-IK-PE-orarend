package com.elte.osz.logic.dbhandler;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author RMUGLK
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Column(updatable = false, insertable = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public BaseEntity() {

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

}
