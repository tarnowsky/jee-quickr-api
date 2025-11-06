package pg.eti.kask.jee.quickr.order.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.kask.jee.quickr.datastore.component.DataStore;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.repository.api.VenueRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class VenueInMemoryRepository implements VenueRepository {

    private final DataStore dataStore;

    @Inject
    public VenueInMemoryRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<Venue> findById(UUID id){
        return dataStore.findVenueById(id);
    }

    @Override
    public List<Venue> findAll(){
        return dataStore.findAllVenues();
    }

    @Override
    public Optional<Venue> create(Venue entity){
        return dataStore.createVenue(entity);
    }

    @Override
    public Optional<Venue> update(Venue entity){
        return dataStore.updateVenue(entity);
    }

    @Override
    public Optional<Venue> delete(UUID id){
        return dataStore.removeVenueById(id);
    }
}
