package com.lgoncalves.user_service.services.interfaces;

import com.lgoncalves.user_service.dtos.UserDTO;
import com.lgoncalves.user_service.dtos.BookingDTO;
import com.lgoncalves.user_service.utils.ICrud;

import java.util.List;

public interface IUserService extends ICrud<UserDTO> {

    List<BookingDTO> getReservesByUserId(String id);

}
