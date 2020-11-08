package pl.sda.spring.customers.service;

import pl.sda.spring.customers.entity.Address;

interface GeocodingService {

    Address reverse(double latitude, double longitude);
}
