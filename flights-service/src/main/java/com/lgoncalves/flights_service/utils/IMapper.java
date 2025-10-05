package com.lgoncalves.flights_service.utils;

public interface IMapper<E>  {

    E getDTO();

    void setDTO(E e);

}
