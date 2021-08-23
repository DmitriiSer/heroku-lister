package serikov.dmitrii.lister.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import serikov.dmitrii.lister.model.UserProfile;

@Repository
public interface UserProfileRepository extends ReactiveCrudRepository<UserProfile, String> {

	Mono<UserProfile> findByUsername(String username);
}
