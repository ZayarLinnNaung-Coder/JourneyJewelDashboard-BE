package rootstudio.development.dms.global.auth.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import rootstudio.development.dms.global.document.Admin;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

	private String id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(String id, String username, String email, String password,
							 Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static CustomUserDetails build(Admin admin) {
		List<GrantedAuthority> authorities = admin.getRole() == null ? null: Collections.singletonList(
				new SimpleGrantedAuthority(admin.getRole().getName()));

		return new CustomUserDetails(
				admin.getId(),
				admin.getUsername(),
				admin.getEmail(),
				admin.getPassword(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}