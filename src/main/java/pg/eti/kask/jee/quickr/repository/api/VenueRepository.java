package pg.eti.kask.jee.quickr.repository.api;

import pg.eti.kask.jee.quickr.entity.User;
import pg.eti.kask.jee.quickr.entity.Venue;

import java.util.List;
import java.util.UUID;

public interface VenueRepository extends Repository<Venue, UUID> {
    List<Venue> findAllByUser(User user);

    List<Venue> findAllByFilter(pg.eti.kask.jee.quickr.dto.VenueFilter filter);
}
