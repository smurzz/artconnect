package com.artconnect.backend.config.deserializer;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@JsonComponent
public class GrantedAuthorityDeserializer extends JsonDeserializer<GrantedAuthority> {

	@Override
	public GrantedAuthority deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		return new SimpleGrantedAuthority(p.getValueAsString());
	}
}
