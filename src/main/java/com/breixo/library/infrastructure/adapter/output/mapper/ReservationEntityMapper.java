package com.breixo.library.infrastructure.adapter.output.mapper;

import java.util.List;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.infrastructure.adapter.output.entities.ReservationEntity;
import com.breixo.library.infrastructure.mapper.ReservationStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Reservation Entity Mapper. */
@Mapper(componentModel = "spring", uses = ReservationStatusMapper.class)
public interface ReservationEntityMapper {

    /**
     * To reservation.
     *
     * @param reservationEntity the reservation entity.
     * @return the reservation.
     */
    @Mapping(source = "statusId", target = "status")
    Reservation toReservation(ReservationEntity reservationEntity);

    /**
     * To reservation entity.
     *
     * @param createReservationCommand the create reservation command.
     * @return the reservation entity.
     */
    ReservationEntity toReservationEntity(CreateReservationCommand createReservationCommand);

    /**
     * To reservation list.
     *
     * @param reservationEntities the reservation entities.
     * @return the reservation list.
     */
    List<Reservation> toReservationList(List<ReservationEntity> reservationEntities);
}
