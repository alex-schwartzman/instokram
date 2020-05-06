package com.alex.instokram.domain;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "useruploadedimage")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class UserUploadedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String blobUri;

    @Column(nullable = false)
    private String uploadedBy;

    @Column()
    private String description;
}
