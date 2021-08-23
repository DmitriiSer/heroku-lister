package serikov.dmitrii.lister.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import serikov.dmitrii.lister.model.UserProfile;
import serikov.dmitrii.lister.repository.UserProfileRepository;

@Service
public class UserProfileService {

	@Autowired
	private UserProfileRepository userProfileRepository;

	public Mono<Boolean> isUserPresent(final String username) {
		return userProfileRepository.findByUsername(username) //
				.defaultIfEmpty(new UserProfile()) //
				.map(userProfile -> userProfile.getUsername() != null);
	}

	public Mono<UserProfile> save(final UserProfile userProfile) {
		return userProfileRepository.save(userProfile);
	}

}
