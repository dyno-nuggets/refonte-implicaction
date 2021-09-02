package com.dynonuggets.refonteimplicaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "relation")
public class Relation {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User reciever;
    private Date date;
}
