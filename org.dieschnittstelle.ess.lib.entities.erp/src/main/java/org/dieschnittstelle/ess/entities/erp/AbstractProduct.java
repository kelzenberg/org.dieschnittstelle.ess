package org.dieschnittstelle.ess.entities.erp;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/*
 * TODO JRS3: entfernen Sie die Auskommentierung der Annotation
 */

// @XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://dieschnittstelle.org/ess/entities/erp/ws")
@XmlSeeAlso({IndividualisedProductItem.class, ProductType.class})

@Entity
// add property @class with the respective AbstractProduct CLASS as a value to the response JSON
// and also read property @class from the request JSON to find the correct AbstractProduct CLASS
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@SequenceGenerator(name = "product_sequence", sequenceName = "product_id_sequence")
public abstract class AbstractProduct implements Serializable, GenericCRUDEntity {

    protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(AbstractProduct.class);

    /**
     *
     */
    private static final long serialVersionUID = 6940403029597060153L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private long id;

    private String name;

    private int price;

    public AbstractProduct() {
        logger.info("<constructor>");
    }

    public AbstractProduct(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
