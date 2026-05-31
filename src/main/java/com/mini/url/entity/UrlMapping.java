package com.mini.url.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="urlmapping")
public class UrlMapping {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="ID")
private Long id;

@Column(name="originalUrl",nullable=false ,length=2048)
private String originalUrl;

@Column(name="shortcode",unique=true,length=10)
private String shortcode;

@Column(name="count")
@Builder.Default
private Long count=0L;

}
