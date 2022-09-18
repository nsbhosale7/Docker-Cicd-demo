package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.BookService;

public interface ServiceRepository extends JpaRepository<BookService, Integer> {

}
