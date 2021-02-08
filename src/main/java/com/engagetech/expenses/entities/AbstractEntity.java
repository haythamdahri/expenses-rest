package com.engagetech.expenses.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Haytham DAHRI
 */
@Entity
@Data
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, updatable = false)
    private Long id;

    @Version
    private Timestamp timestamp;

}
