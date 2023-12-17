package com.learn.datedemo.entity;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DateTimeWrapper {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	//@DateTimeFormat(pattern = "dd/MM/yyyy") HH:mm:ss
	@JsonFormat(shape = Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
	private Date datetime;
	
	@JsonFormat(shape = Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
	private java.sql.Date dateTimeSql;
	
	@JsonFormat(shape = Shape.STRING, pattern="dd/MM/yyyy")
	private LocalDate localDate;
	
	@JsonFormat(shape = Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
	private LocalDateTime localDateTime;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date dateTemporal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date dateTimestampTemporal;
	
	@Temporal(TemporalType.TIME)
	//@JsonFormat(pattern="HH:mm:ss")
	private Time timeTemporal;
}
