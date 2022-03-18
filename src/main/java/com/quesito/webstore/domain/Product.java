package com.quesito.webstore.domain;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@XmlRootElement
public class Product implements Serializable {
    @Pattern(regexp = "P[1-9]",message = "{Pattern.Product.productId.validation}")
    private String productId;
    @Size(min = 4,max = 50,message=" {Size.Product.name.validation}")
    private String name;
    @Min(value = 0,message="{Min.Product.unitPrice.validation}")
    @Digits(integer = 8,fraction = 2,message = "{Digits.Product.unitPrice.validation}")
    @NotNull(message= "{NotNull.Product.unitPrice.validation}")
    private BigDecimal unitPrice;
    private String description;
    private String manufacturer;
    private String category;
    private long unitsInStock;
    private long unitsInOrder;
    private boolean discontinued;
    private String condition;
    @JsonIgnore
    @XmlTransient
    private MultipartFile productImage;

}
