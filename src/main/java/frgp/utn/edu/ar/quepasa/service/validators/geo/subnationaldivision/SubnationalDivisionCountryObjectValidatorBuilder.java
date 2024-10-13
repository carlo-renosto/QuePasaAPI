package frgp.utn.edu.ar.quepasa.service.validators.geo.subnationaldivision;

import frgp.utn.edu.ar.quepasa.model.geo.Country;
import frgp.utn.edu.ar.quepasa.repository.geo.CountryRepository;
import frgp.utn.edu.ar.quepasa.service.validators.ValidatorBuilder;

public class SubnationalDivisionCountryObjectValidatorBuilder extends ValidatorBuilder<Country> {
    public SubnationalDivisionCountryObjectValidatorBuilder(Country value) {
        super(value, "country");
    }
    public SubnationalDivisionCountryObjectValidatorBuilder exists(CountryRepository repository) {
        if(getValue() == null) {
            super.invalidate("El objeto de la propiedad 'country' no puede ser nulo. ");
        }
        if(!repository.existsByIso3(getValue().getIso3())) {
            super.invalidate("El país al que se hace referencia no existe. ");
        }
        return this;
    }
}
