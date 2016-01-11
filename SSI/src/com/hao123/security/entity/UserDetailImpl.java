package com.hao123.security.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hao123.manager.entity.mapping.User;

public class UserDetailImpl extends User implements UserDetails{

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	
	
}
