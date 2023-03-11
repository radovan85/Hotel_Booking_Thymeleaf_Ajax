package com.radovan.spring.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

	@Query(value = "select * from reservations where room_id = :roomId", nativeQuery = true)
	List<ReservationEntity> findAllByRoomId(@Param("roomId") Integer roomId);

	@Query(value = "select * from reservations where guest_id = :guestId", nativeQuery = true)
	List<ReservationEntity> findAllByGuestId(@Param("guestId") Integer guestId);

	@Query(value = "select * from reservations where room_id = :roomId and (check_in between :checkInDate and :checkOutDate or check_out between :checkInDate and :checkOutDate)", nativeQuery = true)
	List<ReservationEntity> checkForReservations(@Param("roomId") Integer roomId,
			@Param("checkInDate") Timestamp checkInDate, @Param("checkOutDate") Timestamp checkOutDate);
	
	@Modifying
	@Query(value = "delete from reservations where room_id = :roomId", nativeQuery = true)
	void deleteAllByRoomId(@Param ("roomId") Integer roomId);
	
}
