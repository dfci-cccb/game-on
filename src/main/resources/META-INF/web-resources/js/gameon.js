function getFilterQueryString(){
	var oTable = $("#l_edu_dfci_cccb_gameon_domain_Snp").dataTable();
	var oSettings = oTable.fnSettings();
	var sMarkerName = $("#markerName").val();
	sMarkerName = sMarkerName.indexOf("Enter")>-1 ? "" : sMarkerName;
	sBuild = encodeURIComponent($("#build").val());
	var sCoordinateLower = $("#coordinateBox1").val();
	sCoordinateLower = isNaN(sCoordinateLower) ? "" : sCoordinateLower;
	var sCoordinateUpper = $("#coordinateBox2").val();
	sCoordinateUpper = isNaN(sCoordinateUpper) ? "" : sCoordinateUpper;
	var sGeneInfo = $("#gene").val();
	sGeneInfo = sGeneInfo.indexOf("Enter")>-1 ? "" : sGeneInfo;
	var sAjaxSource="markerName="+sMarkerName+"&build="+sBuild+"&NStudy="+$("#nstudy").val()+"&chromosome="+$("#chromosome").val()+"&coordinateLower="+sCoordinateLower+"&coordinateUpper="+sCoordinateUpper+"&geneInfo="+sGeneInfo+"&iDisplayStart="+oSettings._iDisplayStart;
	return sAjaxSource;
}

function getCurrentAjaxSource(){
	var oTable = $('#l_edu_dfci_cccb_gameon_domain_Snp').dataTable();
	var oSettings = oTable.fnSettings();
	return oSettings.sAjaxSource;					
}

function getCurrentTableFilterQueryString(){
	var sCurAjaxSource = getCurrentAjaxSource();	
	var sCurFilter = sCurAjaxSource.substr(sCurAjaxSource.indexOf("?")+1);
	sCurFilter = sCurFilter.replace("find=json&", "");
	if(sCurFilter.indexOf("iDisplayStart")==-1)
		sCurFilter+="&iDisplayStart=0";
	return sCurFilter;
}