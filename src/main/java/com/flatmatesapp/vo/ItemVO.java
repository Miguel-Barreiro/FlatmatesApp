package com.flatmatesapp.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author iulian.dafinoiu
 */
@Data
public class ItemVO implements Serializable {
    private static final long serialVersionUID = 187945621349879788L;
    
    private Integer id;
    private String name;
    private Integer idUser;
    private Integer idSpending;
    private Date addedDate;
    private Integer recurrentInterval;
    private Date startingDate;
    
}
