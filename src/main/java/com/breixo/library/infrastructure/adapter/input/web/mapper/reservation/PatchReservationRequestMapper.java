package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchReservationV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
import com.breixo.library.infrastructure.mapper.ReservationStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Patch Reservation Request Mapper. */
@Mapper(componentModel = "spring", uses = {ReservationStatusMapper.class, DateMapper.class})
public interface PatchReservationRequestMapper {

    /**
     * To update reservation command.
     *
     * @param id                        the reservation identifier.
     * @param patchReservationV1Request the patch reservation V1 request.
     * @return the update reservation command.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "patchReservationV1Request.loanId", target = "loanId")
    @Mapping(source = "patchReservationV1Request.expiresAt", target = "expiresAt")
    @Mapping(source = "patchReservationV1Request.status", target = "status")
    UpdateReservationCommand toUpdateReservationCommand(Integer id, PatchReservationV1Request patchReservationV1Request);
}
