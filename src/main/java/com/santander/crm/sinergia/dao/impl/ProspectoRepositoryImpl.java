package com.santander.crm.sinergia.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.santander.crm.sinergia.dao.ProspectoCustomRepository;
import com.santander.crm.sinergia.entity.Prospecto;
import com.santander.crm.sinergia.filter.ProspectoFilter;
import com.santander.crm.sinergia.filter.ProspectoSeguimiento;

@Repository
public class ProspectoRepositoryImpl implements ProspectoCustomRepository {
	
	private final static Integer iEstatusConvertido = 4;

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ProspectoSeguimiento> getProspectosFiltered(ProspectoFilter filter) {
		Integer pageNum = 0;
		Integer pageSize = 5;

		if (filter.getPageSize() != null) {
			pageSize = filter.getPageSize();
		}

		if (filter.getPageNum() != null) {
			int pageNumValue = filter.getPageNum();
			pageNumValue--;
			pageNum = (pageSize * pageNumValue);
		}

		StringBuffer sbQuery = new StringBuffer();

		sbQuery.append("SELECT p, to_date(sysdate, 'dd/mm/yyyy') - to_date(p.fechaAlta, 'dd/mm/yyyy') as dias FROM Prospecto p ");
		sbQuery.append(filterToWhereClause(filter));
		sbQuery.append("order by p.idEstatus asc, p.fechaAlta asc, p.id asc ");
		System.out.println("query-->"+sbQuery);
		Query query = entityManager.createQuery(sbQuery.toString());
		query.setFirstResult(pageNum);
		query.setMaxResults(pageSize);
		List<ProspectoSeguimiento> psList = new ArrayList<>();
		List<Object[]> resultList = query.getResultList();
		ProspectoSeguimiento ps = null;
		for (Object o[] : resultList) {
			ps = new ProspectoSeguimiento();
			Prospecto pr = (Prospecto) o[0];
			ps.setP(pr);
			Integer i = ((Double)o[1]).intValue();
			ps.setDias(i);
			psList.add(ps);
		}
		return psList;
	}

	@Override
	public Long countProspectosFiltered(ProspectoFilter filter) {
		StringBuffer sbQuery = new StringBuffer();
		
		sbQuery.append("SELECT count(p.id) from Prospecto p ");
		sbQuery.append(filterToWhereClause(filter));
		
		Query query = entityManager.createQuery(sbQuery.toString(), Long.class);
		Long total = (Long) query.getSingleResult();
		return total;
	}

	@Override
	public Long countProspectosConvertidosFiltered(ProspectoFilter filter) {
		StringBuffer sbQuery = new StringBuffer();
		
		sbQuery.append("SELECT count(p.id) from Prospecto p ");
		sbQuery.append(filterToWhereClause(filter));
		sbQuery.append("and p.idEstatus = " + iEstatusConvertido);
		
		Query query = entityManager.createQuery(sbQuery.toString(), Long.class);
		Long total = (Long) query.getSingleResult();
		return total;
	}
	
	@Override
	public Long countProspectosByEjecutivo(String ofiAsignado) {
		Query query = entityManager.createQuery("SELECT count(p.id) FROM Prospecto p WHERE p.ofiAsignado = :ofiAsignado ", Long.class);
		query.setParameter("ofiAsignado", ofiAsignado);
		Long total = (Long) query.getSingleResult();
		return total;
	}

	private String filterToWhereClause(ProspectoFilter filter) {
		StringBuffer sbWhere = new StringBuffer();
		sbWhere.append("WHERE ");
		if (filter.getTipoConsulta() != null) {
			switch (filter.getTipoConsulta()) {
			case 1:
				sbWhere.append("p.ofiReferente = '" + filter.getOfiAct() + "'");
				break;
			case 2:
				// caso 2 u otro se va por asignado
			default:
				sbWhere.append("p.ofiAsignado = '" + filter.getOfiAct() + "'");
				break;
			}
		} else {
			sbWhere.append("p.ofiAsignado = '" + filter.getOfiAct() + "'");
		}
		sbWhere.append(" ");
		return sbWhere.toString();
	}

}
