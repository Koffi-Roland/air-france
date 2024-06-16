package com.airfrance.repind.dao.profil;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.entity.profil.Profil_mere;

import java.io.Serializable;

public interface IProfil_mereDAO {
	public void removeById(final Serializable id) throws InvalidParameterException;
	public void create(final Profil_mere entity) throws InvalidParameterException;
	public void update(final Profil_mere entity) throws InvalidParameterException;
	public Profil_mere findById(final Serializable id);
	public int countAll(final Profil_mere entity) throws InvalidParameterException;
}
