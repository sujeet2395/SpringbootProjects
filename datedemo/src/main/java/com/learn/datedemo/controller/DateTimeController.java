package com.learn.datedemo.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.datedemo.entity.DateTimeWrapper;
import com.learn.datedemo.repository.DateTimeRepository;

@RestController
@RequestMapping("/datedemo")
public class DateTimeController {
	@Autowired
	private DateTimeRepository dateTimeRepo;
	
	@PostMapping("")
	public DateTimeWrapper createDateTime(@RequestBody DateTimeWrapper dateTimeWrapper)
	{
		return dateTimeRepo.save(dateTimeWrapper);
	}
	
	@PostMapping("/createNow")
	public DateTimeWrapper createDateTimeNow()
	{
		DateTimeWrapper dateTimeWrapper = new DateTimeWrapper();
		dateTimeWrapper.setDatetime(new Date());
		dateTimeWrapper.setDateTimeSql(new java.sql.Date(System.currentTimeMillis()));
		dateTimeWrapper.setDateTemporal(new Date());
		dateTimeWrapper.setDateTimestampTemporal(new Date());
		dateTimeWrapper.setLocalDate(LocalDate.now());
		dateTimeWrapper.setLocalDateTime(LocalDateTime.now());
		dateTimeWrapper.setTimeTemporal(new Time(System.currentTimeMillis()));
		return dateTimeRepo.save(dateTimeWrapper);
	}
	
	@PostMapping("/createNowWithZone")
	public DateTimeWrapper createDateTimeNowWithZone()
	{
		DateTimeWrapper dateTimeWrapper = new DateTimeWrapper();
		Date date = new Date();
		ZonedDateTime atZone = date.toInstant().atZone(ZoneId.of("Asia/Kolkata"));
		Date dateZoneDate = new Date(atZone.getYear(),atZone.getMonthValue()-1,atZone.getDayOfMonth(),atZone.getHour(),atZone.getMinute(),atZone.getSecond());
		LocalDateTime localDateTime = atZone.toLocalDateTime();
		LocalDate localDate = atZone.toLocalDate();
		dateTimeWrapper.setDatetime(dateZoneDate);
		dateTimeWrapper.setDateTimeSql(new java.sql.Date(atZone.getYear(),atZone.getMonthValue()-1,atZone.getDayOfMonth()));
		dateTimeWrapper.setDateTemporal(dateZoneDate);
		dateTimeWrapper.setDateTimestampTemporal(dateZoneDate);
		dateTimeWrapper.setLocalDate(localDate);
		dateTimeWrapper.setLocalDateTime(localDateTime);
		dateTimeWrapper.setTimeTemporal(new Time(dateZoneDate.getTime()));
		return dateTimeRepo.save(dateTimeWrapper);
	}
	
	@GetMapping("")
	public List<DateTimeWrapper> getDateTime()
	{
		return dateTimeRepo.findAll();
	}
}
