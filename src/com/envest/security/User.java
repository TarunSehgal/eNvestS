package com.envest.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class User implements UserDetails
{

	private Long id;
	private String name;

	private String password;
	private Set<Role> roles = new HashSet<Role>();

	protected User()
	{
		/* Reflection instantiation */
	}

	public User(String name, String passwordHash)
	{
		this.name = name;
		this.password = passwordHash;
		this.addRole(Role.USER);
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public void addRole(Role role)
	{
		this.roles.add(role);
	}
	
	public Set<Role> getRoles()
	{
		return this.roles;
	}
	
	@Override
	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.getRoles();
	}

	@Override
	public String getUsername()
	{
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
