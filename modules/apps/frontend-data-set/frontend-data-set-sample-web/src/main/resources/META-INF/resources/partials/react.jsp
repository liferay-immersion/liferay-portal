<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
ReactFDSDisplayContext reactFDSDisplayContext = new ReactFDSDisplayContext(request);
%>

<react:component
	module="{SampleReactFrontendDataSet} from frontend-data-set-sample-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"apiURL", reactFDSDisplayContext.getAPIURL()
		).put(
			"id", FDSSampleFDSNames.REACT
		).put(
			"style", "fluid"
		).put(
			"views", reactFDSDisplayContext.getViews()
		).build()
	%>'
/>