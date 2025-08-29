package com.example.BookStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private String booktitle;
    private double total;
    private Date orderdate;

    public Orders(int userId, String booktitle, double total, Date date){
        this.userId=userId;
        this.booktitle=booktitle;
        this.total=total;
        this.orderdate=orderdate;
    }
}
