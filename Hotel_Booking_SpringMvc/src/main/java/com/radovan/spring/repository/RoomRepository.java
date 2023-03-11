package com.radovan.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {

	@Query(value = "select * from rooms where category_id = :categoryId", nativeQuery = true)
	List<RoomEntity> findAllByCategoryId(@Param("categoryId") Integer categoryId);

	@Query(value = "select * from rooms where room_number = :roomNumber", nativeQuery = true)
	RoomEntity findByRoomNumber(@Param("roomNumber") Integer roomNumber);
	
	@Modifying
	@Query(value = "delete from rooms where category_id = :categoryId",nativeQuery = true)
	void deleteAllByCategoryId(@Param ("categoryId") Integer categoryId);

}
