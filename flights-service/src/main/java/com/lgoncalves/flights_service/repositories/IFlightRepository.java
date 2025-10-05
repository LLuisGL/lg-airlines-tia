package com.lgoncalves.flights_service.repositories;

import com.lgoncalves.flights_service.dtos.FlightDTO;
import com.lgoncalves.flights_service.entities.FlightEntity;
import com.lgoncalves.flights_service.utils.ICrud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFlightRepository extends JpaRepository<FlightEntity, String> {



}
