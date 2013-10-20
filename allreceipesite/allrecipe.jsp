<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<body>
<s:form name="frmallreceipe" action="allrecipeAction">
<table border=1>
<tr>
<td>link:</td>
<td><s:textfield name="formBean.link" value="%{formBean.link}" readonly="true"/></td>
</tr>
<tr>
<td>Country:</td>
<td><s:textfield name="formBean.country" value="%{formBean.country}" readonly="true"/></td>
</tr>
<tr>
<td>recipeName:</td>
<td><s:textfield name="formBean.recipeName" value="%{formBean.recipeName}" readonly="true"/></td>
</tr>
<tr>
<td>[ingredient name]</td>
<td>[ingredient amount]</td>
</tr>
<s:iterator value="ingredientFormBean" status="st">
<tr>
<td>
	<s:property value="ingredientname" />
	<s:textfield name="%{'ingredientFormBean[' + #st.index + '].ingredientnameIn'}" value="%{ingredientnameIn}" size="50" maxlength="50"/>
</td>
<td>
	<s:property value="ingredientamount" />
	<s:textfield name="%{'ingredientFormBean[' + #st.index + '].ingredientamountIn'}" value="%{ingredientamountIn}" size="50" maxlength="50"/>
</td>
</tr>
</s:iterator>
<tr>
<td>error message:</td>
<td><s:property value="%{messagebean.errorMsg}"/></td>
</tr>
<tr>
<td><s:submit/></td><td></td>
</tr>
</table>
</s:form>
</body>
</html>