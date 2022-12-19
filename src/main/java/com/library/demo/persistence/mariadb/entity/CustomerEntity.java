package com.library.demo.persistence.mariadb.entity;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class CustomerEntity implements Serializable {
    private static final long serialVersionUID = -297553281792804396L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idCustomer;
	
	@NotNull
	private String firstname;
	@NotNull
	private String lastname;
	private String address;
	private int age;
		
	public String toString() {
		return String.format("id=%d, firstname='%s', lastname'%s', address=%s, age=%d", 
								idCustomer, firstname, lastname, address, age);
	}
	
	public CustomerEntity(String firstname, String lastname, String address, int age) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.age = age;
	}	
}
