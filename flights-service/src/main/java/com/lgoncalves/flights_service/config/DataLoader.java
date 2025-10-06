package com.lgoncalves.flights_service.config;

import com.lgoncalves.flights_service.entities.FlightEntity;
import com.lgoncalves.flights_service.repositories.IFlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadFlights(IFlightRepository flightRepository) {
        return args -> {
            var resource = new ClassPathResource("poblacion.csv");
            try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
                String[] line;
                boolean first = true;
                List<FlightEntity> flights = new ArrayList<>();
                if (flightRepository.count() == 0) {
                    while ((line = reader.readNext()) != null) {
                        if (first) {
                            first = false;
                            continue;
                        }

                        FlightEntity flight = new FlightEntity();
                        flight.setOrigen(line[0]);
                        flight.setDestino(line[1]);
                        flight.setFecha(Date.valueOf(LocalDate.parse(line[2])));
                        flight.setHorario(Long.parseLong(line[3]));
                        flight.setDisponibilidad(Integer.parseInt(line[4]));

                        flights.add(flight);
                    }

                    flightRepository.saveAll(flights);
                }
            }
        };
    }
}
