package root.development.tms.global.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import root.development.tms.global.document.Admin;
import root.development.tms.global.repo.AdminRepo;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AdminRepo adminRepo;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Admin user = adminRepo.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

		return CustomUserDetails.build(user);
	}

}