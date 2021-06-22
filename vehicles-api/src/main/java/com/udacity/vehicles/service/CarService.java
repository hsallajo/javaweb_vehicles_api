package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.Address;
import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private PriceClient pricing;
    private MapsClient maps;

    public CarService(CarRepository repository, PriceClient pricing, MapsClient maps) {

        this.maps = maps;
        this.pricing = pricing;
        this.repository = repository;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {

        System.out.println("Car id is: " + id);
        assert (repository != null);

        Car car = doFindCarById(id);

        String price = this.pricing.getPrice(id);
        System.out.println("Price is: " + price);

        car.setPrice(price);

        Location loc = car.getLocation();

        Location addr = this.maps.getAddress(loc);

        System.out.println("Address is: " + addr.getAddress());

        car.setLocation(addr);

        return car;
    }

    private Car doFindCarById(Long id) {
        Optional<Car> opt = repository.findById(id);

        try {
            if(!opt.isPresent()){
                throw new CarNotFoundException("There was no car with id: " + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Car car = opt.get();
        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {

        Car car = doFindCarById(id);

        if (car.getId() != null) {
            repository.delete(car);
        }

    }
}
