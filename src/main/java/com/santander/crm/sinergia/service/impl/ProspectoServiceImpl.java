package com.santander.crm.sinergia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santander.crm.sinergia.dao.ProspectoRepository;
import com.santander.crm.sinergia.entity.Prospecto;
import com.santander.crm.sinergia.filter.ProspectoFilter;
import com.santander.crm.sinergia.service.ProspectoService;

@Service("prospectoServiceImpl")
public class ProspectoServiceImpl implements ProspectoService {
	
	@Autowired
	ProspectoRepository prospectoRepository;

	@Override
	public List<Prospecto> getProspectosByFilter(ProspectoFilter filter) {
		
		return (List<Prospecto>) prospectoRepository.findAll();
	}

	@Override
	public Prospecto saveProspecto(Prospecto prospecto) {
		return prospectoRepository.save(prospecto);
	}

}
