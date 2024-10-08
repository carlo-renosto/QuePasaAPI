package frgp.utn.edu.ar.quepasa.service.geo;

import frgp.utn.edu.ar.quepasa.model.geo.Neighbourhood;
import frgp.utn.edu.ar.quepasa.repository.geo.NeighbourhoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NeighbourhoodServiceImpl {

    @Autowired
    private NeighbourhoodRepository neighbourhoodRepository;

    // Crear un nuevo barrio
    public Neighbourhood createNeighbourhood(Neighbourhood neighbourhood) {
        return neighbourhoodRepository.save(neighbourhood);
    }

    // Obtener todos los barrios
    public List<Neighbourhood> getAllNeighbourhoods(boolean activeOnly) {
        if (activeOnly) {
            return neighbourhoodRepository.findByActiveTrue();
        }
        return neighbourhoodRepository.findAll();
    }

    // Obtener un barrio por su ID
    public Optional<Neighbourhood> getNeighbourhoodById(long id, boolean activeOnly) {
        if (activeOnly) {
            return neighbourhoodRepository.findActiveNeighbourhoodById(id);
        }
        return neighbourhoodRepository.findById(id);
    }

    // Buscar barrios por nombre
    public List<Neighbourhood> searchNeighbourhoodsByName(String name) {
        return neighbourhoodRepository.findByNameContainingIgnoreCase(name);
    }

    // Actualizar un barrio existente
    public Neighbourhood updateNeighbourhood(Neighbourhood updatedNeighbourhood) {
        return neighbourhoodRepository.save(updatedNeighbourhood);
    }

    // Eliminar un barrio
    public void deleteNeighbourhood(long id) {
        Optional<Neighbourhood> neighbourhoodOptional = neighbourhoodRepository.findById(id);
        if (neighbourhoodOptional.isPresent()) {
            Neighbourhood neighbourhood = neighbourhoodOptional.get();
            neighbourhood.setActive(false);
            neighbourhoodRepository.save(neighbourhood);
        }
    }
}
