package com.radovan.spring.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.NoteEntity;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {

	@Query(value="select * from notes where created >= :ts1 and created <= :ts2",nativeQuery = true)
	List<NoteEntity> listAllForToday(@Param ("ts1") Timestamp ts1,@Param("ts2") Timestamp ts2);
}
