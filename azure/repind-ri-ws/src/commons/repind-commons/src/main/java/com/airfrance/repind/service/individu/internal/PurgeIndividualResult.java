package com.airfrance.repind.service.individu.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurgeIndividualResult {

	private List<String> _ginsPurged;
	private List<String> _ginsToPurgeLater;
	private Map<List<String>, String> _errorsPhysicalPurge;
	private List<String> _cbsReport;
	private List<String> _cbsReportDetails;

	public Map<List<String>, String> getErrorsPhysicalPurge() {
		if (_errorsPhysicalPurge == null) {
			return new HashMap<>();
		}
		return _errorsPhysicalPurge;
	}

	public List<String> getGinsPurged() {
		if (_ginsPurged == null) {
			return new ArrayList<>();
		}
		return _ginsPurged;
	}

	public List<String> getGinsToPurgeLater() {
		if (_ginsToPurgeLater == null) {
			return new ArrayList<>();
		}
		return _ginsToPurgeLater;
	}

	public void setErrorsPhysicalPurge(final Map<List<String>, String> errorPhysicalPurge) {
		_errorsPhysicalPurge = errorPhysicalPurge;
	}

	public void setGinsPurged(final List<String> ginsPurged) {
		_ginsPurged = ginsPurged;
	}

	public void setGinsToPurgeLater(final List<String> ginsToPurgeLater) {
		_ginsToPurgeLater = ginsToPurgeLater;
	}
	
	public List<String> get_cbsReport() {
		return _cbsReport;
	}

	public void set_cbsReport(List<String> _cbsReport) {
		this._cbsReport = _cbsReport;
	}

	public List<String> get_cbsReportDetails() {
		return _cbsReportDetails;
	}

	public void set_cbsReportDetails(List<String> _cbsReportDetails) {
		this._cbsReportDetails = _cbsReportDetails;
	}
}
