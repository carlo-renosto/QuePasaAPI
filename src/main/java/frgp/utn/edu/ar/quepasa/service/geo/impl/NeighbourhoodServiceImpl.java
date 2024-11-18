package frgp.utn.edu.ar.quepasa.service.geo.impl;
import frgp.utn.edu.ar.quepasa.model.geo.Neighbourhood;
import frgp.utn.edu.ar.quepasa.repository.geo.NeighbourhoodRepository;
import frgp.utn.edu.ar.quepasa.service.geo.NeighbourhoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NeighbourhoodServiceImpl implements NeighbourhoodService {

    private final NeighbourhoodRepository neighbourhoodRepository;

    @Autowired
    public NeighbourhoodServiceImpl(NeighbourhoodRepository neighbourhoodRepository) {
        this.neighbourhoodRepository = neighbourhoodRepository;
    }

    // Crear un nuevo barrio
    @Override
    public Neighbourhood createNeighbourhood(Neighbourhood neighbourhood) {
        return neighbourhoodRepository.save(neighbourhood);
    }

    // Obtener todos los barrios
    @Override
    public Page<Neighbourhood> getAllNeighbourhoods(boolean activeOnly, Pageable pageable) {
        if (activeOnly) {
            return neighbourhoodRepository.findByActiveTrue(pageable);
        }
        return neighbourhoodRepository.findAll(pageable);
    }

    // Obtener un barrio por su ID
    @Override
    public Optional<Neighbourhood> getNeighbourhoodById(long id, boolean activeOnly) {
        if (activeOnly) {
            return neighbourhoodRepository.findActiveNeighbourhoodById(id);
        }
        return neighbourhoodRepository.findById(id);
    }

    // Buscar barrios por nombre
    @Override
    public Page<Neighbourhood> searchNeighbourhoodsByName(String name, Pageable pageable, int city) {
        return city == -1 ? 
        neighbourhoodRepository.findByNameAndActive(name, pageable)
        : neighbourhoodRepository.findByNameAndActive(name, pageable, city);
    }

    // Actualizar un barrio existente
    @Override
    public Neighbourhood updateNeighbourhood(Neighbourhood updatedNeighbourhood) {
        return neighbourhoodRepository.save(updatedNeighbourhood);
    }

    // Eliminar un barrio
    @Override
    public void deleteNeighbourhood(long id) {
        Optional<Neighbourhood> neighbourhoodOptional = neighbourhoodRepository.findById(id);
        if (neighbourhoodOptional.isPresent()) {
            Neighbourhood neighbourhood = neighbourhoodOptional.get();
            neighbourhood.setActive(false);
            neighbourhoodRepository.save(neighbourhood);
        }
    }
}
