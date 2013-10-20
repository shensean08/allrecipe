<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<body>
<s:form name="frmreceipe" action="recipeAction">
<table>
<tr>
<td>link</td>
<td><s:textfield name="formBean.link" value="%{formBean.link}" size="200" maxlength="200"/></td>
</tr>
<tr>
<td>Country</td>
<td><s:textfield name="formBean.country" value="%{formBean.country}" size="50" maxlength="50"/></td>
</tr>
<tr>
<td>recipe name</td>
<td><s:textfield name="formBean.recipeName" value="%{formBean.recipeName}" size="50" maxlength="50"/></td>
</tr>
<tr>
<td></td>
<td><s:submit/></td>
</tr>
</table>
</s:form>
</body>
</html>