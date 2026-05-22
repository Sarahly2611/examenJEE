<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Formulaire produit</title>
</head>
<body>
<h2>
    <c:choose>
        <c:when test="${actionType == 'insert'}">Ajouter un produit</c:when>
        <c:otherwise>Modifier un produit</c:otherwise>
    </c:choose>
</h2>

<form action="ProductServlet" method="post">
    <input type="hidden" name="action" value="${actionType}" />
    <c:if test="${actionType == 'update'}">
        <input type="hidden" name="id" value="${product.id}" />
    </c:if>

    <label>Nom :</label>
    <input type="text" name="name" value="${product.name}" required /><br/>

    <label>Prix :</label>
    <input type="number" step="0.01" name="price" value="${product.price}" required /><br/>

    <label>Quantité :</label>
    <input type="number" name="quantity" value="${product.quantity}" required /><br/>

    <label>Type :</label>
    <select name="type" required>
        <option value="">-- Sélectionner --</option>
        <option value="Alimentaire" ${product.type == 'Alimentaire' ? 'selected' : ''}>Alimentaire</option>
        <option value="Informatique" ${product.type == 'Informatique' ? 'selected' : ''}>Informatique</option>
        <option value="Vestimentaire" ${product.type == 'Vestimentaire' ? 'selected' : ''}>Vestimentaire</option>
        <option value="Autre" ${product.type == 'Autre' ? 'selected' : ''}>Autre</option>
    </select><br/>

    <input type="submit" value="Valider" />
    <a href="ProductServlet?action=list">Annuler</a>
</form>
</body>
</html>