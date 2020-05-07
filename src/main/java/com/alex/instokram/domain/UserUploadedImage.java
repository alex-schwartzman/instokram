package com.alex.instokram.domain;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "useruploadedimage")
@XmlRootElement
@Data
public class UserUploadedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String blobUri;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column()
    private String description;
}
